package com.example.grade_tracker.controller;

import com.example.grade_tracker.model.User;
import com.example.grade_tracker.model.Role;
import com.example.grade_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthWebController {

    private final UserService userService;

    @Autowired
    public AuthWebController(UserService userService) {
        this.userService = userService;
    }

    // Страница логина
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Страница регистрации
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Обработка регистрации
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            // Проверяем, не занят ли username
            if (userService.usernameExists(user.getUsername())) {
                model.addAttribute("error", "Username already exists");
                return "auth/register";
            }

            // По умолчанию новый пользователь = ROLE_USER
            user.setRole(Role.ROLE_USER);

            // Сохраняем пользователя (пароль автоматически шифруется в сервисе)
            userService.saveUser(user);

            return "redirect:/auth/login?success";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "auth/register";
        }
    }

    // Страница "Доступ запрещен"
    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "auth/access-denied";
    }
}