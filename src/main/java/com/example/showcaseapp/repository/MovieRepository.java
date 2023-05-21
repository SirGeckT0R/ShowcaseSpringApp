package com.example.showcaseapp.repository;

import com.example.showcaseapp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitleIgnoreCase(String title);
    Optional<List<Movie>> findAllByTitleIgnoreCaseContaining(String title);
    Optional<Movie> findById(Long id);

}
