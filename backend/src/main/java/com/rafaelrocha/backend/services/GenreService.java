package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.GenreDTO;
import com.rafaelrocha.backend.entities.Genre;
import com.rafaelrocha.backend.repositories.GenreRepository;
import com.rafaelrocha.backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
