package com.example.showcaseapp.repository;

import com.example.showcaseapp.entity.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRatingsRepository extends JpaRepository<MovieRating,Long> {
    Optional<List<MovieRating>> findByMovieId(Long movieId);
    Optional<List<MovieRating>> findByUserId(Long userId);
    Optional<MovieRating> findByMovieIdAndUserId(Long movieId, Long userId);

}
