package com.rafaelrocha.backend.dto;

import com.rafaelrocha.backend.entities.Review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ReviewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Campo obrigatorio")
    @NotEmpty
    private String text;
    private UserDTO user;
    private Long movieId;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, UserDTO user, Long movieId) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.movieId = movieId;
    }

    public ReviewDTO(Review review) {
        id = review.getId();
        text = review.getText();
        user = new UserDTO(review.getUser());
        movieId = review.getMovie().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
