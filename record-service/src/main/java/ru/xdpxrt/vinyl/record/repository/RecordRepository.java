package ru.xdpxrt.vinyl.record.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdpxrt.vinyl.record.model.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByGenreIdOrderByName(Long id, Limit limit);

    List<Record> findAllByPerformerIdOrderByPublicationYear(Long id);
}