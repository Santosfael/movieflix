package com.rafaelrocha.backend.repositories;

import com.rafaelrocha.backend.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
