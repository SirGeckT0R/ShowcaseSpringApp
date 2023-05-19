package com.example.showcaseapp.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Table(name="users")
@Entity(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 45)
    private String username;

    @Column(name = "emails", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "passwords", nullable = false, length = 60)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
   @JoinColumn(name = "ratings",referencedColumnName = "userId")
    private Set<MovieRating> personalRatings;

//    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "ratedBy",
//            joinColumns = @JoinColumn(name = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "movieId"))
//    private List<Movie> ratedMovies;

    public User(String email, String password,String username) {
        this.email = email;
        this.password = password;
        this.username=username;
    }

    public User() {
        this.email = "";
        this.password = "";
        this.username="";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<MovieRating> getPersonalRatings() {
        return personalRatings;
    }

    public void setPersonalRatings(Set<MovieRating> movieRatings) {
        this.personalRatings = movieRatings;
    }

    public void addPersonalRating(MovieRating movieRating) {
        if(personalRatings == null) {
            personalRatings = Set.of(movieRating);
        }
        else {
            personalRatings.add(movieRating);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(userId, user.userId)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role.getName() +
                '}';
    }

    public void addRaiting(Long movieId,float raiting) {
        if(personalRatings == null) {
            personalRatings = Set.of(new MovieRating(movieId,userId,raiting));
        }
        else {
            personalRatings.add(new MovieRating(movieId,userId,raiting));

        }
    }

}
