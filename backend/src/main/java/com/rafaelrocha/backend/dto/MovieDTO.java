package com.rafaelrocha.backend.dto;

import com.rafaelrocha.backend.entities.Movie;
import com.rafaelrocha.backend.entities.Review;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MovieDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String subTitle;
    private Integer year;
    private String imgUrl;
    private String synopsis;
    private Long genreId;
    private List<ReviewDTO> reviews = new ArrayList<>();

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String subTitle, Integer year, String imgUrl, String synopsis, Long genreId) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genreId = genreId;
    }

    public MovieDTO(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        subTitle = movie.getSubTitle();
        year = movie.getYear();
        imgUrl = movie.getImgUrl();
        synopsis = movie.getSynopsis();
        genreId = movie.getGenre().getId();
        movie.getReviews().forEach(review -> this.reviews.add(new ReviewDTO(review)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
