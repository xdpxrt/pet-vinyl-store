package ru.xdpxrt.vinyl.performer.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.performer.service.PerformerService;
import ru.xdpxrt.vinyl.dto.performerDTO.FullPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.NewPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.UpdatePerformerDTO;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.ID_URI;
import static ru.xdpxrt.vinyl.cons.URI.PERFORMER_URI;
import static ru.xdpxrt.vinyl.util.Utilities.fromSizePage;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(PERFORMER_URI)
public class PerformerController {
    private final PerformerService performerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullPerformerDTO addPerformer(@RequestBody @Valid NewPerformerDTO newPerformerDTO) {
        log.info("Response from POST request on {}", PERFORMER_URI);
        return performerService.addPerformer(newPerformerDTO);
    }

    @GetMapping
    public List<ShortPerformerDTO> getPerformers(@RequestParam(defaultValue = "") String text,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "20") Integer size,
                                                 @RequestParam(required = false) SortType sortType) {
        log.info("Response from GET request on {}", PERFORMER_URI);
        return performerService.getPerformers(text, fromSizePage(from, size), sortType);
    }

    @PatchMapping(ID_URI)
    public FullPerformerDTO updatePerformer(@RequestBody @Valid UpdatePerformerDTO updatePerformerDTO,
                                            @RequestParam @Positive Long id) {
        log.info("Response from PATCH request on {}/{}", PERFORMER_URI, id);
        return performerService.updatePerformer(updatePerformerDTO, id);
    }

    @DeleteMapping(ID_URI)
    void deletePerformer(@RequestParam @Positive Long id) {
        log.info("Response from DELETE request on {}/{}", PERFORMER_URI, id);
        performerService.deletePerformer(id);
    }

    @GetMapping(ID_URI)
    public FullPerformerDTO getPerformer(@RequestParam @Positive Long id) {
        log.info("Response from GET request on {}/{}", PERFORMER_URI, id);
        return performerService.getPerformer(id);
    }
}