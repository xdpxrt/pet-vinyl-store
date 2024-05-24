package ru.xdpxrt.vinyl.dto.recordDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordDTO {
    private String title;
    private String description;
    @Min(1899)
    private Integer publicationYear;
    @Positive
    private Long performer;
    private String trackList;
    private List<Integer> genres;
}