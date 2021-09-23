package com.rafaelrocha.backend.resources;

import com.rafaelrocha.backend.dto.ReviewDTO;
import com.rafaelrocha.backend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewResource {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> findAll() {
        List<ReviewDTO> reviewDTOs = reviewService.findAll();

        return ResponseEntity.ok().body(reviewDTOs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable Long id) {
        ReviewDTO reviewDTO = reviewService.findById(id);

        return ResponseEntity.ok().body(reviewDTO);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> insert(@Valid @RequestBody ReviewDTO reviewDTO) {
        reviewDTO = reviewService.insert(reviewDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(reviewDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(reviewDTO);
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> update(@PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO) {
        reviewDTO = reviewService.update(id, reviewDTO);

        return ResponseEntity.ok().body(reviewDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> dalete(@PathVariable Long id) {
        reviewService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
