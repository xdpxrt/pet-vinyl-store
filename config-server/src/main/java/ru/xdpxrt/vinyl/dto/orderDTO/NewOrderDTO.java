package ru.xdpxrt.vinyl.dto.orderDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.itemDTO.ShortItemDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDTO {
    @NotNull
    List<ShortItemDTO> items;
}