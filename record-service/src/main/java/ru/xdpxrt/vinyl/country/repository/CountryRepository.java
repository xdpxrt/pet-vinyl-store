package ru.xdpxrt.vinyl.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdpxrt.vinyl.country.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}