package com.example.showcaseapp.controller;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NavController {
    private MovieService movieService;

    public NavController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }


    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request, Model model){
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        List<Movie> movies=user.getPersonalRatings().stream().map(rating -> {
            try {
                return movieService.findById(rating.getMovieId());
            } catch (MainException e) {
                return null;
            }
        }).filter(movie -> movie!=null).collect(Collectors.toList());

        List<Float> ratings=user.getPersonalRatings().stream().map(rating -> {
            try {
                if(movieService.findById(rating.getMovieId())!=null);{
                    return rating.getRating();
                }
            } catch (MainException e) {
            }
            return null;
        }).collect(Collectors.toList());

        List<MovieRatingsForVisualization> movieRatingsForVisualization=new ArrayList<MovieRatingsForVisualization>();


        model.addAttribute("user", user);
        model.addAttribute("moviesAndRatings", movieRatingsForVisualization);
        return "home";
    }

    private class MovieRatingsForVisualization{
        private Movie movie;
        private float rating;

        public MovieRatingsForVisualization(Movie movie, float ratings) {
            this.movie = movie;
            this.rating = rating;
        }

        public Movie getMovies() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }
    }
}
