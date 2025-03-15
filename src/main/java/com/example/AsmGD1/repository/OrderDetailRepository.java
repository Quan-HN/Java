package com.example.AsmGD1.repository;

import com.example.AsmGD1.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Integer orderId); // Tìm orderDetail theo orderId
}
