package ru.xdpxrt.vinyl.genre.service;

import org.springframework.data.domain.PageRequest;
import ru.xdpxrt.vinyl.dto.genreDTO.FullGenreDTO;
import ru.xdpxrt.vinyl.dto.genreDTO.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO addGenre(GenreDTO genreDTO);

    List<GenreDTO> getGenres(PageRequest pageRequest);

    GenreDTO updateGenre(GenreDTO genreDTO, Long id);

    void deleteGenre(Long id);

    FullGenreDTO getGenre(Long id);
}