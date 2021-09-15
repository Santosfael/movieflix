package com.rafaelrocha.backend.resources;

import com.rafaelrocha.backend.dto.GenreDTO;
import com.rafaelrocha.backend.entities.Genre;
import com.rafaelrocha.backend.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/genres")
public class GenreResource {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> findAll() {
        List<GenreDTO> genreDTOS = genreService.findAll();

        return ResponseEntity.ok().body(genreDTOS);
    }
}
