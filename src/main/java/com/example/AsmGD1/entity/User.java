package com.example.AsmGD1.entity;

import jakarta.persistence.*;
//import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Users")
//@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto tăng giá trị ID
    private Integer id;

    @Column(unique = true, nullable = false) // Không trùng, không null
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String email;
    private String phone;
    private String address;

    @Column(nullable = false)
    private String role = "USER";

    @Temporal(TemporalType.TIMESTAMP) // Kiểu dữ liệu ngày giờ
    private Date createdAt = new Date(); // Ngày tạo tài khoản

    public User() {
    }

    public User(Integer id, String username, String password, String fullName, String email, String phone, String address, String role, Date createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
