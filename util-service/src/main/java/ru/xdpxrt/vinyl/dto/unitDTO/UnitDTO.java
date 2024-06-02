package ru.xdpxrt.vinyl.dto.unitDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {
    private Long recordId;
    private LocalDate addedOn;
    private Double price;
    private Integer quantity;
    private Integer sellCount;
}