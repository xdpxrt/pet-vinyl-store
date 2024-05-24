package ru.xdpxrt.vinyl.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdpxrt.vinyl.genre.model.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findAllByIdIn(List<Integer> ids);
}