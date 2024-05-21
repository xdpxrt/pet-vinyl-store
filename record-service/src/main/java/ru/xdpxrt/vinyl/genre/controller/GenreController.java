package ru.xdpxrt.vinyl.genre.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.dto.genreDTO.FullGenreDTO;
import ru.xdpxrt.vinyl.dto.genreDTO.GenreDTO;
import ru.xdpxrt.vinyl.genre.service.GenreService;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.GENRE_URI;
import static ru.xdpxrt.vinyl.cons.URI.ID_URI;
import static ru.xdpxrt.vinyl.util.Utilities.fromSizePage;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(GENRE_URI)
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO addGenre(@RequestBody @Valid GenreDTO genreDTO) {
        log.info("Response from POST request on {}", GENRE_URI);
        return genreService.addGenre(genreDTO);
    }

    @GetMapping
    public List<GenreDTO> getGenres(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "20") Integer size) {
        log.info("Response from GET request on {}", GENRE_URI);
        return genreService.getGenres(fromSizePage(from, size, "name"));
    }

    @PatchMapping(ID_URI)
    public GenreDTO updateGenre(@RequestBody @Valid GenreDTO genreDTO,
                                @PathVariable @Positive Long id) {
        log.info("Response from PATCH request on {}/{}", GENRE_URI, id);
        return genreService.updateGenre(genreDTO, id);
    }

    @DeleteMapping(ID_URI)
    public void deleteGenre(@PathVariable @Positive Long id) {
        log.info("Response from DELETE request on {}/{}", GENRE_URI, id);
        genreService.deleteGenre(id);
    }

    @GetMapping(ID_URI)
    public FullGenreDTO getGenre(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}", GENRE_URI, id);
        return genreService.getGenre(id);
    }
}
