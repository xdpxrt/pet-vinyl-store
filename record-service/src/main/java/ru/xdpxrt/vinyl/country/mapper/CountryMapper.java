package ru.xdpxrt.vinyl.country.mapper;

import org.mapstruct.Mapper;
import ru.xdpxrt.vinyl.country.model.Country;
import ru.xdpxrt.vinyl.dto.countryDTO.CountryDTO;
import ru.xdpxrt.vinyl.dto.countryDTO.FullCountryDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toCountry(CountryDTO countryDTO);

    FullCountryDTO toFullCountryDTO(Country country);

    CountryDTO toCountryDTO(Country country);

    List<CountryDTO> toCountryDTO(List<Country> country);
}