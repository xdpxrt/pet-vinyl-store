package ru.xdpxrt.vinyl.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.record.model.Record;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDate addedOn;
}