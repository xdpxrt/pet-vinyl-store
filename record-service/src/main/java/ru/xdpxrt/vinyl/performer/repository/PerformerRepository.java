package ru.xdpxrt.vinyl.performer.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.xdpxrt.vinyl.performer.model.Performer;

import java.util.List;

public interface PerformerRepository extends JpaRepository<Performer, Long>, JpaSpecificationExecutor<Performer> {
    List<Performer> findAllByCountryIdOrderByName(Long id, Limit limit);
}