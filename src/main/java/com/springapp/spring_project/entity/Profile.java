package com.springapp.spring_project.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "github")
    @Size(max = 45)
    private  String github;

    @Column(name = "twitter", unique = true)
    @NotNull
    @Email
    @Size(max = 45)
    private String email;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "profileId"
    )
    private User user;

    public Profile() {
    }

    public Profile(String github, String email) {
        this.github = github;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String twitter) {
        this.email = twitter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", github='" + github + '\'' +
                ", twitter='" + email + '\'' +
                '}';
    }
}
