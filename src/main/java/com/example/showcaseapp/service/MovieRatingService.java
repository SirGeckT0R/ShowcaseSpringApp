package com.example.showcaseapp.service;

import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.repository.MovieRatingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieRatingService {
    private final MovieRatingsRepository movieRaitingsRepository;

    public MovieRatingService(MovieRatingsRepository movieRaitingsRepository) {
        this.movieRaitingsRepository = movieRaitingsRepository;
    }
    public MovieRating getMovieRatingByMovieIdAndUserId(Long movieId,Long userId) throws MainException {
        Optional<MovieRating> movieRating = movieRaitingsRepository.findByMovieIdAndUserId(movieId, userId);
        return movieRating.orElse(null);
    }

    public List<MovieRating> getMovieRatingsByMovieId(Long movieId) throws MainException {
        Optional<List<MovieRating>> movieRatings = movieRaitingsRepository.findByMovieId(movieId);
        if (movieRatings.isEmpty()) {
            throw new MainException("Ratings were not found");
        }
        return movieRatings.get();
    }

    public List<MovieRating> getMovieRatingsByUserId(Long userId) throws MainException {
        Optional<List<MovieRating>> movieRatings = movieRaitingsRepository.findByUserId(userId);
        if (movieRatings.isEmpty()) {
            throw new MainException("Ratings were not found");
        }
        return movieRatings.get();
    }

//    public MovieRating saveMovieRating(Long movieId,Long userId, float rating) throws MainException {
//        Optional<MovieRating> movieRating = movieRaitingsRepository.findByMovieIdAndUserId(movieId, userId);
//        MovieRating newMovieRating;
//        if (!movieRating.isPresent()) {
//            newMovieRating= new MovieRating(movieId,userId,rating);
//        }else{
//            newMovieRating = movieRating.get();
//            newMovieRating.setRating(rating);
//        }
//        return movieRaitingsRepository.save(newMovieRating);
//    }

    public void saveMovieRating(MovieRating movieRating){
    	movieRaitingsRepository.save(movieRating);
    }
}
