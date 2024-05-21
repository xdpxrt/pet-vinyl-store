package ru.xdpxrt.vinyl.country.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.country.service.CountryService;
import ru.xdpxrt.vinyl.dto.countryDTO.CountryDTO;
import ru.xdpxrt.vinyl.dto.countryDTO.FullCountryDTO;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.COUNTRY_URI;
import static ru.xdpxrt.vinyl.cons.URI.ID_URI;
import static ru.xdpxrt.vinyl.util.Utilities.fromSizePage;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(COUNTRY_URI)
public class CountryController {
    public final CountryService countryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CountryDTO addCountry(@RequestBody @Valid CountryDTO countryDTO) {
        log.info("Response from POST request on {}", COUNTRY_URI);
        return countryService.addCountry(countryDTO);
    }

    @GetMapping
    public List<CountryDTO> getCountries(@RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "20") Integer size) {
        log.info("Response from GET request on {}", COUNTRY_URI);
        return countryService.getCountries(fromSizePage(from, size, "name"));
    }

    @PatchMapping(ID_URI)
    public CountryDTO updateCountry(@RequestBody @Valid CountryDTO countryDTO,
                                    @PathVariable @Positive Long id) {
        log.info("Response from PATCH request on {}/{}", COUNTRY_URI, id);
        return countryService.updateCountry(countryDTO, id);
    }

    @DeleteMapping(ID_URI)
    public void deleteCountry(@PathVariable @Positive Long id) {
        log.info("Response from DELETE request on {}/{}", COUNTRY_URI, id);
        countryService.deleteCountry(id);
    }

    @GetMapping(ID_URI)
    public FullCountryDTO getCountry(@PathVariable @Positive Long id) {
        log.info("Response from GET request on {}/{}", COUNTRY_URI, id);
        return countryService.getCountry(id);
    }
}