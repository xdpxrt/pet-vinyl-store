package ru.xdpxrt.vinyl.unit.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.dto.unitDTO.UnitDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UpdateUnitDTO;
import ru.xdpxrt.vinyl.unit.service.UnitService;

import static ru.xdpxrt.vinyl.cons.URI.ID_URI;
import static ru.xdpxrt.vinyl.cons.URI.UNIT_URI;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(UNIT_URI + ID_URI)
public class UnitController {
    private final UnitService unitService;

    @PutMapping
    public UnitDTO updateUnit(@RequestBody @Valid UpdateUnitDTO updateUnitDTO,
                              @PathVariable @Positive Long id) {
        log.info("Response from PATCH request on {}/{}", UNIT_URI, id);
        return unitService.updateUnit(updateUnitDTO, id);
    }

    @GetMapping
    public UnitDTO getUnit(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}", UNIT_URI, id);
        return unitService.getUit(id);
    }
}