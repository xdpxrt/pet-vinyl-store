package ru.xdpxrt.vinyl.order.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.cons.OrderStatus;
import ru.xdpxrt.vinyl.dto.orderDTO.FullOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.NewOrderDTO;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;
import ru.xdpxrt.vinyl.order.service.OrderService;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ORDER_URI)
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullOrderDTO addOrder(@RequestBody @Valid NewOrderDTO newOrderDTO,
                                 Authentication authentication) {
        log.info("Response from POST request on {}", ORDER_URI);
        return orderService.addOrder(newOrderDTO, authentication);
    }

    @PatchMapping(ID_URI)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ShortOrderDTO updateOrder(@PathVariable @Positive Long id,
                                     @RequestParam OrderStatus status,
                                     Authentication authentication) {
        log.info("Response from PATCH request on {}/{}", ORDER_URI, id);
        return orderService.updateOrder(id, status);
    }

    @GetMapping(ID_URI)
    public FullOrderDTO getOrderById(@PathVariable @Positive Long id,
                                     Authentication authentication) {
        log.info("Response from GET request on {}/{}", ORDER_URI, id);
        return orderService.getOrder(id, authentication);
    }

    @DeleteMapping(ID_URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable @Positive Long id,
                            Authentication authentication) {
        log.info("Response from DELETE request on {}/{}", ORDER_URI, id);
        orderService.deleteOrder(id, authentication);
    }


    @GetMapping(USER_URI + ID_URI)
    public List<ShortOrderDTO> getOrdersByCustomer(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}", ORDER_URI + USER_URI, id);
        return orderService.getOrdersByCustomerId(id);
    }
}