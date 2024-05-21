package ru.xdpxrt.vinyl.dto.performerDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPerformerDTO {
    @NotBlank
    @Size(max = 64)
    private String name;
    @NotBlank
    @Size(max = 512)
    private String bio;
    @NotNull
    private Long countryId;
}