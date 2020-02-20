package com.springapp.spring_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapp.spring_project.dao.SrpConfigRepository;
import com.springapp.spring_project.entity.Config;
import com.springapp.spring_project.entity.User;
import com.springapp.spring_project.repo.UrlRepo;
import com.springapp.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    // user service autowired in constructor
    private UserService userService;
    private UrlRepo urlRepo;
    private SrpConfigRepository srpConfigRepository;
    private static ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, UrlRepo urlRepo, SrpConfigRepository srpConfigRepository) {
        this.userService = userService;
        this.urlRepo = urlRepo;
        this.srpConfigRepository = srpConfigRepository;
        objectMapper = new ObjectMapper();
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

    @PostMapping("v1/saveconfig")
    public int saveConfig(@RequestBody Map<String, Object> config) throws IOException {
        try{
            Config configToSave = new Config();

            String value = objectMapper.writeValueAsString(config.get("value"));
            String id = (String) config.get("id");
            String section = (String) config.get("section");

            configToSave.setId(id);
            configToSave.setValue(value);
            configToSave.setSection(section);
            srpConfigRepository.save(configToSave);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @GetMapping("v1/getconfig")
    public Object getConfig(@RequestParam(name = "section", value = "", required=false) String _section) throws JsonProcessingException {
        HashMap<String, Object> res = new HashMap<>();
        _section =  _section == null || _section.isEmpty() ? null : _section;
        List sections = _section == null ? Collections.emptyList() : Arrays.asList(_section.split(","));

        List<Config> configList = srpConfigRepository.findAll();

        for(Config conf : configList){
            String section =  conf.getSection();
            String key = conf.getId();
            Object value = isJSONValid(conf.getValue()) ? objectMapper.readTree(conf.getValue()) : conf.getValue() ;

            if(_section == null && (section == null || section.isEmpty())){
                res.put(conf.getId(), value);
            } else if(res.containsKey(section)){
                HashMap<String, Object> nestedSec = (HashMap<String, Object>) res.get(section);
                nestedSec.put(key, value);
            } else if( _section == null || sections.indexOf(section) > -1 ){
                HashMap<String, Object> nestedSec = new HashMap<>();
                nestedSec.put(key, value);
                res.put(section, nestedSec);
            }
        }
        return res;
    }

    public static boolean isJSONValid(String jsonInString ) {
        try {
            objectMapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

