package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.User;
import com.example.AsmGD1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Trả về trang login.html
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user); // Truyền user mới vào model
        return "register";
    }

    @PostMapping("/register/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user); //Gọi user lưu trong service
        return "login";
    }

}
