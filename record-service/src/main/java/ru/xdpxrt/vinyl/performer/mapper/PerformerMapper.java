package ru.xdpxrt.vinyl.performer.mapper;

import org.mapstruct.Mapper;
import ru.xdpxrt.vinyl.dto.performerDTO.FullPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.NewPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.performer.model.Performer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerformerMapper {
    Performer toPerformer(NewPerformerDTO newPerformerDTO);

    ShortPerformerDTO toShortPerformerDTO(Performer performer);

    List<ShortPerformerDTO> toShortPerformerDTO(List<Performer> performer);

    FullPerformerDTO toFullPerformerDTO(Performer performer);
}