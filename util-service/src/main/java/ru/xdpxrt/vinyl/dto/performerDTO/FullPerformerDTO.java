package ru.xdpxrt.vinyl.dto.performerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.countryDTO.CountryDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullPerformerDTO {
    private Long id;
    private String name;
    private String bio;
    private CountryDTO country;
    private List<ShortRecordDTO> records;
}