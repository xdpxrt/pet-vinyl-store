package ru.xdpxrt.vinyl.country.service;


import ru.xdpxrt.vinyl.CountryDTO;

import java.util.List;

public interface CountryService {
    CountryDTO addCountry(CountryDTO countryDTO);

    List<CountryDTO> getCountries();

    CountryDTO updateCountry(CountryDTO countryDTO);

    void deleteCountry(Long id);


}