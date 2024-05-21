package ru.xdpxrt.vinyl.dto.genreDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGenreDTO {
    private Integer id;
    private String name;
    private Set<ShortRecordDTO> records;
}