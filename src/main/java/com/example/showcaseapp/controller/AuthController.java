package com.example.showcaseapp.controller;

import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.exception.UserServiceException;
import com.example.showcaseapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest request) throws UserServiceException {
        User candidate = userService.logIn(user.getEmail(), user.getPassword());
        if (candidate == null) {
            return "login";
        }

        request.getSession().setAttribute("user", candidate);
        return "home";
    }

    @GetMapping("register")
    public String getRegisterPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute User user, Model model,HttpServletRequest request) throws UserServiceException {
        try {
            userService.registerUser(user);
        } catch (UserServiceException e) {
            throw new UserServiceException(e);
        }

        request.getSession().setAttribute("user", user);
        return "home";
    }
    @GetMapping("users")
    public String getProfile(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

}
