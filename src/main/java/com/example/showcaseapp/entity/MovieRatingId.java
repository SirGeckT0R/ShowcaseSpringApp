package com.example.showcaseapp.entity;

import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class MovieRatingId implements Serializable {
    private Long movieId;
    private Long userId;

    public MovieRatingId(Long movieId, Long userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    public MovieRatingId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieRatingId that = (MovieRatingId) o;

        if (!Objects.equals(movieId, that.movieId)) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = movieId != null ? movieId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
