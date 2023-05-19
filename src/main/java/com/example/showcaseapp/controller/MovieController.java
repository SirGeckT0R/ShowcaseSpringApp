package com.example.showcaseapp.controller;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.mapper.UserMapper;
import com.example.showcaseapp.service.MovieRatingService;
import com.example.showcaseapp.service.MovieService;
import com.example.showcaseapp.service.UserService;
import com.sun.tools.javac.Main;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {
    private MovieService movieService;
    private UserService userService;
    private MovieRatingService movieRatingService;

    public MovieController(MovieService movieService, UserService userService, MovieRatingService movieRatingService) {
        this.movieService = movieService;
        this.userService = userService;
        this.movieRatingService = movieRatingService;
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        List<Movie> movies=movieService.getMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }
    @GetMapping("/movie/{id}")
    public String getMoviePage(@PathVariable("id") Long movieId,Model model) {
        try{
            Movie movie=movieService.findById(movieId);
            model.addAttribute("movie", movie);
            model.addAttribute("rating", new Rating());
        }catch (MainException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "movie";
    }

    @PostMapping("/movie/{id}")
    public String rateMovie(@PathVariable("id") Long movieId, @ModelAttribute("rating") Rating rating, Model model,HttpServletRequest request) {
        try{
            UserDto userRated=(UserDto) request.getSession().getAttribute("user");
            //MovieRating movieRating=movieRatingService.saveMovieRating(movieId,userRated.getUserId(),rating.getRating());
            MovieRating movieRating = movieRatingService.getMovieRatingByMovieIdAndUserId(movieId, userRated.getUserId());
            MovieRating newMovieRating;
            Movie movie=movieService.findById(movieId);
            if (movieRating==null) {
                newMovieRating= new MovieRating(movieId, userRated.getUserId(),rating.getRating());
                movieService.addMovieRating(movie,rating.getRating());
            }else{
                newMovieRating = new MovieRating(movieRating.getMovieId(), movieRating.getUserId(),movieRating.getRating());
                newMovieRating.setRating(rating.getRating());
                movieService.updateMovieRating(movie,newMovieRating,movieRating.getRating());
            }
            movieRatingService.saveMovieRating(newMovieRating);


            User user=userService.findUserById(userRated.getUserId());
            request.getSession().setAttribute("user",
                    userService.updatePersonalRatings(user,movieRating));
        }catch (MainException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/movie/"+movieId;
    }
    @GetMapping("/create_movie")
    public String getCreateMoviePage(Model model,HttpServletRequest request) {
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            model.addAttribute("movie", new Movie());
            return "create_movie";
        }else{
            return "redirect:/movies";
        }
    }

    @PostMapping("/create_movie")
    public String createMovie(@ModelAttribute Movie movie, Model model){
        movie.setRating(0);
        movie.setNumberOfRatings(0);
        try {
            movieService.addMovie(movie);
            return "redirect:/movies";
        } catch (MainException ex) {
           model.addAttribute("errorMessage", ex.getMessage());
           return "create_movie";
        }
    }

    @GetMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable("id") Long id, Model model,HttpServletRequest request) {
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            movieService.deleteMovie(id);
            return "redirect:/movies";
        }else{
            return "redirect:/movies";
        }
    }

    private class Rating{
        private float rating;

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) throws MainException {
            this.rating = rating;
        }
    }
}
