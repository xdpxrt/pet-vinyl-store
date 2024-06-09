package ru.xdpxrt.vinyl.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.cons.OrderStatus;
import ru.xdpxrt.vinyl.dto.itemDTO.ItemDTO;
import ru.xdpxrt.vinyl.dto.itemDTO.ShortItemDTO;
import ru.xdpxrt.vinyl.dto.messageDTO.MessageDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.FullOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.NewOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UpdateUnitDTO;
import ru.xdpxrt.vinyl.dto.userDTO.AuthUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;
import ru.xdpxrt.vinyl.dto.userDTO.UserDTO;
import ru.xdpxrt.vinyl.handler.ConflictException;
import ru.xdpxrt.vinyl.handler.NotFoundException;
import ru.xdpxrt.vinyl.order.item.model.Item;
import ru.xdpxrt.vinyl.order.item.repository.ItemRepository;
import ru.xdpxrt.vinyl.order.mapper.OrderMapper;
import ru.xdpxrt.vinyl.order.model.Order;
import ru.xdpxrt.vinyl.order.repository.OrderRepository;
import ru.xdpxrt.vinyl.service.RecordFeignService;
import ru.xdpxrt.vinyl.service.UserFeignService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static ru.xdpxrt.vinyl.cons.Config.ORDERS_TOPIC;
import static ru.xdpxrt.vinyl.cons.Message.ORDER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    private final UserFeignService userFeignService;
    private final RecordFeignService recordFeignService;

    private final OrderMapper orderMapper;

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    @Override
    public FullOrderDTO addOrder(NewOrderDTO newOrderDTO, String username) {
        log.debug("Adding new order: {}", newOrderDTO);
        AuthUserDTO user = userFeignService.getUserByEmail(username);
        Map<Long, Integer> recordIdToPcs = newOrderDTO.getItems()
                .stream()
                .collect(Collectors.toMap(ShortItemDTO::getRecordId, ShortItemDTO::getPcs));
        Map<Long, ShortRecordDTO> records = getRecordsIdToRecordDTO(recordIdToPcs.keySet().stream().toList());
        List<ShortItemDTO> itemsDTO = newOrderDTO.getItems()
                .stream()
                .peek(i -> {
                    if (i.getPcs() > records.get(i.getRecordId()).getQuantity())
                        throw new ConflictException(String.format("Only %s pcs. of record ID%s available and warehouse",
                                records.get(i.getRecordId()).getQuantity(), i.getRecordId()));
                }).toList();
        final Order order = orderRepository.save(Order.builder()
                .created(LocalDateTime.now())
                .customerId(user.getId())
                .status(OrderStatus.NEW)
                .build());
        Map<Long, Item> recordIdToItem = itemsDTO
                .stream()
                .map(i -> Item.builder()
                        .order(order)
                        .recordId(i.getRecordId())
                        .quantity(i.getPcs())
                        .build())
                .peek(itemRepository::save)
                .collect(Collectors.toMap(Item::getRecordId, Function.identity()));
        recordIdToItem.values()
                .forEach(i -> recordFeignService.updateUnit(
                        UpdateUnitDTO
                                .builder()
                                .sell(i.getQuantity())
                                .build(), i.getRecordId()));
        FullOrderDTO fullOrderDTO = toFullOrderDTO(order, records, recordIdToPcs, toShortUserDTO(user));
        kafkaTemplate.send(ORDERS_TOPIC, MessageDTO
                .builder()
                .email(user.getEmail())
                .message(newOrderMessage(fullOrderDTO))
                .build());
        log.debug("New order: {} added", fullOrderDTO);
        return fullOrderDTO;
    }

    @Override
    public ShortOrderDTO updateOrder(Long id, OrderStatus status) {
        log.debug("Updating order ID{}", id);
        Order order = getOrderIfExists(id);
        order.setStatus(status);
        order = orderRepository.save(order);
        UserDTO user = userFeignService.getUserById(order.getCustomerId());
        kafkaTemplate.send(ORDERS_TOPIC, MessageDTO
                .builder()
                .email(user.getEmail())
                .message(String.format(
                        "The status of order #%s has been changed to '%s'", order.getId().toString(), status.name()))
                .build());
        log.debug("Order ID{} updated", id);
        return orderMapper.toShortOrderDTO(order);
    }

    @Override
    public FullOrderDTO getOrder(Long orderId) {
        log.debug("Getting order ID{}", orderId);
        Order order = getOrderIfExists(orderId);
        ShortUserDTO user = userFeignService.getShortUser(order.getCustomerId());
        Map<Long, Integer> recordIdToPcs = order.getItems()
                .stream()
                .collect(Collectors.toMap(Item::getRecordId, Item::getQuantity));
        Map<Long, ShortRecordDTO> records = getRecordsIdToRecordDTO(recordIdToPcs.keySet().stream().toList());
        return toFullOrderDTO(order, records, recordIdToPcs, user);
    }

    @Override
    public void deleteOrder(Long orderId) {
        log.debug("Deleting order ID{}", id);
        Order order = getOrderIfExists(orderId);
        orderRepository.delete(order);
        log.debug("Order ID{} deleted", orderId);
    }

    @Override
    public List<ShortOrderDTO> getOrdersByCustomerId(Long userId) {
        log.debug("Getting orders by customer ID{}", userId);
        userFeignService.getUserById(userId);
        List<Order> orders = orderRepository.findAllByCustomerId(userId);
        return orderMapper.toShortOrderDTO(orders);
    }

    private ShortUserDTO toShortUserDTO(AuthUserDTO userDTO) {
        return ShortUserDTO
                .builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
    }

    private Order getOrderIfExists(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundException(String.format(ORDER_NOT_FOUND, orderId)));
    }

    private Map<Long, ShortRecordDTO> getRecordsIdToRecordDTO(List<Long> ids) {
        return recordFeignService.getRecordsByIds(ids)
                .stream()
                .collect(Collectors.toMap(ShortRecordDTO::getId, Function.identity()));
    }

    private FullOrderDTO toFullOrderDTO(Order order,
                                        Map<Long, ShortRecordDTO> records,
                                        Map<Long, Integer> recordIdToPcs,
                                        ShortUserDTO user) {
        List<ItemDTO> items = records.values()
                .stream()
                .map(r -> ItemDTO
                        .builder()
                        .recordId(r.getId())
                        .title(r.getTitle())
                        .publicationYear(r.getPublicationYear())
                        .performerName(r.getPerformer().getName())
                        .price(r.getPrice())
                        .quantity(recordIdToPcs.get(r.getId()))
                        .build())
                .toList();
        FullOrderDTO orderDTO = orderMapper.toFullOrderDTO(order);
        orderDTO.setItems(items);
        orderDTO.setCustomer(user);
        orderDTO.setFullPrice(items.stream()
                .mapToDouble(i -> i.getQuantity() * i.getPrice())
                .sum());
        return orderDTO;
    }

    private String newOrderMessage(FullOrderDTO fullOrderDTO) {
        StringBuilder message = new StringBuilder(String.format("Order #%s has been created.", fullOrderDTO.getId()));
        fullOrderDTO.getItems()
                .forEach(i -> message.append("\n").append(i.toString()));
        return message.append("\n").append("Total value:").append(fullOrderDTO.getFullPrice()).toString();
    }
}