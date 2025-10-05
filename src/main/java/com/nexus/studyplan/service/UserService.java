package com.nexus.studyplan.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nexus.studyplan.model.UserModel;
import com.nexus.studyplan.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<UserModel> getAllUser() {
        return repo.findAll();
    }

    public UserModel addUser(UserModel user) {
        // Extra business rule validation
        if (repo.existsByMailid(user.getMailid())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return repo.save(user);
    }

    public UserModel updateUser(Long id, UserModel user) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
        user.setId(id); // Ensure correct ID
        return repo.save(user);
    }

    public String deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
        repo.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }

    public boolean existByEmail(String mailid) {
        if(!repo.existsByMailid(mailid)){
            return false;
        }
        return true;
    }

    public void register(UserModel user) {
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    public UserModel authenticate(String mailId, String password) {
        return repo.findByMailid(mailId)
                .filter(u-> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }
}
