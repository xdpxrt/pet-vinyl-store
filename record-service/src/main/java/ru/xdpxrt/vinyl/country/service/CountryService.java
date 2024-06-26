package ru.xdpxrt.vinyl.country.service;

import org.springframework.data.domain.PageRequest;
import ru.xdpxrt.vinyl.dto.countryDTO.CountryDTO;
import ru.xdpxrt.vinyl.dto.countryDTO.FullCountryDTO;

import java.util.List;

public interface CountryService {
    CountryDTO addCountry(CountryDTO countryDTO);

    List<CountryDTO> getCountries(PageRequest pageRequest);

    CountryDTO updateCountry(CountryDTO countryDTO, Integer id);

    void deleteCountry(Integer id);

    FullCountryDTO getCountry(Integer id);
}