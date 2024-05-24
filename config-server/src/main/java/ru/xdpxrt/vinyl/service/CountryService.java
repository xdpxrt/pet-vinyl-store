package ru.xdpxrt.vinyl.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.dto.countryDTO.CountryDTO;
import ru.xdpxrt.vinyl.dto.countryDTO.FullCountryDTO;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.URI.COUNTRY_URI;
import static ru.xdpxrt.vinyl.cons.URI.ID_URI;

@Service
@FeignClient("record-service")
public interface CountryService {
    @PostMapping(COUNTRY_URI)
    @ResponseStatus(HttpStatus.CREATED)
    public CountryDTO addCountry(@RequestBody @Valid CountryDTO countryDTO);

    @GetMapping(COUNTRY_URI)
    public List<CountryDTO> getCountries(@RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "20") Integer size);

    @PatchMapping(COUNTRY_URI + ID_URI)
    public CountryDTO updateCountry(@RequestBody @Valid CountryDTO countryDTO,
                                    @PathVariable @Positive Integer id);

    @DeleteMapping(COUNTRY_URI + ID_URI)
    public void deleteCountry(@PathVariable @Positive Integer id);

    @GetMapping(COUNTRY_URI + ID_URI)
    public FullCountryDTO getCountry(@PathVariable @Positive Integer id);
}