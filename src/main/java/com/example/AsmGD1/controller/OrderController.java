package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.Order;
import com.example.AsmGD1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders(); // Lấy danh sách orders
        model.addAttribute("orders", orders); // Truyền danh sách orders vào giao diện
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrderDetail(@PathVariable Integer id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);  // Lấy order theo id
        order.ifPresent(value -> model.addAttribute("order", value)); // Truyền order vào model
        return order.isPresent() ? "order_detail" : "redirect:/orders"; // Nếu order tồn tại trả về trang order_detail.html, ngược lại trả về trang orders.html
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order) {
        orderService.saveOrder(order); // Lưu order vào database
        return "redirect:/orders"; 
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id); // Gọi service để xóa order theo Id
        return "redirect:/orders";
    }
}
