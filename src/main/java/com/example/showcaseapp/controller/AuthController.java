package com.example.showcaseapp.controller;

import com.example.showcaseapp.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @GetMapping("")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("login")
    public String getLoginPage(Model model){
        model.addAttribute("name","Ivan");
        model.addAttribute("Surname","Ivanov");
        return "login";
    }

    @GetMapping("register")
    public String getRegisterPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute User user, Model model){
        model.addAttribute("user",user);
        return "index";
    }
}
