package ru.xdpxrt.vinyl.record.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.recordDTO.FullRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.NewRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.record.model.Record;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "performer", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "image", ignore = true)
    Record toRecord(NewRecordDTO newRecordDTO);

    @Mapping(target = "price", expression = "java(record.getUnit().getPrice())")
    @Mapping(target = "quantity", expression = "java(record.getUnit().getQuantity())")
    FullRecordDTO toFullRecordDTO(Record record);

    @Mapping(target = "price", expression = "java(record.getUnit().getPrice())")
    @Mapping(target = "quantity", expression = "java(record.getUnit().getQuantity())")
    ShortRecordDTO toShortRecordDTO(Record record);

    List<ShortRecordDTO> toShortRecordDTO(List<Record> record);
}