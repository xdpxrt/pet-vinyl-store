package ru.xdpxrt.vinyl.dto.performerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortPerformerDTO {
    private Long id;
    private String name;
}