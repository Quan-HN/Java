package com.example.AsmGD1.repository;

import com.example.AsmGD1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); // Tìm user theo username
}
