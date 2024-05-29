package ru.xdpxrt.vinyl.dto.itemDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortItemDTO {
    @NotNull
    @Positive
    private Long recordId;
    @NotNull
    @Positive
    private Integer pcs;
}