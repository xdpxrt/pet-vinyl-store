package ru.xdpxrt.vinyl.performer.model;

import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.country.model.Country;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performers")
public class Performer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    private String name;
    @Column(nullable = false, length = 512)
    private String bio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
}