package com.example.showcaseapp.entity;


import jakarta.persistence.*;

@Entity(name = "raitings")
@Table(name = "raitings")
@IdClass(MovieRatingId.class)
public class MovieRating {
    @Id
    private Long movieId;
    @Id
    private Long userId;
    private float rating;
    public MovieRating(Long movieId, Long userId, float rating) {
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
    }

    public MovieRating() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}