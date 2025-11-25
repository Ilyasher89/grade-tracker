package com.example.grade_tracker.service;

import com.example.grade_tracker.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    List<User> findAllUsers();
    boolean usernameExists(String username);
}