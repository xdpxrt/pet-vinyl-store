package ru.xdpxrt.vinyl.genre.service;

import org.springframework.data.domain.PageRequest;
import ru.xdpxrt.vinyl.dto.genreDTO.FullGenreDTO;
import ru.xdpxrt.vinyl.dto.genreDTO.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO addGenre(GenreDTO genreDTO);

    List<GenreDTO> getGenres(PageRequest pageRequest);

    GenreDTO updateGenre(GenreDTO genreDTO, Integer id);

    void deleteGenre(Integer id);

    FullGenreDTO getGenre(Integer id);
}