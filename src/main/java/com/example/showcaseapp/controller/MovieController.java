package com.example.showcaseapp.controller;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.service.MovieRatingService;
import com.example.showcaseapp.service.MovieService;
import com.example.showcaseapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;
    private final MovieRatingService movieRatingService;

    public MovieController(MovieService movieService, UserService userService, MovieRatingService movieRatingService) {
        this.movieService = movieService;
        this.userService = userService;
        this.movieRatingService = movieRatingService;
    }

    @GetMapping("/movies")
    public String getMovies(Model model,HttpServletRequest request) {
        List<Movie> movies=movieService.getMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("title", new Title());
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "movies";
    }

    @PostMapping("/movies")
    public String getMovies(@ModelAttribute Title title,Model model,HttpServletRequest request) {
        try{
            List<Movie> movies=movieService.findMoviesByTitle(title.getTitle().trim());
            model.addAttribute("movies", movies);
        }catch (MainException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }

        UserDto user=(UserDto) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "movies";
    }
    @GetMapping("/movie/{id}")
    public String getMoviePage(@PathVariable("id") Long movieId,Model model,HttpServletRequest request) {
        try{
            Movie movie=movieService.findById(movieId);
            model.addAttribute("movie", movie);
            model.addAttribute("rating", new Rating());
            UserDto user=(UserDto) request.getSession().getAttribute("user");
            model.addAttribute("user", user);
        }catch (MainException ex){
           return "redirect:/movies";
        }
        return "movie";
    }

    @PostMapping("/movie/{id}")
    public String rateMovie(@PathVariable("id") Long movieId, @ModelAttribute("rating") Rating rating, Model model,HttpServletRequest request) {
        if(rating.getRating()<0 || rating.getRating()>10) {
            model.addAttribute("errorMessage", "Rating must be between 0 and 10");
            return "redirect:/movie/"+movieId;
        }
        try{
            UserDto userRated=(UserDto) request.getSession().getAttribute("user");
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
                    userService.updatePersonalRatings(user,newMovieRating));
        }catch (MainException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/movie/"+movieId;
    }
    @GetMapping("/create_movie")
    public String getCreateMoviePage(Model model,HttpServletRequest request) {
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            model.addAttribute("movie", new Movie());;
            model.addAttribute("user", user);
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

    @GetMapping("/edit_movie/{id}")
    public String getEditMoviePage(@PathVariable("id") Long id,Model model,HttpServletRequest request) {
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            Movie movie;
            try{
                movie=movieService.findById(id);
            }catch (MainException ex){
                return "redirect:/movies";
            }
            model.addAttribute("movie", movie);
            model.addAttribute("user", user);
            return "edit_movie";
        }else{
            return "redirect:/movies";
        }


    }

    @PostMapping("/edit_movie")
    public String editMovie(@ModelAttribute Movie movie){
        movieService.saveMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable("id") Long id,HttpServletRequest request) {
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            movieService.deleteMovie(id);
            return "redirect:/movies";
        }else{
            return "redirect:/movies";
        }
    }

    private static class Rating{
        private float rating;

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) throws MainException {
            this.rating = rating;
        }
    }

    private static class Title{
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
