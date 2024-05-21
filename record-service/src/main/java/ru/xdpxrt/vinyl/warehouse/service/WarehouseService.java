package ru.xdpxrt.vinyl.warehouse.service;

import ru.xdpxrt.vinyl.dto.unitDTO.UnitDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UpdateUnitDTO;

public interface WarehouseService {

    UnitDTO updateUnit(UpdateUnitDTO updateUnitDTO);

    UnitDTO getUitByRecordId(Long recordId);
}