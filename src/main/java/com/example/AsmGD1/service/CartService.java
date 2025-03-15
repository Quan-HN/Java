//package com.example.AsmGD1.service;
//
//import com.example.AsmGD1.entity.Cart;
//import com.example.AsmGD1.entity.Product;
//import com.example.AsmGD1.entity.Order;
//import com.example.AsmGD1.entity.OrderDetail;
//import com.example.AsmGD1.repository.CartRepository;
//import com.example.AsmGD1.repository.OrderDetailRepository;
//import com.example.AsmGD1.repository.OrderRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CartService {
//
//    private final CartRepository cartRepository;
//    private final OrderDetailRepository orderDetailRepository;
//    private final OrderRepository orderRepository;
//
//    public CartService(CartRepository cartRepository, OrderDetailRepository orderDetailRepository, OrderRepository orderRepository) {
//        this.cartRepository = cartRepository;
//        this.orderDetailRepository = orderDetailRepository;
//        this.orderRepository = orderRepository;
//    }
//
//    private final List<Cart> cartItems = new ArrayList<>(); // Lưu giỏ hàng tạm thời
//
//    public List<Cart> addToCart(Product product) {
//        boolean exists = false;
//        for (Cart item : cartItems) {
//            if (item.getProduct().getId().equals(product.getId())) {
//                item.setQuantity(item.getQuantity() + 1);
//                exists = true;
//                break;
//            }
//        }
//        if (!exists) {
//            Cart newItem = new Cart();
//            newItem.setProduct(product);
//            newItem.setQuantity(1);
//            cartItems.add(newItem);
//        }
//        return cartItems;
//    }
//
//    public void updateCart(Integer cartId, Integer quantity) {
//        for (Cart item : cartItems) {
//            if (item.getId().equals(cartId)) {
//                item.setQuantity(quantity);
//                return;
//            }
//        }
//        throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
//    }
//
//
//    public void deleteCartItem(Integer id) {
//        this.cartRepository.deleteById(id);
//    }
//
//
//
//
//    public List<Cart> getCart() {
//        return new ArrayList<>(cartItems);
//    }
//
//    public double calculateTotal(List<Cart> cartItems) {
//        return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
//    }
//
//    public void clearCart() {
//        cartItems.clear();
//    }
//
//    public void checkout() {
//        if (cartItems.isEmpty()) {
//            throw new RuntimeException("Giỏ hàng trống");
//        }
//
//        Order order = new Order();
//        order.setTotalPrice(calculateTotal(cartItems));
//        order.setStatus("Pending");
//        orderRepository.save(order);
//
//        for (Cart cartItem : cartItems) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//            orderDetail.setProduct(cartItem.getProduct());
//            orderDetail.setQuantity(cartItem.getQuantity());
//            orderDetail.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
//            orderDetailRepository.save(orderDetail);
//        }
//
//        clearCart();
//    }
//}

package com.example.AsmGD1.service;

import com.example.AsmGD1.entity.*;
import com.example.AsmGD1.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.cartRepository = cartRepository; // Khởi tạo đối tượng cartRepository(giỏ hàng Kho lưu trữ)
        this.productRepository = productRepository; 
        this.userRepository = userRepository;
        this.orderRepository = orderRepository; // Khởi tạo đối tượng orderRepository(đơn hàng Kho lưu trữ)
        this.orderDetailRepository = orderDetailRepository; // Khởi tạo đối tượng orderDetailRepository(chi tiết đơn hàng Kho lưu trữ)
    }

    public List<Cart> getCartByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Lấy thông tin user đang đăng nhập
        String username = auth.getName(); // Lấy username của user đang đăng nhập
        Optional<User> userOpt = userRepository.findByUsername(username); // Tìm user theo username
        return userOpt.map(cartRepository::findByUser).orElse(List.of()); // Trả về giỏ hàng của user
    }

    public void addToCart(Integer productId, int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Lấy thông tin user đang đăng nhập
        String username = auth.getName(); // Lấy username của user đang đăng nhập
        Optional<User> userOpt = userRepository.findByUsername(username); // Tìm user theo username
        // Nếu user tồn tại
        if (userOpt.isPresent()) {
            User user = userOpt.get(); // Lấy thông tin user
            Optional<Product> productOpt = productRepository.findById(productId); // Tìm sản phẩm theo ID
            // Nếu sản phẩm tồn tại
            if (productOpt.isPresent()) {
                Product product = productOpt.get(); // Lấy thông tin sản phẩm
                Optional<Cart> existingCartItem = cartRepository.findByUserAndProduct(user, product); // Tìm giỏ hàng theo user và sản phẩm
                // Nếu giỏ hàng đã tồn tại
                if (existingCartItem.isPresent()) {
                    Cart cartItem = existingCartItem.get(); // Lấy thông tin giỏ hàng
                    cartItem.setQuantity(cartItem.getQuantity() + quantity); // Cập nhật số lượng
                    cartRepository.save(cartItem); // Lưu giỏ hàng
                    // Nếu giỏ hàng chưa tồn tại
                } else {
                    Cart cartItem = new Cart(); // Tạo mới giỏ hàng
                    cartItem.setUser(user); // Gán user cho giỏ hàng
                    cartItem.setProduct(product); // Gán sản phẩm cho giỏ hàng
                    cartItem.setQuantity(quantity); // Gán số lượng cho giỏ hàng
                    cartRepository.save(cartItem); // Lưu giỏ hàng
                }
            }
        }
    }
    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(Integer cartItemId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartItemId); // Tìm giỏ hàng theo ID
        if (cartOpt.isPresent()) {
            Cart cartItem = cartOpt.get();  // Lấy thông tin giỏ hàng
            if (cartItem.getQuantity() > 1) {  // Nếu số lượng lớn hơn 1
                cartItem.setQuantity(cartItem.getQuantity() - 1);  // Giảm số lượng
                cartRepository.save(cartItem);  // Lưu giỏ hàng
            } else {
                cartRepository.delete(cartItem);  // Xóa giỏ hàng
            }
        }
    }




    public double calculateTotal(List<Cart> cartItems) {
        return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum(); // Tính tổng tiền
    }
    private final List<Cart> cartItems = new ArrayList<>(); // Lưu giỏ hàng tạm thời

    public void clearCart() {
        cartRepository.deleteAll(); // Xóa tất cả giỏ hàng
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống"); // Nếu giỏ hàng trống thì thông báo lỗi
        }

        Order order = new Order(); // Tạo mới đơn hàng
        order.setTotalPrice(calculateTotal(cartItems)); // Tính tổng tiền
        order.setStatus("Pending"); // Trạng thái đơn hàng
        orderRepository.save(order);    // Lưu đơn hàng vào database

        for (Cart cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail(); // Tạo mới chi tiết đơn hàng
            orderDetail.setOrder(order); // Gán đơn hàng cho chi tiết đơn hàng
            orderDetail.setProduct(cartItem.getProduct()); // Gán sản phẩm cho chi tiết đơn hàng
            orderDetail.setQuantity(cartItem.getQuantity()); // Gán số lượng cho chi tiết đơn hàng
            orderDetail.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity()); // Tính giá cho chi tiết đơn hàng
            orderDetailRepository.save(orderDetail); // Lưu chi tiết đơn hàng vào database
        }

        clearCart(); 
    }
}
