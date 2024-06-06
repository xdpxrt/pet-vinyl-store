package ru.xdpxrt.vinyl.performer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.performerDTO.FullPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.NewPerformerDTO;
import ru.xdpxrt.vinyl.performer.model.Performer;

@Mapper(componentModel = "spring")
public interface PerformerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", ignore = true)
    Performer toPerformer(NewPerformerDTO newPerformerDTO);

    @Mapping(target = "records", ignore = true)
    FullPerformerDTO toFullPerformerDTO(Performer performer);
}