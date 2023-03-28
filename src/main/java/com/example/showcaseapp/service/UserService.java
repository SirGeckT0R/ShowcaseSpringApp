package com.example.showcaseapp.service;

import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.repository.UserRepository;
import com.example.showcaseapp.exception.UserServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User logIn(String email, String password) throws UserServiceException {
        User candidate = this.findUserByEmail(email);
        if (candidate == null) {
            throw new UserServiceException("User with this data never exist!");
        }
        if (Objects.equals(candidate.getPassword(), password)) {
            return candidate;
        }
        throw new UserServiceException("Entered data is incorrect!");
    }

    public User registerUser(User user) throws UserServiceException{
        User candidate = this.findUserByEmail(user.getEmail());
        if (candidate != null) {
            throw new UserServiceException("User with this email is already exists!");
        }
        return this.userRepository.save(user);
    }
}
