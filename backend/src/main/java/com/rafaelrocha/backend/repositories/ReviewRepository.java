package com.rafaelrocha.backend.repositories;

import com.rafaelrocha.backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
