package com.nexus.studyplan.controller;

import com.nexus.studyplan.model.UserModel;
import com.nexus.studyplan.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "#")
public class UserController {
    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public List<UserModel> getAllUser(){
        return service.getAllUser();
    }
    @PostMapping("/create")
    public UserModel addUser(@RequestBody UserModel user) {
        return service.addUser(user);
    }

    @PutMapping("/update/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/remove/{id}")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }




}
