package ru.xdpxrt.vinyl.dto.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.cons.OrderStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortOrderDTO {
    private Long id;
    private OrderStatus status;
    private LocalDate created;
}