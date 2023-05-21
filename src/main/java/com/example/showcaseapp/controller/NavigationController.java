package com.example.showcaseapp.controller;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.javatuples.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class NavigationController {
    private final MovieService movieService;

    public NavigationController(MovieService movieService) {
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
        HashMap<Long, Pair<String,Float>> moviesRatings= new HashMap<>();
        Set<MovieRating> ratings=user.getPersonalRatings();
        if (ratings != null && !ratings.isEmpty()) {
            ratings.stream().map(rating -> {
                try {
                    if(rating!=null){
                        moviesRatings.put(rating.getMovieId(),new Pair<>(movieService.findById(rating.getMovieId()).getTitle(),rating.getRating()));
                    }
                } catch (MainException e) {
                }
                return null;
            }).collect(Collectors.toList());
        }


        model.addAttribute("user", user);
        model.addAttribute("moviesAndRatings", moviesRatings);
        return "home";
    }

}
