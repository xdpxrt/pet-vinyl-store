package ru.xdpxrt.vinyl.unit.model;

import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.record.model.Record;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units")
public class Unit {
    @Id
    @Column(name = "record_id")
    private Long id;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;
    @Column(nullable = false,
            name = "added_on")
    private LocalDate addedOn;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false,
    name = "sell_count")
    private Integer sellCount;
}