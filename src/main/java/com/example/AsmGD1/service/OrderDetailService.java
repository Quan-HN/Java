package com.example.AsmGD1.service;

import com.example.AsmGD1.entity.OrderDetail;
import com.example.AsmGD1.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail); // Lưu orderDetail vào database
    }

    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId); // Lấy danh sách orderDetail theo orderId
    }
}
