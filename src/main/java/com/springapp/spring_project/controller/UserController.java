package com.springapp.spring_project.controller;

import com.springapp.spring_project.dao.impl.UserDAOHibernateImpl;
import com.springapp.spring_project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    // Dirty: inject UserDao (use constructor injection)
    private UserDAOHibernateImpl userDAO;

    @Autowired
    public UserController(UserDAOHibernateImpl userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/v1")
    public List<User> findAll(){
        return userDAO.findAll();
    }
}
