package com.nexus.studyplan.service;

import com.nexus.studyplan.model.UserModel;
import com.nexus.studyplan.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserRepository repo;

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
}
