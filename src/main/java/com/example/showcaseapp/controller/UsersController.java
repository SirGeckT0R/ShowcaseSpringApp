package com.example.showcaseapp.controller;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.exception.MainException;
import com.example.showcaseapp.mapper.UserMapper;
import com.example.showcaseapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute User candidate, HttpServletRequest request,Model model)  {
        try{
            UserDto user = userService.logIn(candidate);
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        }catch (MainException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "login";
        }
    }

    @GetMapping("register")
    public String getRegisterPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute User candidate, HttpServletRequest request,Model model) {
        try {
            UserDto user=userService.registerUser(candidate);
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (MainException e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "register";
        }
    }

    @GetMapping("/home/setAdmin")
    public String setAdmin(HttpServletRequest request){
        try{
            UserDto user=(UserDto) request.getSession().getAttribute("user");
            UserDto userAdmin=userService.setAdminRole(user.getUserId());

            request.getSession().setAttribute("user", userAdmin);
        }catch (MainException e){
        }
        return "redirect:/home";
    }

    @GetMapping("users")
    public String getUsers(HttpServletRequest request,Model model) {
        UserDto user=(UserDto) request.getSession().getAttribute("user");
        if(user.hasRole("Admin")){
            List<UserDto> users=userService.getUsers().stream().map(UserMapper::map).collect(Collectors.toList());
            model.addAttribute("users", users);
            model.addAttribute("user", user);
            return "users";
        }else{
            return "redirect:/home";
        }
    }

    @GetMapping("deleteUser/{id}")
    public String deleteUser(HttpServletRequest request,@PathVariable("id") Long userId) {
        UserDto currentUser=(UserDto) request.getSession().getAttribute("user");
        if(!Objects.equals(currentUser.getUserId(), userId)){
            userService.deleteUser(userId);
        }
        return "redirect:/users";
    }
}
