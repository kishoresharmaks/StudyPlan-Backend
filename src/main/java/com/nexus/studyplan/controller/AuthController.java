package com.nexus.studyplan.controller;

import com.nexus.studyplan.model.UserModel;
import com.nexus.studyplan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userservice;
    @PostMapping("/signup")
    public String signup(@RequestBody UserModel user){
        if(user.getMailid() == null || user.getPassword() == null){
            return "Please Enter proper credentials";
        }
        if(userservice.existByEmail(user.getMailid())){
            return "Email Already Registered";
        }
        userservice.register(user);
        return "Signup success";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserModel user, HttpSession session){
       UserModel dbuser = userservice.authenticate(user.getMailid(), user.getPassword());
       if(dbuser == null){
           return "invalid data";
       }
       session.setAttribute("userid", dbuser.getId());
       session.setAttribute("username", dbuser.getUsername());
        return "Login Success";
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        String username = (String) session.getAttribute("username");
        
        if (userId == null || username == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userId);
        userInfo.put("username", username);
        
        return ResponseEntity.ok(userInfo);
    }
}
