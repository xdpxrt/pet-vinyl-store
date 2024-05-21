package ru.xdpxrt.vinyl.dto.recordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordDTO {
    private String title;
    private Integer publicationYear;
    private String description;
    private Long performer;
    private String trackList;
    private Set<Long> genres;
}