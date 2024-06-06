package ru.xdpxrt.vinyl.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.dto.recordDTO.FullRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UnitDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UpdateUnitDTO;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.*;

@Validated
@FeignClient("RECORD-SERVICE")
public interface RecordFeignService {

    @GetMapping(RECORD_URI + ID_URI)
    FullRecordDTO getRecord(@PathVariable @Positive Long id);

    @GetMapping(RECORD_URI + IDS_URI)
    public List<ShortRecordDTO> getRecordsByIds(@RequestParam List<@Positive Long> ids);

    @PutMapping(UNIT_URI + ID_URI)
    public UnitDTO updateUnit(@RequestBody @Valid UpdateUnitDTO updateUnitDTO,
                              @PathVariable @Positive Long id);

    @GetMapping(UNIT_URI + ID_URI)
    public UnitDTO getUnit(@PathVariable @Positive Long id);
}