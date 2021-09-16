package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.MovieDTO;
import com.rafaelrocha.backend.entities.Genre;
import com.rafaelrocha.backend.entities.Movie;
import com.rafaelrocha.backend.repositories.GenreRepository;
import com.rafaelrocha.backend.repositories.MovieRepository;
import com.rafaelrocha.backend.repositories.RoleRepository;
import com.rafaelrocha.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RoleRepository roleRepository;

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
}
