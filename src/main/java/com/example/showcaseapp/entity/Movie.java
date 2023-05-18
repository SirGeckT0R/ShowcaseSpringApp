package com.example.showcaseapp.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name="movies")
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private Long movieId;

    @Column(name="title", nullable = false,unique = true)
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="release_year")
    private String releaseDate;
    @Column(name="rating")
    private String rating;
    @Column(name="image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "ratedMovies")
    private List<User> users;

    public Movie() {
    }

    public Movie(String title, String description, String releaseDate, String rating, String imageUrl) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
