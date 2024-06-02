package ru.xdpxrt.vinyl.dto.recordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortRecordDTO {
    private Long id;
    private String title;
    private Integer publicationYear;
    private ShortPerformerDTO performer;
    private Double price;
    private Integer quantity;
}