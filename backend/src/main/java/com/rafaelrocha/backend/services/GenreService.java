package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.GenreDTO;
import com.rafaelrocha.backend.entities.Genre;
import com.rafaelrocha.backend.repositories.GenreRepository;
import com.rafaelrocha.backend.repositories.RoleRepository;
import com.rafaelrocha.backend.services.exceptions.DataBaseException;
import com.rafaelrocha.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(GenreDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GenreDTO findById(Long id) {
        Optional<Genre> genreObj = genreRepository.findById(id);
        Genre genre = genreObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        return new GenreDTO(genre);
    }

    @Transactional
    public GenreDTO insert(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setName(genreDTO.getName());
        genre = genreRepository.save(genre);

        return new GenreDTO(genre);
    }

    @Transactional
    public GenreDTO update(Long id, GenreDTO genreDTO) {
        try {
            Genre genre = genreRepository.getOne(id);
            genre.setName(genreDTO.getName());
            genre = genreRepository.save(genre);

            return new GenreDTO(genre);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    public void delete(Long id) {
        try {
            genreRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found "+id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
