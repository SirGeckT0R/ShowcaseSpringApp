package com.example.showcaseapp.controller;

import com.example.showcaseapp.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;

@Controller
public class NavController {
    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }


    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request, Model model){
        User user=(User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "home";
    }
}
