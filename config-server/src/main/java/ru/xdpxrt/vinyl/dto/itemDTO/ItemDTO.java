package ru.xdpxrt.vinyl.dto.itemDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long recordId;
    private String title;
    private Integer publicationYear;
    private String performerName;
    private Double price;
    private Integer quantity;
}