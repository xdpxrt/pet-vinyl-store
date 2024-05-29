package ru.xdpxrt.vinyl.order.service;

import ru.xdpxrt.vinyl.cons.OrderStatus;
import ru.xdpxrt.vinyl.dto.orderDTO.FullOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.NewOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;

import java.util.List;

public interface OrderService {
    FullOrderDTO addOrder(NewOrderDTO newOrderDTO, Long userId);

    ShortOrderDTO updateOrder(Long id, OrderStatus status);

    FullOrderDTO getOrder(Long orderId);

    void deleteOrder(Long orderId);

    List<ShortOrderDTO> getOrdersByCustomerId(Long userId);
}