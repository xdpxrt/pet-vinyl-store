package ru.xdpxrt.vinyl.country.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 64)
    private String name;
}