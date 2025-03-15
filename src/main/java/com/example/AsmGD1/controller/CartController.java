package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.Cart;
import com.example.AsmGD1.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(Model model) {
        List<Cart> cartItems = cartService.getCartByUser(); //Lấy giỏ hàng của user
        double totalPrice = cartService.calculateTotal(cartItems); //Tính tổng tiền
        model.addAttribute("cartItems", cartItems); //Truyền giỏ hàng vào model
        model.addAttribute("totalPrice", totalPrice); //Truyền tổng tiền vào model

        return "cart"; //Trả về trang cart.html
    }

    @PostMapping("/add/{productId}/{quantity}") 
    public String addToCart(@PathVariable Integer productId, @PathVariable int quantity) {
        cartService.addToCart(productId, quantity); //Thêm sản phẩm vào giỏ hàng
        return "redirect:/cart"; //Chuyển hướng về trang giỏ hàng
    }

    @GetMapping("/deleteProductCart/{id}")
    public String deleteProductCart(@PathVariable("id") Integer id) {
        cartService.removeFromCart(id); //Xóa sản phẩm khỏi giỏ hàng
        return "redirect:/cart";
    }


}
