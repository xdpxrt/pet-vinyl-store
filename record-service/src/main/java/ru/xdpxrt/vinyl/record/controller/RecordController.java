package ru.xdpxrt.vinyl.record.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.dto.recordDTO.FullRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.NewRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.UpdateRecordDTO;
import ru.xdpxrt.vinyl.record.service.RecordService;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.ID_URI;
import static ru.xdpxrt.vinyl.cons.URI.RECORD_URI;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(RECORD_URI)
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullRecordDTO addRecord(@RequestBody @Valid NewRecordDTO newRecordDTO) {
        log.info("Response from POST request on {}", RECORD_URI);
        return recordService.addRecord(newRecordDTO);
    }

    @GetMapping
    public List<ShortRecordDTO> getRecords(@RequestParam(required = false) SortType sortType,
                                           @RequestParam(defaultValue = "") String text,
                                           @RequestParam(required = false) Long genreId,
                                           @RequestParam(required = false) Integer fromYear,
                                           @RequestParam(required = false) Integer toYear,
                                           @RequestParam(required = false) Double fromPrice,
                                           @RequestParam(required = false) Double toPrice,
                                           @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "20") Integer size) {
        log.info("Response from GET request on {}", RECORD_URI);
        return recordService.getRecords(sortType, text, genreId, fromYear, toYear, fromPrice, toPrice, onlyAvailable,
                from, size);
    }

    @PatchMapping(ID_URI)
    public FullRecordDTO updateRecord(@RequestBody @Valid UpdateRecordDTO updateRecordDTO,
                                      @PathVariable @Positive Long id) {
        log.info("Response from PATCH request on {}/{}", RECORD_URI, id);
        return recordService.updateRecord(updateRecordDTO, id);
    }

    @DeleteMapping(ID_URI)
    public void deleteRecord(@PathVariable @Positive Long id) {
        log.info("Response from DELETE request on {}/{}", RECORD_URI, id);
        recordService.deleteRecord(id);
    }

    @GetMapping(ID_URI)
    FullRecordDTO getRecord(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}", RECORD_URI, id);
        return recordService.getRecord(id);
    }
}