package com.example.AsmGD1.controller;
import com.example.AsmGD1.entity.Product;
import com.example.AsmGD1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
//
//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("title", "Trang Chủ - Cửa Hàng Quần Áo");
//        return "index";
//    }
    @Autowired
    private ProductService productService;

    // @GetMapping("/")
    // public String homePage(Model model) {
    //     List<Product> featuredProducts = productService.getFeaturedProducts();
    //     model.addAttribute("featuredProducts", featuredProducts);
    //     return "index";
    // }

    @GetMapping("/")
    public String home(Model model) {   
        List<Product> products = productService.getFeaturedProducts(); // Lấy danh sách sản phẩm nổi bật
        model.addAttribute("featuredProducts", products); // Truyền danh sách sản phẩm vào giao diện
        return "index";
}
}