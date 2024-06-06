package ru.xdpxrt.vinyl.unit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.dto.unitDTO.UnitDTO;
import ru.xdpxrt.vinyl.dto.unitDTO.UpdateUnitDTO;
import ru.xdpxrt.vinyl.handler.NotFoundException;
import ru.xdpxrt.vinyl.unit.mapper.UnitMapper;
import ru.xdpxrt.vinyl.unit.model.Unit;
import ru.xdpxrt.vinyl.unit.repository.UnitRepository;

import static ru.xdpxrt.vinyl.cons.Message.UNIT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    public UnitDTO updateUnit(UpdateUnitDTO updateUnitDTO, Long id) {
        log.debug("Updating unit ID{}", id);
        Unit unit = getUnitIfExist(id);
        if (updateUnitDTO.getPrice() != null) unit.setPrice(updateUnitDTO.getPrice());
        if (updateUnitDTO.getAdd() != null) unit.setQuantity(unit.getQuantity() + updateUnitDTO.getAdd());
        if (updateUnitDTO.getSell() != null) {
            unit.setSellCount(unit.getSellCount() + updateUnitDTO.getSell());
            unit.setQuantity(unit.getQuantity() - updateUnitDTO.getSell());
        }
        unit = unitRepository.save(unit);
        log.debug("Unit ID{} updated", id);
        return unitMapper.toUnitDTO(unit);
    }

    @Override
    public UnitDTO getUit(Long id) {
        log.debug("Getting unit ID{}", id);
        return unitMapper.toUnitDTO(getUnitIfExist(id));
    }

    private Unit getUnitIfExist(Long id) {
        return unitRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(UNIT_NOT_FOUND, id)));
    }
}