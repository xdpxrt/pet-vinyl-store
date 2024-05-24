package ru.xdpxrt.vinyl.unit.service;

import ru.xdpxrt.vinyl.dto.unitDTO.UnitDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UpdateUnitDTO;

public interface UnitService {

    UnitDTO updateUnit(UpdateUnitDTO updateUnitDTO, Long id);

    UnitDTO getUit(Long id);
}