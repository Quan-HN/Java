package com.example.AsmGD1.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto tăng giá trị ID
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;

    private Double totalPrice;
    private String status = "Pending"; // Pending, Completed, Cancelled

    @Temporal(TemporalType.TIMESTAMP) // Kiểu dữ liệu ngày giờ
    private Date createdAt = new Date(); // Ngày tạo đơn hàng

    public Order() {
    }

    public Order(Integer id, User user, Double totalPrice, String status, Date createdAt) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
