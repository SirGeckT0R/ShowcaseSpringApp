package com.example.showcaseapp.service;

import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }
    public Movie findById(Long id) throws MainException {
        Optional<Movie> movie=movieRepository.findById(id);
        if(movie.isEmpty()){
            throw new MainException("Movie with this id doesn't exist!");
        }
        return movie.get();
    }
    public List<Movie> findMoviesByTitle(String title) throws MainException {
        Optional<List<Movie>> movies=movieRepository.findAllByTitleIgnoreCaseContaining(title);
        if(movies.isEmpty()){
            throw new MainException("There are no movies with this title!");
        }
        return movies.get();
    }

    public Movie findByTitle(String title) throws MainException {
        Optional<Movie> movie=movieRepository.findByTitleIgnoreCase(title);
        if(movie.isEmpty()){
            throw new MainException("Movie with this title doesn't exist!");
        }
        return movie.get();
    }
    public void updateMovieRating(Movie movie, MovieRating movieRating,float oldRating) {
        float diff=(movie.getRating()* movie.getNumberOfRatings()-oldRating);
        float div=movie.getNumberOfRatings()>1?(movie.getNumberOfRatings()-1):1;
        float rollbackRating=diff/div;
        movie.setRating(rollbackRating);
        movie.decreaseNumberOfRatings();
        movie.addRating(movieRating.getRating());
        movie.increaseNumberOfRatings();
        saveMovie(movie);
    }
    public void addMovieRating(Movie movie,float rating) {
        movie.addRating(rating);
        movie.increaseNumberOfRatings();
        saveMovie(movie);
    }

    public void addMovie(Movie movie) throws MainException {
        Optional<Movie> candidate = movieRepository.findByTitleIgnoreCase(movie.getTitle());
        if (candidate.isPresent()) {
            throw new MainException("Movie with this title already exists!");
        }
        saveMovie(movie);
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }


    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
