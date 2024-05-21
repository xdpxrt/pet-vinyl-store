package ru.xdpxrt.vinyl.country.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.country.mapper.CountryMapper;
import ru.xdpxrt.vinyl.country.model.Country;
import ru.xdpxrt.vinyl.country.repository.CountryRepository;
import ru.xdpxrt.vinyl.dto.countryDTO.CountryDTO;
import ru.xdpxrt.vinyl.dto.countryDTO.FullCountryDTO;
import ru.xdpxrt.vinyl.error.ConflictException;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.performer.mapper.PerformerMapper;
import ru.xdpxrt.vinyl.performer.model.Performer;
import ru.xdpxrt.vinyl.performer.repository.PerformerRepository;

import java.util.List;

import static ru.xdpxrt.vinyl.cons.Message.COUNTRY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final PerformerRepository performerRepository;

    private final CountryMapper countryMapper;
    private final PerformerMapper performerMapper;

    @Override
    public CountryDTO addCountry(CountryDTO countryDTO) {
        log.debug("Adding component {}", countryDTO);
        Country country = countryRepository.save(countryMapper.toCountry(countryDTO));
        log.debug("Country added {}", country);
        return countryMapper.toCountryDTO(country);
    }

    @Override
    public List<CountryDTO> getCountries(PageRequest pageRequest) {
        log.debug("Getting list of countries");
        return countryMapper.toCountryDTO(countryRepository.findAll(pageRequest).toList());
    }

    @Override
    public CountryDTO updateCountry(CountryDTO countryDTO, Long id) {
        log.debug("Updating country ID{}", id);
        Country country = getCountryIfExist(id);
        if (country.getName().equals(countryDTO.getName()))
            throw new ConflictException(String.format("Country ID%d already has name %s", id, country.getName()));
        country.setName(countryDTO.getName());
        country = countryRepository.save(country);
        log.debug("Country updated {}", country);
        return countryMapper.toCountryDTO(country);
    }

    @Override
    public void deleteCountry(Long id) {
        log.debug("Deleting country ID{}", id);
        getCountryIfExist(id);
        countryRepository.deleteById(id);
        log.debug("Country ID{} deleted", id);
    }

    @Override
    public FullCountryDTO getCountry(Long id) {
        log.debug("Getting country ID{}", id);
        FullCountryDTO fullCountryDTO = countryMapper.toFullCountryDTO(getCountryIfExist(id));
        //TODO fill FullCountryDTO with 10 most popular performers of this country
        List<Performer> performers = performerRepository.findAllByCountryIdOrderByName(id, Limit.of(10));
        fullCountryDTO.setPerformers(performerMapper.toShortPerformerDTO(performers));
        return fullCountryDTO;
    }

    private Country getCountryIfExist(Long id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(COUNTRY_NOT_FOUND, id)));
    }
}
