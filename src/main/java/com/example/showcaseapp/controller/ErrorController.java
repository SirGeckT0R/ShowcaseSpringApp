package com.example.showcaseapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletResponse response, Exception ex){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}
