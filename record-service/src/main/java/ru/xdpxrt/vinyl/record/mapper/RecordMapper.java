package ru.xdpxrt.vinyl.record.mapper;

import org.mapstruct.Mapper;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.record.model.Record;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    ShortRecordDTO toShortRecordDTO(Record record);

    List<ShortRecordDTO> toShortRecordDTO(List<Record> record);
}