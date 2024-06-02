package ru.xdpxrt.vinyl.dto.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.cons.OrderStatus;
import ru.xdpxrt.vinyl.dto.itemDTO.ItemDTO;
import ru.xdpxrt.vinyl.dto.userDTO.ShortUserDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullOrderDTO {
    private Long id;
    private ShortUserDTO customer;
    private OrderStatus status;
    private LocalDate created;
    private Double fullPrice;
    private List<ItemDTO> items;
}
