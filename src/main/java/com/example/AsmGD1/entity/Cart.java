package com.example.AsmGD1.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto tăng giá trị ID
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id") // Tên cột khoá ngoại
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) 
    private Product product; //TT sản phẩm

    private Integer quantity; // Số lượng sản phẩm

    @Temporal(TemporalType.TIMESTAMP) // Kiểu dữ liệu ngày giờ
    private Date createdAt = new Date(); // Ngày tạo giỏ hàng

    public Cart() {
    }

    public Cart(Integer id, User user, Product product, Integer quantity, Date createdAt) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
