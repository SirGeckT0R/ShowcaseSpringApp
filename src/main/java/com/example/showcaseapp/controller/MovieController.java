package com.example.showcaseapp.controller;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller()
public class MovieController {
    private MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String getMovies(Model model, HttpServletRequest request) {
        List<Movie> movies=movieService.getMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }
    @GetMapping("/movie/{id}")
    public String getMoviePage(@PathVariable("id") Long movieId,Model model, HttpServletRequest request) {
        try{
            Movie movie=movieService.findById(movieId);
            model.addAttribute("movie", movie);
        }catch (MainException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "movie";
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
        try {
            movieService.addMovie(movie);
            return "redirect:/movies";
        } catch (MainException ex) {
           model.addAttribute("errorMessage", ex.getMessage());
           return "create_movie";
        }

    }
}
