package ru.xdpxrt.vinyl.order.service;

import org.springframework.security.core.Authentication;
import ru.xdpxrt.vinyl.cons.OrderStatus;
import ru.xdpxrt.vinyl.dto.orderDTO.FullOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.NewOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;

import java.util.List;

public interface OrderService {
    FullOrderDTO addOrder(NewOrderDTO newOrderDTO, Authentication authentication);

    ShortOrderDTO updateOrder(Long id, OrderStatus status);

    FullOrderDTO getOrder(Long orderId, Authentication authentication);

    void deleteOrder(Long orderId, Authentication authentication);

    List<ShortOrderDTO> getOrdersByCustomerId(Long userId);
}