package com.example.showcaseapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {
    @GetMapping("/home")
    public String getIndexPage(){
        return "home";
    }
}
