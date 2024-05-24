package ru.xdpxrt.vinyl.country.service;

import jakarta.transaction.Transactional;
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
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.error.ConflictException;
import ru.xdpxrt.vinyl.error.NotFoundException;
import ru.xdpxrt.vinyl.performer.mapper.PerformerMapper;
import ru.xdpxrt.vinyl.performer.model.Performer;
import ru.xdpxrt.vinyl.performer.repository.PerformerRepository;

import java.beans.Transient;
import java.util.List;

import static ru.xdpxrt.vinyl.cons.Message.COUNTRY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final PerformerRepository performerRepository;

    private final CountryMapper countryMapper;

    @Override
    @Transactional
    public CountryDTO addCountry(CountryDTO countryDTO) {
        log.debug("Adding component {}", countryDTO);
        Country country = countryRepository.save(countryMapper.toCountry(countryDTO));
        log.debug("Country added {}", country);
        return countryMapper.toCountryDTO(country);
    }

    @Override
    @Transactional
    public List<CountryDTO> getCountries(PageRequest pageRequest) {
        log.debug("Getting list of countries");
        return countryMapper.toCountryDTO(countryRepository.findAll(pageRequest).toList());
    }

    @Override
    @Transactional
    public CountryDTO updateCountry(CountryDTO countryDTO, Integer id) {
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
    @Transactional
    public void deleteCountry(Integer id) {
        log.debug("Deleting country ID{}", id);
        getCountryIfExist(id);
        countryRepository.deleteById(id);
        log.debug("Country ID{} deleted", id);
    }

    @Override
    @Transactional
    public FullCountryDTO getCountry(Integer id) {
        log.debug("Getting country ID{}", id);
        FullCountryDTO fullCountryDTO = countryMapper.toFullCountryDTO(getCountryIfExist(id));
        List<ShortPerformerDTO> performers =
                performerRepository.findAllOrderBySellCountDesc(PageRequest.of(0, 10));
        fullCountryDTO.setPerformers(performers);
        return fullCountryDTO;
    }

    private Country getCountryIfExist(Integer id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(COUNTRY_NOT_FOUND, id)));
    }
}
