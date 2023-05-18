package com.example.showcaseapp.dto;

import com.example.showcaseapp.entity.Movie;
import com.example.showcaseapp.entity.Role;

import java.util.List;
import java.util.Objects;

public class UserDto {

    private Long userId;
    private String username;

    private String email;

    private Role role;
    private List<Movie> ratedMovies;

    public UserDto(Long id, String email,Role role,String username,List<Movie> ratedMovies) {
        this.userId=id;
        this.email = email;
        this.role = role;
        this.username=username;
        this.ratedMovies=ratedMovies;
    }

    public UserDto() {
        this.email = "";
        this.username="";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean hasRole(String role){
        return this.role.getName() == role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public List<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(List<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (!Objects.equals(userId, userDto.userId)) return false;
        if (!Objects.equals(username, userDto.username)) return false;
        if (!Objects.equals(email, userDto.email)) return false;
        return Objects.equals(role, userDto.role);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
