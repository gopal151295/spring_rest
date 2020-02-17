package com.springapp.spring_project.controller;

import com.springapp.spring_project.entity.User;
import com.springapp.spring_project.repo.UrlRepo;
import com.springapp.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    // user service autowired in constructor
    private UserService userService;
    private UrlRepo urlRepo;

    @Autowired
    public UserController(UserService userService, UrlRepo urlRepo) {
        this.userService = userService;
        this.urlRepo = urlRepo;
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

    @GetMapping("v1/save")
    public String saveUrl(@RequestParam("url") String urlToSave) {
        urlRepo.saveUrl("url:" + MD5(urlToSave), urlToSave);
        System.out.println("digest: "+ MD5(urlToSave));
        return urlToSave;
    }

    public String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

