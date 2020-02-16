package com.springapp.spring_project.controller;

import com.springapp.spring_project.dao.impl.UserDAOHibernateImpl;
import com.springapp.spring_project.entity.User;
import com.springapp.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    // user service autowired in constructor
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/v1/{userId}")
    public User getUser(@PathVariable int userId){
        User user = userService.findById(userId);

        if(user == null){
            throw  new RuntimeException("User not found with id: " + userId);
        }

        return user;
    }

    @PostMapping("/v1")
    public User addUser(@RequestBody User user){
        // if client sends id in body - set it to 0 to force adding new user instead of updating
        user.setId(0);

        userService.save(user);

        return user;
    }

    @PutMapping("/v1")
    public User updateUser(@RequestBody User user){
        userService.save(user);

        return user;
    }

    @DeleteMapping("v1/{userId}")
    public String deleteUser(@PathVariable int userId){
        User user = userService.findById(userId);

        if(user == null){
            throw  new RuntimeException("User not found with id: " + userId);
        }

        userService.deleteById(userId);
        return "User delete with id: " + userId;
    }
}
