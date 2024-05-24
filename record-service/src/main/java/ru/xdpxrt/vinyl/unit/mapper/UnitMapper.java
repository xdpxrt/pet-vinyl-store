package ru.xdpxrt.vinyl.unit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.unitDTO.UnitDTO;
import ru.xdpxrt.vinyl.unit.model.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    @Mapping(target = "recordId", expression = "java(unit.getRecord().getId())")
    UnitDTO toUnitDTO(Unit unit);
}