package ru.xdpxrt.vinyl.genre.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.xdpxrt.vinyl.dto.genreDTO.FullGenreDTO;
import ru.xdpxrt.vinyl.dto.genreDTO.GenreDTO;
import ru.xdpxrt.vinyl.genre.model.Genre;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "records", ignore = true)
    Genre toGenre(GenreDTO genreDTO);

    GenreDTO toGenreDTO(Genre genre);

    List<GenreDTO> toGenreDTO(List<Genre> genre);

    @Mapping(target = "records", ignore = true)
    FullGenreDTO toFullGenreDTO(Genre genre);
}