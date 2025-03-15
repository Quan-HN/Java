package com.example.AsmGD1.service;

import com.example.AsmGD1.entity.User;
import com.example.AsmGD1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username) // Tìm user mà User nhập vào có trong database không
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Nếu không thì báo lỗi terminal

        return new org.springframework.security.core.userdetails.User( 
                user.getUsername(), 
                user.getPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))  // Trả về role của user
        ); // Trả về UserDetails
    }
}
