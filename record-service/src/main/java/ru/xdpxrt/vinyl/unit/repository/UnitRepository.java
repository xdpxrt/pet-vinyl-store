package ru.xdpxrt.vinyl.unit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdpxrt.vinyl.unit.model.Unit;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findAllByIdIn(List<Long> ids);

    List<Unit> findAllByPriceGreaterThanEqual(Double price);

    List<Unit> findAllByPriceLessThanEqual(Double price);

    List<Unit> findAllByQuantityGreaterThan(Integer quantity);
}