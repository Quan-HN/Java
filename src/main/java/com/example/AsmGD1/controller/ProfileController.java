package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.User;
import com.example.AsmGD1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Hiển thị trang profile
    @GetMapping("/profile")
    public String profile(Model model) {  
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Lấy thông tin user đang đăng nhập
        String username = authentication.getName(); // Lấy username của user đăng nhập

        Optional<User> userOptional = userRepository.findByUsername(username); // Tìm user theo username
        userOptional.ifPresent(user -> model.addAttribute("user", user)); // Truyền user vào model (Lưu và xử lý dữ liệu)

        return "profile"; // Trả về trang profile.html
    }
    // Hiển thị trang chỉnh sửa hồ sơ
    @GetMapping("/profile/edit") 
    public String editProfile(Model model) { // Hiển thị trang chỉnh sửa hồ sơ
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Lấy thông tin user đang đăng nhập
        String username = authentication.getName(); // Lấy username của user đang đăng nhập

        Optional<User> userOptional = userRepository.findByUsername(username); // Tìm user theo username
        if (userOptional.isPresent()) { // Nếu user tồn tại
            model.addAttribute("user", userOptional.get()); // Truyền user vào model
            return "update_profile"; // Trả về trang chỉnh sửa hồ sơ (edit-profile.html)
        }
        return "redirect:/profile";
    }
    // Cập nhật thông tin user
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser, @RequestParam("password") String password) { // Cập nhật thông tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Lấy thông tin user đang đăng nhập
        String username = authentication.getName(); // Lấy username của user đang đăng nhập

        Optional<User> userOptional = userRepository.findByUsername(username); // Tìm user theo username
        if (userOptional.isPresent()) { // Nếu user tồn tại
            User user = userOptional.get();  // Lấy thông tin user
            user.setFullName(updatedUser.getFullName());  // Cập nhật thông tin user
            user.setEmail(updatedUser.getEmail()); // Cập nhật thông tin user
            user.setPhone(updatedUser.getPhone()); // Cập nhật thông tin user
            user.setAddress(updatedUser.getAddress());
            user.setPassword(password); // Không mã hóa theo yêu cầu

            userRepository.save(user); // Lưu thông tin user
        }
        return "redirect:/profile"; // Chuyển hướng về trang profile
    }
}
