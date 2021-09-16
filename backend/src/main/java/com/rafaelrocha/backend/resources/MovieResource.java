package com.rafaelrocha.backend.resources;

import com.rafaelrocha.backend.dto.MovieDTO;
import com.rafaelrocha.backend.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Direction;

@RestController
@RequestMapping(value = "/movies")
public class MovieResource {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        MovieDTO movieDTO = movieService.findById(id);
        return ResponseEntity.ok().body(movieDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MovieDTO>> findAll(
            @RequestParam(value = "genreId", defaultValue = "0") Long genreId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<MovieDTO> movies = movieService.findAllPaged(genreId, pageRequest);
        return ResponseEntity.ok().body(movies);
    }
}
