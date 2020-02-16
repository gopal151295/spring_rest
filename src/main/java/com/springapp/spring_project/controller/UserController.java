package com.springapp.spring_project.controller;

import com.springapp.spring_project.dao.impl.UserDAOHibernateImpl;
import com.springapp.spring_project.entity.User;
import com.springapp.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    // Dirty: inject UserDao (use constructor injection)
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1")
    public List<User> findAll(){
        return userService.findAll();
    }
}
