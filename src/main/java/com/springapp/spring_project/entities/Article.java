package com.springapp.spring_project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Article {
    @Id
    @Column(name = "id")
    private int id;
}
