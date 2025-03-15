package com.example.AsmGD1.repository;

import com.example.AsmGD1.entity.Cart;
import com.example.AsmGD1.entity.User;
import com.example.AsmGD1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Đánh dấu đây là repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // Kế thừa từ JpaRepository có sẵn trong Spring
    List<Cart> findByUser(User user); // Tìm giỏ hàng theo user
//    void deleteByUser(User user);
    void deleteByUserId(Integer userId); // Xóa giỏ hàng theo user
    Product findProductById(Long productId); // Tìm sản phẩm theo ID
    List<Cart> findAll(); // Lấy tất cả giỏ hàng
    Cart findByProduct(Product product); // Tìm giỏ hàng theo sản phẩm
    Optional<Cart> findByUserAndProduct(User user, Product product); // Tìm giỏ hàng theo user và sản phẩm

}
