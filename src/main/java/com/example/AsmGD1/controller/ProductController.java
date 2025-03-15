package com.example.AsmGD1.controller;

import com.example.AsmGD1.entity.Product;
import com.example.AsmGD1.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "6") int size) { 
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable để phân trang
        Page<Product> productPage = productService.getAllProducts(pageable); // Lấy danh sách sản phẩm theo trang

        model.addAttribute("products", productPage.getContent()); // Truyền danh sách sản phẩm vào giao diện
        model.addAttribute("currentPage", page); // Truyền số trang hiện tại vào giao diện
        model.addAttribute("totalPages", productPage.getTotalPages()); // Truyền tổng số trang vào giao diện

        return "products";
    }
    
    @GetMapping("/{id}")
    public String getProductById(@PathVariable Integer id, Model model) {
        Optional<Product> product = productService.getProductById(id); // Lấy sản phẩm theo id
        if (product.isPresent()) {
            model.addAttribute("product", product.get()); // Truyền sản phẩm vào giao diện
            return "product_detail"; 
        }
        return "redirect:/products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); // Truyền đối tượng product vào giao diện
        return "add_product"; 
    }

    @PostMapping("/add-product")
    public String saveProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {  // Thêm sản phẩm
        if(bindingResult.hasErrors()) { // Kiểm tra lỗi nhập liệu
            return "add_product";
        }
        productService.saveProduct(product); // Lưu sản phẩm vào database
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) { // Xóa sản phẩm theo id
        productService.deleteProduct(id); // Gọi service để xóa sản phẩm theo Id
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) { // Tìm kiếm sản phẩm
        List<Product> searchResults = productService.searchByName(keyword); // Tìm kiếm sản phẩm theo tên
        model.addAttribute("products", searchResults); // Truyền danh sách sản phẩm tìm kiếm được vào giao diện
        model.addAttribute("keyword", keyword); // Truyền từ khóa tìm kiếm vào giao diện
        return "product-list";
    }
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Optional<Product> product = productService.getProductById(id); // Lấy sản phẩm theo id
        if (product.isPresent()) {
            model.addAttribute("product", product.get()); // Truyền sản phẩm vào giao diện
            return "edit_product";
        }
        return "redirect:/products";
    }
    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "edit_product";
        }
        productService.updateProduct(product); // Cập nhật sản phẩm vào database
        return "redirect:/products";
    }
}
