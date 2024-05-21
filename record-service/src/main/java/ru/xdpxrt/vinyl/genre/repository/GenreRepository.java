package ru.xdpxrt.vinyl.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdpxrt.vinyl.genre.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
