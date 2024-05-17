package ru.xdpxrt.vinyl.record.model;

import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.performer.model.Performer;
import ru.xdpxrt.vinyl.genre.model.Genre;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64)
    private String title;
    @Column(name = "publication_year", nullable = false, length = 4)
    private Integer publicationYear;
    @Column(nullable = false, length = 512)
    private String description;
    @Column(name = "track_list", nullable = false, length = 1024)
    private String trackList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_id")
    private Performer performer;
    @ManyToMany
    @JoinTable(name = "records_genres",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @Transient
    private Double price;
    @Transient
    private Integer quantity;
}