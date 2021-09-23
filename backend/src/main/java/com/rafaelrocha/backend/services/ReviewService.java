package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.ReviewDTO;
import com.rafaelrocha.backend.entities.Movie;
import com.rafaelrocha.backend.entities.Review;
import com.rafaelrocha.backend.entities.User;
import com.rafaelrocha.backend.repositories.MovieRepository;
import com.rafaelrocha.backend.repositories.ReviewRepository;
import com.rafaelrocha.backend.repositories.UserRepository;
import com.rafaelrocha.backend.services.exceptions.DataBaseException;
import com.rafaelrocha.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id) {
        Optional<Review> reviewObj = reviewRepository.findById(id);
        Review review = reviewObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ReviewDTO(review);
    }

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

    @Transactional
    public ReviewDTO update(Long id, ReviewDTO reviewDTO) {
        try {
            Review review = reviewRepository.getOne(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(authentication.getName());

            Movie movie = movieRepository.getOne(reviewDTO.getMovieId());

            review.setUser(user);
            review.setText(review.getText());
            review.setMovie(movie);

            return new ReviewDTO(review);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
