package ru.xdpxrt.vinyl.performer.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;
import ru.xdpxrt.vinyl.performer.model.Performer;

import java.util.List;

public interface PerformerRepository extends JpaRepository<Performer, Long>, JpaSpecificationExecutor<Performer> {
    List<Performer> findAllByCountryIdOrderByName(Integer id, Limit limit);

    @Query("SELECT new ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO(p.id, p.name) " +
            "FROM Performer p " +
            "JOIN Record r " +
            "JOIN Unit u " +
            "WHERE p.name LIKE ?1 " +
            "GROUP BY p.id, p.name " +
            "ORDER BY COUNT(u.sellCount) DESC")
    List<ShortPerformerDTO> findAllNameContainingOrderBySellCountDesc(String text, PageRequest pageRequest);

    @Query("SELECT new ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO(p.id, p.name) " +
            "FROM Performer p " +
            "JOIN Record r " +
            "JOIN Unit u " +
            "GROUP BY p.id, p.name " +
            "ORDER BY COUNT(u.sellCount) DESC")
    List<ShortPerformerDTO> findAllOrderBySellCountDesc(PageRequest pageRequest);

    @Query("SELECT new ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO(p.id, p.name) " +
            "FROM Performer p " +
            "WHERE p.name LIKE ?1 " +
            "ORDER BY p.name DESC")
    List<ShortPerformerDTO> findAllNameContainingOrderByName(String text, PageRequest pageRequest);

    @Query("SELECT new ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO(p.id, p.name) " +
            "FROM Performer p " +
            "ORDER BY p.name DESC")
    List<ShortPerformerDTO> findAllOrderByName(PageRequest pageRequest);
}