package com.springapp.spring_project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Articles {

    @GetMapping("/hello")
    public String hello (){
        return "hello";
    }
}
