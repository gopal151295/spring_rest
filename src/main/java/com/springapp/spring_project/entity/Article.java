package com.springapp.spring_project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
public class Article {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(max = 45, min = 5)
    @NotNull
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "posted_on")
    private Timestamp postedOn;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            optional = false
    )
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User authorId;

    public Article(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Timestamp getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Timestamp postedOn) {
        this.postedOn = postedOn;
    }

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public Article(int id, @Size(max = 45, min = 5) @NotNull String title, String description, Boolean isActive, Timestamp postedOn, User authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
        this.postedOn = postedOn;
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", postedOn=" + postedOn +
                ", authorId=" + authorId +
                '}';
    }
}
