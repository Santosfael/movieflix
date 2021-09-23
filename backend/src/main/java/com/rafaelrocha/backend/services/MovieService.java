package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.MovieDTO;
import com.rafaelrocha.backend.dto.ReviewDTO;
import com.rafaelrocha.backend.entities.Genre;
import com.rafaelrocha.backend.entities.Movie;
import com.rafaelrocha.backend.entities.Review;
import com.rafaelrocha.backend.repositories.GenreRepository;
import com.rafaelrocha.backend.repositories.MovieRepository;
import com.rafaelrocha.backend.repositories.ReviewRepository;
import com.rafaelrocha.backend.repositories.RoleRepository;
import com.rafaelrocha.backend.services.exceptions.DataBaseException;
import com.rafaelrocha.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        Optional<Movie> objMovie = movieRepository.findById(id);
        Movie movie = objMovie.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MovieDTO(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAllPaged(Long genreId, PageRequest pageRequest) {
        Genre genre = (genreId == 0) ? null: genreRepository.getOne(genreId);
        Page<Movie> movies = movieRepository.find(genre, pageRequest);

        return movies.map(MovieDTO::new);
    }

    @Transactional
    public MovieDTO insert(MovieDTO movieDTO) {
        Movie movie = new Movie();
        copyDtoToEntity(movieDTO, movie);
        movie = movieRepository.save(movie);
        return new MovieDTO(movie);
    }

    @Transactional
    public MovieDTO update(Long id, MovieDTO movieDTO) {
        try {
            Movie movie = movieRepository.getOne(id);
            copyDtoToEntity(movieDTO, movie);
            movie = movieRepository.save(movie);

            return new MovieDTO(movie);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not fund " + id);
        }
    }

    public void delete(Long id) {
        try {
            movieRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " +id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(MovieDTO movieDTO, Movie movie) {
        movie.setTitle(movieDTO.getTitle());
        movie.setSubTitle(movieDTO.getSubTitle());
        movie.setYear(movieDTO.getYear());
        movie.setImgUrl(movieDTO.getImgUrl());
        movie.setSynopsis(movieDTO.getSynopsis());

        Genre genre = genreRepository.getOne(movieDTO.getGenreId());
        movie.setGenre(genre);

        movie.getReviews().clear();

        for(ReviewDTO reviewDTO : movieDTO.getReviews()) {
            Review review = reviewRepository.getOne(reviewDTO.getId());
            movie.getReviews().add(review);
        }

    }
}
