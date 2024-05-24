package ru.xdpxrt.vinyl.genre.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.dto.genreDTO.FullGenreDTO;
import ru.xdpxrt.vinyl.dto.genreDTO.GenreDTO;
import ru.xdpxrt.vinyl.error.ConflictException;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.genre.mapper.GenreMapper;
import ru.xdpxrt.vinyl.genre.model.Genre;
import ru.xdpxrt.vinyl.genre.repository.GenreRepository;
import ru.xdpxrt.vinyl.record.mapper.RecordMapper;
import ru.xdpxrt.vinyl.record.model.Record;
import ru.xdpxrt.vinyl.record.repository.RecordRepository;

import java.util.HashSet;
import java.util.List;

import static ru.xdpxrt.vinyl.cons.Message.GENRE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final RecordRepository recordRepository;

    private final GenreMapper genreMapper;
    private final RecordMapper recordMapper;


    @Override
    @Transactional
    public GenreDTO addGenre(GenreDTO genreDTO) {
        log.debug("Adding genre {}", genreDTO);
        Genre genre = genreRepository.save(genreMapper.toGenre(genreDTO));
        log.debug("New genre added {}", genre);
        return genreMapper.toGenreDTO(genre);
    }

    @Override
    @Transactional
    public List<GenreDTO> getGenres(PageRequest pageRequest) {
        log.debug("Getting list of genres");
        return genreMapper.toGenreDTO(genreRepository.findAll(pageRequest).toList());
    }

    @Override
    @Transactional
    public GenreDTO updateGenre(GenreDTO genreDTO, Integer id) {
        log.debug("Updating genre ID{}", id);
        Genre genre = getGenreIfExist(id);
        if (genre.getName().equals(genreDTO.getName()))
            throw new ConflictException(String.format("Genre ID%d already has name %s", id, genre.getName()));
        genre.setName(genreDTO.getName());
        genre = genreRepository.save(genre);
        log.debug("Genre ID{} updated", id);
        return genreMapper.toGenreDTO(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Integer id) {
        log.debug("Deleting genre ID{}", id);
        getGenreIfExist(id);
        genreRepository.deleteById(id);
        log.debug("Genre ID{} deleted", id);
    }

    @Override
    @Transactional
    public FullGenreDTO getGenre(Integer id) {
        log.debug("Getting genre ID{}", id);
        FullGenreDTO genre = genreMapper.toFullGenreDTO(getGenreIfExist(id));
        List<Record> records = recordRepository.findAllByGenresIdOrderByUnitSellCountDesc(id, Limit.of(10));
        genre.setRecords(new HashSet<>(recordMapper.toShortRecordDTO(records)));
        return genre;
    }

    private Genre getGenreIfExist(Integer id) {
        return genreRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(GENRE_NOT_FOUND, id)));
    }
}
