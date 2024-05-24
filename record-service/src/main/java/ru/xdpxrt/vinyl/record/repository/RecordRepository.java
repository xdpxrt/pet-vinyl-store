package ru.xdpxrt.vinyl.record.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.xdpxrt.vinyl.record.model.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {
    List<Record> findAllByGenresIdOrderByTitle(Integer id, Limit limit);

    List<Record> findAllByPerformerIdOrderByPublicationYear(Long id);

    List<Record> findAllByGenresIdOrderByUnitSellCountDesc(Integer id, Limit limit);
}