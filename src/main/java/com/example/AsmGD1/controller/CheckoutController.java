package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.*;
import com.example.AsmGD1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;
    // Hiển thị trang thanh toán
    @GetMapping("")
    public String checkoutPage(Model model) {
        // Lấy giỏ hàng từ dịch vụ, không cần userId
        List<Cart> cartItems = cartService.getCartByUser(); //Lấy giỏ hàng của user
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        double totalPrice = cartService.calculateTotal(cartItems);  //Tính tổng tiền

        model.addAttribute("cartItems", cartItems); //Truyền giỏ hàng vào model
        model.addAttribute("totalPrice", totalPrice);  //Truyền tổng tiền vào model

        return "checkout";
    }
    // Xử lý thông tin đặt hàng
    @PostMapping("/process")
    public String processCheckout(@RequestParam String name, 
                                  @RequestParam String address,
                                  @RequestParam String phone) {
        List<Cart> cartItems = cartService.getCartByUser(); //Lấy giỏ hàng của user
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = new Order(); //Tạo mới đơn hàng
        order.setTotalPrice(cartService.calculateTotal(cartItems)); //Tính tổng tiền    
        order.setStatus("Pending"); //Trạng thái đơn hàng
        orderService.saveOrder(order); //Lưu đơn hàng vào database

        for (Cart cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail(); //Tạo mới chi tiết đơn hàng
            orderDetail.setOrder(order); //Gán đơn hàng cho chi tiết đơn hàng
            orderDetail.setProduct(cartItem.getProduct());  //Gán sản phẩm cho chi tiết đơn hàng
            orderDetail.setQuantity(cartItem.getQuantity());  //Gán số lượng cho chi tiết đơn hàng
            orderDetail.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());  //Tính giá cho chi tiết đơn hàng
            orderDetailService.saveOrderDetail(orderDetail); //Lưu chi tiết đơn hàng vào database
        }

        cartService.clearCart();

        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String checkoutSuccess() {
        return "checkout_success";
    }
}
