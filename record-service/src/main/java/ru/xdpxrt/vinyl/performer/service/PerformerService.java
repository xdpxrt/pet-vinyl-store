package ru.xdpxrt.vinyl.performer.service;

import org.springframework.data.domain.PageRequest;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.dto.performerDTO.FullPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.NewPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.UpdatePerformerDTO;

import java.util.List;

public interface PerformerService {
    FullPerformerDTO addPerformer(NewPerformerDTO newPerformerDTO);

    List<ShortPerformerDTO> getPerformers(String text, Integer from, Integer size, SortType sortType);

    FullPerformerDTO updatePerformer(UpdatePerformerDTO updatePerformerDTO, Long id);

    void deletePerformer(Long id);

    FullPerformerDTO getPerformer(Long id);
}