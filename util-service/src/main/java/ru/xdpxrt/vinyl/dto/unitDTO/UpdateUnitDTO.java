package ru.xdpxrt.vinyl.dto.unitDTO;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUnitDTO {
    private Double price;
    @Positive
    private Integer add;
    @Positive
    private Integer sell;
}