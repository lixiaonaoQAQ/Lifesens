package com.Lifesens.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.cart.CartItem;
import com.Lifesens.entity.Coupon;
import com.Lifesens.entity.OrderInfo;
import com.Lifesens.entity.OrderItem;
import com.Lifesens.entity.UserInfo;
import com.Lifesens.entity.UserLogin;
import com.Lifesens.repository.CouponRepository;
import com.Lifesens.repository.OrderInfoRepository;
import com.Lifesens.repository.OrderItemRepository;
import com.Lifesens.repository.UserInfoRepository;
import com.Lifesens.service.UserLoginService;

@Controller
public class OrderController {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CouponRepository couponRepository;

    // 購入画面の表示
    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalPrice", total);

        // ✅ ログイン済みの場合はユーザー情報を取得して入力欄に渡す
        String username = (String) session.getAttribute("username");
        if (username != null) {
            Optional<UserLogin> userLoginOpt = userLoginService.findByUsername(username);
            if (userLoginOpt.isPresent()) {
                UserLogin userLogin = userLoginOpt.get();
                Optional<UserInfo> userInfoOpt = userInfoRepository.findById(userLogin.getUserId());
                userInfoOpt.ifPresent(userInfo -> model.addAttribute("userInfo", userInfo));
            }
        }

        return "checkout";
    }

    // 注文確認画面の処理
    @PostMapping("/checkout/confirm")
    public String confirmOrder(
            @RequestParam String lastName,
            @RequestParam String firstName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String zipcode,
            @RequestParam String prefecture,
            @RequestParam String city,
            @RequestParam String address,
            @RequestParam String shippingMethod,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String couponCode,
            HttpSession session,
            Model model) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("error", "カートに商品がありません。");
            return "redirect:/cart/view";
        }

        String username = (String) session.getAttribute("username");
        Optional<UserLogin> userOpt = userLoginService.findByUsername(username);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした。");
            return "redirect:/login";
        }

        UserLogin user = userOpt.get();
        String fullAddress = prefecture + city + " " + address + "（" + zipcode + "）";

        // 商品合計
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // ✅ クーポン割引の処理
        BigDecimal discount = BigDecimal.ZERO;
        if (couponCode != null && !couponCode.trim().isEmpty()) {
            Optional<Coupon> couponOpt = couponRepository.findByCouponCode(couponCode.trim());
            if (couponOpt.isPresent()) {
                Coupon coupon = couponOpt.get();
                LocalDateTime now = LocalDateTime.now();
                if (coupon.getStartDate().isBefore(now) && coupon.getEndDate().isAfter(now)) {
                    discount = total.multiply(coupon.getDiscountRate());
                    model.addAttribute("discountAmount", discount);
                    model.addAttribute("appliedCouponCode", couponCode);
                } else {
                    model.addAttribute("couponError", "このクーポンは現在ご利用いただけません。");
                    model.addAttribute("appliedCouponCode", couponCode);
                }
            } else {
                model.addAttribute("couponError", "無効なクーポンコードです。");
                model.addAttribute("appliedCouponCode", couponCode);
            }
        }

        // ✅ 合計金額（割引後）
        BigDecimal finalTotal = total.subtract(discount);

        // 注文情報の一時保存（確認画面用）
        OrderInfo order = new OrderInfo();
        order.setUserId(user.getUserId());
        order.setReceiverName(lastName + " " + firstName);
        order.setReceiverPhone(phoneNumber);
        order.setReceiverAddress(fullAddress);
        order.setShippingMethod(shippingMethod);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("未発送");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setTotalPrice(finalTotal);

        // ✅ セッションに保存
        session.setAttribute("cartItems", cart);
        session.setAttribute("paymentMethod", paymentMethod);
        session.setAttribute("shippingMethod", shippingMethod);
        session.setAttribute("receiverName", lastName + " " + firstName);
        session.setAttribute("receiverPhone", phoneNumber);
        session.setAttribute("receiverAddress", fullAddress);
        session.setAttribute("discountAmount", discount);
        session.setAttribute("couponCode", couponCode);

        model.addAttribute("order", order);
        model.addAttribute("items", cart);

        return "order_confirm";
    }

    // 注文確定処理
    @PostMapping("/order/success")
    public String placeOrder(@RequestParam("cardNumber") String cardNumber,
                             HttpSession session,
                             Model model) {

        // テスト用カード番号のチェック
        String normalized = cardNumber.replaceAll("\\s+", "");
        if (!"4242424242424242".equals(normalized)) {
            model.addAttribute("error", "無効なカード番号です。テスト用カード番号（4242 4242 4242 4242）をご利用ください。");

            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            if (cartItems != null) {
                model.addAttribute("items", cartItems);
            }

            OrderInfo order = new OrderInfo();
            order.setReceiverName((String) session.getAttribute("receiverName"));
            order.setReceiverPhone((String) session.getAttribute("receiverPhone"));
            order.setReceiverAddress((String) session.getAttribute("receiverAddress"));
            order.setShippingMethod((String) session.getAttribute("shippingMethod"));
            order.setPaymentMethod((String) session.getAttribute("paymentMethod"));

            BigDecimal total = BigDecimal.ZERO;
            if (cartItems != null) {
                for (CartItem item : cartItems) {
                    total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }
            }

            BigDecimal discount = (BigDecimal) session.getAttribute("discountAmount");
            if (discount == null) discount = BigDecimal.ZERO;

            order.setTotalPrice(total.subtract(discount));
            model.addAttribute("order", order);
            return "order_confirm";
        }

        // ログイン情報の取得
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        Optional<UserLogin> userOpt = userLoginService.findByUsername(username);
        if (userOpt.isEmpty()) return "redirect:/login";

        UserLogin userLogin = userOpt.get();
        Integer userId = userLogin.getUserId();

        // カート情報の取得
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("error", "カートが空です。");
            return "redirect:/cart/view";
        }

        BigDecimal discount = (BigDecimal) session.getAttribute("discountAmount");
        if (discount == null) discount = BigDecimal.ZERO;

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 注文主情報の作成と保存
        OrderInfo order = new OrderInfo();
        order.setUserId(userId);
        order.setStatus("未発送");
        order.setPaymentMethod((String) session.getAttribute("paymentMethod"));
        order.setShippingMethod((String) session.getAttribute("shippingMethod"));
        order.setReceiverName((String) session.getAttribute("receiverName"));
        order.setReceiverPhone((String) session.getAttribute("receiverPhone"));
        order.setReceiverAddress((String) session.getAttribute("receiverAddress"));
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setTotalPrice(total.subtract(discount));

        OrderInfo savedOrder = orderInfoRepository.save(order); // ✅ 注文主の保存

        // 注文明細の保存
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getOrderId());
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getProductName());
            orderItem.setUnitPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItem.setUpdatedAt(LocalDateTime.now());
            orderItemRepository.save(orderItem);
        }

        // セッション情報のクリア
        session.removeAttribute("cartItems");
        session.removeAttribute("cart");
        session.removeAttribute("discountAmount");
        session.removeAttribute("couponCode");

        model.addAttribute("order", savedOrder);
        return "order_success";
    }

    // 注文履歴の表示（マイページ）
    @GetMapping("/mypage/order-history")
    public String showOrderHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        Optional<UserLogin> userOpt = userLoginService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        Integer userId = userOpt.get().getUserId();
        List<OrderInfo> orders = orderInfoRepository.findByUserIdOrderByCreatedAtDesc(userId);

        // 注文ごとに明細情報を取得して設定
        for (OrderInfo order : orders) {
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getOrderId());
            order.setItems(items); // ✅ 一時的に設定するためのフィールド
        }

        model.addAttribute("orders", orders);
        return "order_history";
    }
}
