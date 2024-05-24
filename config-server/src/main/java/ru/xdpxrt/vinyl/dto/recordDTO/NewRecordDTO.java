package ru.xdpxrt.vinyl.dto.recordDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewRecordDTO {
    @NotBlank
    @Size(max = 64)
    private String title;
    @NotNull
    @Min(1899)
    private Integer publicationYear;
    @NotBlank
    @Size(max = 512)
    private String description;
    @NotBlank
    @Size(max = 1024)
    private String trackList;
    @NotNull
    @Positive
    private Long performer;
    @NotNull
    private List<Integer> genres;
    @NotNull
    @Positive
    private Double price;
    @NotNull
    @PositiveOrZero
    private Integer quantity;
}