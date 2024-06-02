package ru.xdpxrt.vinyl.dto.countryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullCountryDTO {
    private Integer id;
    private String name;
    private List<ShortPerformerDTO> performers;
}