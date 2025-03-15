package com.example.AsmGD1.service;

import com.example.AsmGD1.entity.Order;
import com.example.AsmGD1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order); // Lưu order vào database
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll(); // Lấy danh sách orders
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id); 
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id); // Gọi repository để xóa order theo Id
    }

    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserId(userId); 
    }
}
