package com.rafaelrocha.backend.repositories;

import com.rafaelrocha.backend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
