package com.example.showcaseapp.service;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.mapper.UserMapper;
import com.example.showcaseapp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        List<Movie> movies=movieRepository.findAll();
        return movies;
    }
    public Movie findById(Long id) throws MainException {
        Optional<Movie> movie=movieRepository.findById(id);
        if(!movie.isPresent()){
            throw new MainException("Movie with this id doesn't exist!");
        }
        return movie.get();
    }

    public Movie findByTitle(String title) throws MainException {
        Optional<Movie> movie=movieRepository.findByTitleIgnoreCase(title);
        if(!movie.isPresent()){
            throw new MainException("Movie with this title doesn't exist!");
        }
        return movie.get();
    }
//
//    public List<UserDto> getAllUsersWhoRated(Long movieId) throws MainException {
//        Optional<List<User>> users=movieRepository.findUsersByMovieId(movieId);
//        if(users.isEmpty()){
//            throw new MainException("There are no users who rated this movie!");
//        }
//        return users.get().stream().map(UserMapper::map).collect(Collectors.toList());
//    }

    public void updateMovieRating(Movie movie, MovieRating movieRating,float oldRating) {
        movie.setRating(movie.getRating()* movie.getNumberOfRatings()-oldRating);
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
