package com.example.showcaseapp.repository;

import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitleIgnoreCase(String title);
    Optional<List<User>> findUsersByMovieId(Long movieId);
    Optional<Movie> findById(Long id);

}
