package com.example.AsmGD1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 100, message = "Tên sản phẩm phải từ 2 đến 100 ký tự")
    private String name;

    @Size(max = 500, message = "Mô tả sản phẩm không được vượt quá 500 ký tự")
    private String description;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.1", message = "Giá sản phẩm phải lớn hơn 0")
    private Double price;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được nhỏ hơn 0")
    private Integer stockQuantity;


    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)  
    private Category category;  

    @Temporal(TemporalType.TIMESTAMP) // Kiểu dữ liệu ngày giờ
    private Date createdAt = new Date(); // Ngày tạo sản phẩm

    public Product() {
    }

    public Product(Integer id, String name, String description, Double price, Integer stockQuantity, String imageUrl, Category category, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
