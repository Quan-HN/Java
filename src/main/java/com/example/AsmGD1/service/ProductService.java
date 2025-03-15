package com.example.AsmGD1.service;
import com.example.AsmGD1.entity.Product;
import com.example.AsmGD1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable); // Gọi repository để lấy danh sách sản phẩm theo trang
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id); // Gọi repository để lấy product theo Id
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product); // Lưu product vào database
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id); // Gọi repository để xóa product theo Id
    }

    // public List<Product> getFeaturedProducts() {
    //     return productRepository.findTop3ByOrderByIdAsc();
    // }

    public List<Product> getFeaturedProducts() {
        return productRepository.findTop4ByOrderByIdAsc(); // Lấy 4 sản phẩm mới nhất
    }
    
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword); // Tìm sản phẩm theo tên
    }

    public void updateProduct(Product product) {
        productRepository.save(product); // Lưu SP vào database
    }
}
