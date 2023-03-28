package com.example.showcaseapp.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name="users")
@Entity(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emails", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "passwords", nullable = false, length = 45)
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
        this.email = "";
        this.password = "";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
