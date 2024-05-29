package ru.xdpxrt.vinyl.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.orderDTO.FullOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;
import ru.xdpxrt.vinyl.order.model.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "fullPrice", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "customer", ignore = true)
    FullOrderDTO toFullOrderDTO(Order order);

    ShortOrderDTO toShortOrderDTO(Order order);

    List<ShortOrderDTO> toShortOrderDTO(List<Order> orders);
}