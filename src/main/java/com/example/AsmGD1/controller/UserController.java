package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.User;
import com.example.AsmGD1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService; // Inject userService vào controller
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Lấy danh sách users
        model.addAttribute("users", users); // Truyền danh sách users vào giao diện
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserDetail(@PathVariable Integer id, Model model) {
        Optional<User> user = userService.getUserById(id);  // Lấy user theo id
        user.ifPresent(value -> model.addAttribute("user", value)); // Truyền user vào model
        return user.isPresent() ? "user_detail" : "redirect:/users"; // Nếu user tồn tại trả về trang user_detail.html, ngược lại trả về trang users.html
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user); // Lưu user vào database
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id); // Gọi service để xóa user theo Id
        return "redirect:/users";
    }
}
