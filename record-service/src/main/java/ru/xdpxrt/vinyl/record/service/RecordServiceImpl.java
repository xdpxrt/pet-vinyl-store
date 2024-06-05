package ru.xdpxrt.vinyl.record.service;

import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.dto.recordDTO.FullRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.NewRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.UpdateRecordDTO;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.genre.model.Genre;
import ru.xdpxrt.vinyl.genre.repository.GenreRepository;
import ru.xdpxrt.vinyl.performer.model.Performer;
import ru.xdpxrt.vinyl.performer.repository.PerformerRepository;
import ru.xdpxrt.vinyl.record.mapper.RecordMapper;
import ru.xdpxrt.vinyl.record.model.Record;
import ru.xdpxrt.vinyl.record.repository.RecordRepository;
import ru.xdpxrt.vinyl.service.StorageFeignService;
import ru.xdpxrt.vinyl.unit.model.Unit;
import ru.xdpxrt.vinyl.unit.repository.UnitRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.xdpxrt.vinyl.cons.Message.*;
import static ru.xdpxrt.vinyl.util.Utilities.fromSizePage;

@Slf4j
@Service
@CacheConfig(cacheNames = "record")
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final PerformerRepository performerRepository;
    private final GenreRepository genreRepository;
    private final UnitRepository unitRepository;

    private final StorageFeignService storage;

    private final RecordMapper recordMapper;

    @Override
    @Transactional
    public FullRecordDTO addRecord(NewRecordDTO newRecordDTO, MultipartFile cover) {
        log.debug("Adding new record {}", newRecordDTO);
        Performer performer = getPerformerIfExist(newRecordDTO.getPerformer());
        Set<Genre> genres = new HashSet<>(getGenresIfExists(newRecordDTO.getGenres()));
        Record record = recordMapper.toRecord(newRecordDTO);
        record.setPerformer(performer);
        record.setGenres(genres);
        record.setImage(storage.upload(cover));
        record = recordRepository.save(record);
        Unit unit = Unit.builder()
                .record(record)
                .price(newRecordDTO.getPrice())
                .quantity(newRecordDTO.getQuantity())
                .addedOn(LocalDate.now())
                .sellCount(0)
                .build();
        unit = unitRepository.save(unit);
        record.setUnit(unit);
        log.debug("New record added {}", record);
        return toFullRecordDTO(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShortRecordDTO> getRecords(SortType sortType, String text, Long genreId, Integer fromYear,
                                           Integer toYear, Double fromPrice, Double toPrice, Boolean onlyAvailable,
                                           Integer from, Integer size) {
        log.debug("Getting list of records by params");
        List<Specification<Record>> s = new ArrayList<>();
        s.add(text.isBlank() ? null : titleAndPerformerContaining(text));
        s.add(genreId == null ? null : genreIdIs(genreId));
        s.add(fromYear == null ? null : publicationYearAfter(fromYear));
        s.add((toYear == null ? null : publicationYearBefore(toYear)));
        s.add(fromPrice == null ? null : priceIsGreater(fromPrice));
        s.add(toPrice == null ? null : priceIsLess(toPrice));
        s.add(!onlyAvailable ? null : onlyAvailable());
        s = s.stream().filter(Objects::nonNull).collect(Collectors.toList());
        Specification<Record> specification = s
                .stream()
                .reduce(Specification::and).orElse(null);
        Sort sort;
        switch (sortType) {
            case PERFORMER -> sort = Sort.by("performer_name");
            case TITLE -> sort = Sort.by("title");
            case YEAR -> sort = Sort.by("publication_year");
            case POPULAR -> sort = Sort.by("unit_sell_count");
            case PRICE -> sort = Sort.by("price");
            default -> sort = Sort.unsorted();
        }
        List<Record> records = recordRepository.findAll(specification != null ? specification
                : Specification.allOf(), fromSizePage(from, size, sort)).toList();
        return recordMapper.toShortRecordDTO(records);
    }

    @Override
    @Transactional
    @CachePut(key = "#id")
    public FullRecordDTO updateRecord(UpdateRecordDTO updateRecordDTO, MultipartFile cover, Long id) {
        log.debug("Updating record ID{}", id);
        Record record = getRecordIfExist(id);
        if (updateRecordDTO.getTitle() != null && !updateRecordDTO.getTitle().isBlank())
            record.setTitle(updateRecordDTO.getTitle());
        if (updateRecordDTO.getDescription() != null && !updateRecordDTO.getDescription().isBlank())
            record.setDescription(updateRecordDTO.getDescription());
        if (updateRecordDTO.getPublicationYear() != null)
            record.setPublicationYear(updateRecordDTO.getPublicationYear());
        if (updateRecordDTO.getTrackList() != null && !updateRecordDTO.getTrackList().isBlank())
            record.setTrackList(updateRecordDTO.getTrackList());
        if (updateRecordDTO.getPerformer() != null) {
            Performer performer = getPerformerIfExist(updateRecordDTO.getPerformer());
            record.setPerformer(performer);
        }
        if (updateRecordDTO.getGenres() != null) {
            Set<Genre> genres = getGenresIfExists(updateRecordDTO.getGenres());
            record.setGenres(genres);
        }
        if (cover != null && !cover.isEmpty()) {
            storage.delete(record.getImage());
            record.setImage(storage.upload(cover));
        }
        record = recordRepository.save(record);
        log.debug("Record ID{} updated {}", id, record);
        return toFullRecordDTO(record);
    }

    @Override
    @Transactional
    @Cacheable(key = "#id")
    public void deleteRecord(Long id) {
        log.debug("Deleting record ID{}", id);
        getRecordIfExist(id);
        recordRepository.deleteById(id);
        log.debug("Record ID{} deleted", id);
    }

    @Override
    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public FullRecordDTO getRecord(Long id) {
        log.debug("Getting record ID{}", id);
        Record record = getRecordIfExist(id);
        return toFullRecordDTO(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShortRecordDTO> getRecordsByIds(List<Long> ids) {
        log.debug("Getting list of records IDs{}", ids);
        List<Record> records = recordRepository.findAllById(ids);
        return recordMapper.toShortRecordDTO(records);
    }

    private FullRecordDTO toFullRecordDTO(Record record) {
        FullRecordDTO recordDTO = recordMapper.toFullRecordDTO(record);
        byte[] image = storage.download(record.getImage());
        recordDTO.setImage(Base64.getEncoder().encodeToString(image));
        return recordDTO;
    }

    private Record getRecordIfExist(Long id) {
        return recordRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RECORD_NOT_FOUND, id)));
    }

    private Performer getPerformerIfExist(Long id) {
        return performerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(PERFORMER_NOT_FOUND, id)));
    }

    private Set<Genre> getGenresIfExists(List<Integer> ids) {
        List<Genre> genres = genreRepository.findAllByIdIn(ids);
        if (genres.isEmpty()) throw new NotFoundException(String.format(GENRE_NOT_FOUND, ids));
        return new HashSet<>(genres);
    }

    private Specification<Record> titleAndPerformerContaining(String text) {
        String searchText = "%" + text.toLowerCase() + "%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), searchText),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("performer").get("name")), searchText));
    }

    private Specification<Record> publicationYearAfter(Integer year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get("publicationYear"), year);
    }

    private Specification<Record> publicationYearBefore(Integer year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get("publicationYear"), year);
    }

    private Specification<Record> priceIsGreater(Double price) {
        List<Long> ids = unitRepository.findAllByPriceGreaterThanEqual(price)
                .stream()
                .map(Unit::getId)
                .toList();
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("id")).value(ids);
    }

    private Specification<Record> priceIsLess(Double price) {
        List<Long> ids = unitRepository.findAllByPriceLessThanEqual(price)
                .stream()
                .map(Unit::getId)
                .toList();
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("id")).value(ids);
    }

    private Specification<Record> onlyAvailable() {
        List<Long> ids = unitRepository.findAllByQuantityGreaterThan(0)
                .stream()
                .map(Unit::getId)
                .toList();
        return ((root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("id")).value(ids));
    }

    private Specification<Record> genreIdIs(Long genreId) {
        return (root, query, criteriaBuilder) -> {
            Root<Genre> genre = query.from(Genre.class);
            return criteriaBuilder.equal(genre.get("id"), genreId);
        };
    }
}