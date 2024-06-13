package ru.xdpxrt.vinyl.performer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.country.model.Country;
import ru.xdpxrt.vinyl.country.repository.CountryRepository;
import ru.xdpxrt.vinyl.dto.performerDTO.FullPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.NewPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.UpdatePerformerDTO;
import ru.xdpxrt.vinyl.handler.BadRequestException;
import ru.xdpxrt.vinyl.handler.NotFoundException;
import ru.xdpxrt.vinyl.performer.mapper.PerformerMapper;
import ru.xdpxrt.vinyl.performer.model.Performer;
import ru.xdpxrt.vinyl.performer.repository.PerformerRepository;
import ru.xdpxrt.vinyl.record.mapper.RecordMapper;
import ru.xdpxrt.vinyl.record.model.Record;
import ru.xdpxrt.vinyl.record.repository.RecordRepository;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.Message.COUNTRY_NOT_FOUND;
import static ru.xdpxrt.vinyl.cons.Message.PERFORMER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformerServiceImpl implements PerformerService {
    private final PerformerRepository performerRepository;
    private final CountryRepository countryRepository;
    private final RecordRepository recordRepository;

    private final PerformerMapper performerMapper;
    private final RecordMapper recordMapper;

    @Override
    @Transactional
    public FullPerformerDTO addPerformer(NewPerformerDTO newPerformerDTO) {
        log.debug("Adding performer {}", newPerformerDTO);
        Country country = getCountryIfExist(newPerformerDTO.getCountryId());
        Performer performer = performerMapper.toPerformer(newPerformerDTO);
        performer.setCountry(country);
        performer = performerRepository.save(performer);
        log.debug("New performer added {}", performer);
        return performerMapper.toFullPerformerDTO(performer);
    }

    @Override
    @Transactional
    public List<ShortPerformerDTO> getPerformers(String text, PageRequest pageRequest, SortType sortType) {
        log.debug("Getting list of performers");
        List<ShortPerformerDTO> performers;
        switch (sortType) {
            case PERFORMER -> performers = text.isBlank() ? performerRepository.findAllOrderBySellCountDesc(pageRequest)
                    : performerRepository.findAllNameContainingOrderBySellCountDesc(text, pageRequest);
            case POPULAR -> performers = text.isBlank() ? performerRepository.findAllOrderByName(pageRequest)
                    : performerRepository.findAllNameContainingOrderByName(text, pageRequest);
            default -> throw new BadRequestException(String.format("Wrong type of sorting %s", sortType));
        }
        return performers;
    }

    @Override
    @Transactional
    public FullPerformerDTO updatePerformer(UpdatePerformerDTO updatePerformerDTO, Long id) {
        log.debug("Updating performer ID{}", id);
        Performer performer = getPerformerIfExist(id);
        if (updatePerformerDTO.getName() != null && !updatePerformerDTO.getName().isBlank())
            performer.setName(updatePerformerDTO.getName());
        if (updatePerformerDTO.getBio() != null && !updatePerformerDTO.getBio().isBlank())
            performer.setBio(updatePerformerDTO.getBio());
        if (updatePerformerDTO.getCountryId() != null)
            performer.setCountry(getCountryIfExist(updatePerformerDTO.getCountryId()));
        performer = performerRepository.save(performer);
        log.debug("Performer ID{} updated", id);
        return fillPerformerWithRecords(performerMapper.toFullPerformerDTO(performer));
    }

    @Override
    @Transactional
    public void deletePerformer(Long id) {
        log.debug("Deleting performer ID{}", id);
        getPerformerIfExist(id);
        performerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public FullPerformerDTO getPerformer(Long id) {
        log.debug("Getting performer ID{}", id);
        Performer performer = getPerformerIfExist(id);
        return fillPerformerWithRecords(performerMapper.toFullPerformerDTO(performer));
    }

    private Country getCountryIfExist(Integer id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(COUNTRY_NOT_FOUND, id)));
    }

    private Performer getPerformerIfExist(Long id) {
        return performerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(PERFORMER_NOT_FOUND, id)));
    }

    private FullPerformerDTO fillPerformerWithRecords(FullPerformerDTO performer) {
        List<Record> records = recordRepository.findAllByPerformerIdOrderByPublicationYear(performer.getId());
        performer.setRecords(recordMapper.toShortRecordDTO(records));
        return performer;
    }
}