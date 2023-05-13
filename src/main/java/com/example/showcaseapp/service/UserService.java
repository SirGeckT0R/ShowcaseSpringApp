package com.example.showcaseapp.service;

import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.repository.UserRepository;
import com.example.showcaseapp.exception.MainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository,RoleService roleService) {this.userRepository = userRepository;
        this.roleService = roleService;}

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
    public Optional<User> findUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public User logIn(String email, String password) throws MainException {
        User candidate = this.findUserByEmail(email);
        if (candidate == null) {
            throw new MainException("User with this data never exist!");
        }
        if (Objects.equals(candidate.getPassword(), password)) {
            return candidate;
        }
        throw new MainException("Entered data is incorrect!");
    }

    public User registerUser(User user) throws MainException {
        User candidate = this.findUserByEmail(user.getEmail());
        if (candidate != null) {
            throw new MainException("User with this email is already exists!");
        }
        roleService.setUserRole(user);
        userRepository.save(user);
        return candidate;
    }

    public User setAdminRole(Long id)throws MainException{
        Optional<User> candidate = userRepository.findById(id);
        if (candidate.isEmpty()) {
            throw new MainException("User with this data never exist!");
        }

        User user = candidate.get();
        roleService.setAdminRole(user);
        userRepository.save(user);
        return user;

    }
}
