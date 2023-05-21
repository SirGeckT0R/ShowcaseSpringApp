package com.example.showcaseapp.service;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.MovieRating;
import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.mapper.UserMapper;
import com.example.showcaseapp.repository.UserRepository;
import com.example.showcaseapp.exception.MainException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository,RoleService roleService) {this.userRepository = userRepository;
        this.roleService = roleService;}

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public UserDto updatePersonalRatings(User user, MovieRating movieRating){
        user.addPersonalRating(movieRating);
        saveUser(user);
        return UserMapper.map(user);
    }

    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        Optional<User> candidate=this.userRepository.findByEmail(email);
        return candidate.orElse(null);
    }
    public User findUserById(Long id) {
        Optional<User> candidate=this.userRepository.findById(id);
        return candidate.orElse(null);
    }

    public UserDto logIn(User user) throws MainException {
        User candidate = findUserByEmail(user.getEmail());
        if (candidate == null) {
            throw new MainException("User with this email never existed!");
        }
        BCryptPasswordEncoder  encoder=new BCryptPasswordEncoder();
        if (encoder.matches(user.getPassword(),candidate.getPassword())) {
            return UserMapper.map(candidate);
        }
        throw new MainException("Entered password is incorrect!");
    }

    public UserDto registerUser(User user) throws MainException {
        User candidate = findUserByEmail(user.getEmail());
        if (candidate != null) {
            throw new MainException("User with this email already exists!");
        }
        roleService.setUserRole(user);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return UserMapper.map(userRepository.save(user));
    }

    public UserDto setAdminRole(Long id)throws MainException{
        User candidate = findUserById(id);
        if (candidate == null) {
            throw new MainException("No user was found!");
        }
        roleService.setAdminRole(candidate);
        User updatedUser=userRepository.save(candidate);
        return UserMapper.map(updatedUser);
    }
}
