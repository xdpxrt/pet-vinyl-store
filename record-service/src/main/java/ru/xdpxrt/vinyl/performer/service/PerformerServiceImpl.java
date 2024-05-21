package ru.xdpxrt.vinyl.performer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.country.model.Country;
import ru.xdpxrt.vinyl.country.repository.CountryRepository;
import ru.xdpxrt.vinyl.dto.performerDTO.FullPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.NewPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.UpdatePerformerDTO;
import ru.xdpxrt.vinyl.error.BadRequestException;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.performer.mapper.PerformerMapper;
import ru.xdpxrt.vinyl.performer.model.Performer;
import ru.xdpxrt.vinyl.performer.repository.PerformerRepository;
import ru.xdpxrt.vinyl.record.mapper.RecordMapper;
import ru.xdpxrt.vinyl.record.model.Record;
import ru.xdpxrt.vinyl.record.repository.RecordRepository;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.Message.COUNTRY_NOT_FOUND;
import static ru.xdpxrt.vinyl.cons.Message.PERFORMER_NOT_FOUND;
import static ru.xdpxrt.vinyl.util.Utilities.fromSizePage;

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
    public List<ShortPerformerDTO> getPerformers(String text, Integer from, Integer size, SortType sortType) {
        log.debug("Getting list of performers");
        //TODO sort by popular
        String sort = switch (sortType) {
            case PERFORMER -> "name";
            case POPULAR -> "popular";
            default -> throw new BadRequestException(String.format("Wring type of sorting %s", sortType));
        };
        List<Performer> performers;
        if (text != null && !text.isBlank()) {
            String searchText = "%" + text.toLowerCase() + "%";
            Specification<Performer> s = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchText));
            performers = performerRepository.findAll(s, fromSizePage(from, size, sort)).toList();
        } else performers = performerRepository.findAll(fromSizePage(from, size, sort)).toList();
        return performerMapper.toShortPerformerDTO(performers);
    }

    @Override
    public FullPerformerDTO updatePerformer(UpdatePerformerDTO updatePerformerDTO, Long id) {
        log.debug("Updating performer ID{}", id);
        Performer performer = getPerformerIfExist(id);
        if (updatePerformerDTO.getName() != null && !updatePerformerDTO.getName().isBlank())
            performer.setName(updatePerformerDTO.getName());
        if (updatePerformerDTO.getBio() != null && !updatePerformerDTO.getBio().isBlank())
            performer.setBio(updatePerformerDTO.getBio());
        if (updatePerformerDTO.getCountryId() != null)
            performer.setCountry(getCountryIfExist(id));
        performer = performerRepository.save(performer);
        log.debug("Performer ID{} updated", id);
        return fillPerformerWithRecords(performerMapper.toFullPerformerDTO(performer));
    }

    @Override
    public void deletePerformer(Long id) {
        log.debug("Deleting performer ID{}", id);
        getPerformerIfExist(id);
        performerRepository.deleteById(id);
    }

    @Override
    public FullPerformerDTO getPerformer(Long id) {
        log.debug("Getting performer ID{}", id);
        Performer performer = getPerformerIfExist(id);
        return fillPerformerWithRecords(performerMapper.toFullPerformerDTO(performer));
    }

    private Country getCountryIfExist(Long id) {
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