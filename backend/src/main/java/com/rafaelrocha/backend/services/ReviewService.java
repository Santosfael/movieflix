package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.ReviewDTO;
import com.rafaelrocha.backend.entities.Movie;
import com.rafaelrocha.backend.entities.Review;
import com.rafaelrocha.backend.entities.User;
import com.rafaelrocha.backend.repositories.MovieRepository;
import com.rafaelrocha.backend.repositories.ReviewRepository;
import com.rafaelrocha.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO) {
        Review review = new Review();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());

        Movie movie = movieRepository.getOne(reviewDTO.getMovieId());

        review.setUser(user);
        review.setMovie(movie);
        review.setText(reviewDTO.getText());

        review = reviewRepository.save(review);

        return new ReviewDTO(review);

    }
}
