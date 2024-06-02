package ru.xdpxrt.vinyl.service;

import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.xdpxrt.vinyl.dto.orderDTO.FullOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.*;

@Validated
@FeignClient("ORDER-SERVICE")
public interface OrderFeignService {

    @GetMapping(ORDER_URI + USER_URI + ID_URI)
    public List<ShortOrderDTO> getOrdersByCustomer(@PathVariable @Positive Long id);

    @GetMapping(ORDER_URI + ID_URI)
    public FullOrderDTO getOrderById(@PathVariable @Positive Long id);
}