package com.rafaelrocha.backend.resources;

import com.rafaelrocha.backend.dto.GenreDTO;
import com.rafaelrocha.backend.entities.Genre;
import com.rafaelrocha.backend.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> findById(@PathVariable Long id) {
        GenreDTO genreDTO = genreService.findById(id);
        return ResponseEntity.ok().body(genreDTO);
    }

    @PostMapping
    public ResponseEntity<GenreDTO> insert(@RequestParam GenreDTO genreDTO) {
        genreDTO = genreService.insert(genreDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(genreDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(genreDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> update(@PathVariable Long id, @RequestBody GenreDTO genreDTO) {
        genreDTO = genreService.update(id, genreDTO);
        return ResponseEntity.ok().body(genreDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
