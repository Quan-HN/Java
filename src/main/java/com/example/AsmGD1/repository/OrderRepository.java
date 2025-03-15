package com.example.AsmGD1.repository;

import com.example.AsmGD1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId); // Tìm order theo userId
}
