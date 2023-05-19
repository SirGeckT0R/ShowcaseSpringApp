package com.example.showcaseapp.entity;

import jakarta.persistence.*;
import java.util.Set;

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
    private float rating;

    @Column(name="numberOfRatings")
    private int numberOfRatings;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<MovieRating> movieRatings;

//    @ManyToMany(mappedBy = "ratedMovies")
//    private Set<User> users;

    public Movie() {
    }

    public Movie(Long movieId, String title, String description, String releaseDate, float rating, int numberOfRatings) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void increaseNumberOfRatings() {
        this.numberOfRatings++;
    }
    public void decreaseNumberOfRatings() {
        this.numberOfRatings--;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    public void addRating(float rating){
        this.rating=(this.rating*numberOfRatings+rating)/(numberOfRatings+1);
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//    public void addUser(User user){
//        this.users.add(user);
//    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating=" + rating +
                //", imageUrl='" + imageUrl + '\'' +
                //", users=" + users.toString() +
                '}';
    }
}
