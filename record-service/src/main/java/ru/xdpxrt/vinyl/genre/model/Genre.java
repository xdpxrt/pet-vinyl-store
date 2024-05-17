package ru.xdpxrt.vinyl.genre.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 64)
    private String name;
}