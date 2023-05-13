package com.example.showcaseapp.controller;

import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute User user, HttpServletRequest request) throws MainException {
        User candidate = userService.logIn(user.getEmail(), user.getPassword());
        if (candidate == null) {
            return "login";
        }

        request.getSession().setAttribute("user", candidate);
        return "home";
    }

    @GetMapping("/home/setAdmin")
    public String setAdmin(HttpServletRequest request){
        try{
            User user=(User) request.getSession().getAttribute("user");
            User userAdmin=userService.setAdminRole(user.getUserId());

            request.getSession().setAttribute("user", userAdmin);
            return "redirect:/users";
        }catch (MainException e){
            return "redirect:/home";
        }
    }
    @GetMapping("register")
    public String getRegisterPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute User user, HttpServletRequest request) throws MainException {
        try {
            userService.registerUser(user);
        } catch (MainException e) {
            throw new MainException(e);
        }

        request.getSession().setAttribute("user", user);
        return "home";
    }

    @GetMapping("users")
    public String getProfile(HttpServletRequest request,Model model) {
        User user=(User) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            model.addAttribute("users", userService.getUsers());
            return "users";
        }else{
            return "redirect:/home";
        }
    }
}
