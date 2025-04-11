package com.Lifesens.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Lifesens.cart.CartItem;
import com.Lifesens.entity.ProductImage;
import com.Lifesens.entity.ProductInfo;
import com.Lifesens.service.ProductImageService;
import com.Lifesens.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    // ホーム画面：商品一覧とカート情報を表示
    @GetMapping("/")
    public String showHome(Model model, HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);
        model.addAttribute("cartItems", cart);

        // 商品リストを取得して表示
        List<ProductInfo> products = productService.findAllProducts();
        for (ProductInfo product : products) {
            List<ProductImage> images = productImageService.getImagesByProductId(product.getProductId());
            if (!images.isEmpty()) {
                product.setMainImageUrl(images.get(0).getImageUrl());
            }
        }
        model.addAttribute("products", products);

        // カート内の合計金額を計算
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        model.addAttribute("totalPrice", total);

        return "index";
    }

    // 商品一覧ページ（カートの件数も表示）
    @GetMapping("/product-list")
    public String showPublicProductList(Model model, HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);
        model.addAttribute("cartItems", cart);

        List<ProductInfo> products = productService.findAllProducts();
        for (ProductInfo product : products) {
            List<ProductImage> images = productImageService.getImagesByProductId(product.getProductId());
            if (!images.isEmpty()) {
                product.setMainImageUrl(images.get(0).getImageUrl());
            }
        }
        model.addAttribute("products", products);
        return "public_product_list";
    }

    // 商品詳細ページ（カートの件数も表示）
    @GetMapping("/product-detail/{id}")
    public String showUserProductDetail(@PathVariable("id") Integer id, Model model, HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);
        model.addAttribute("cartItems", cart);

        ProductInfo product = productService.findProductById(id);
        List<ProductImage> images = productImageService.getImagesByProductId(id);

        model.addAttribute("product", product);
        model.addAttribute("images", images);

        return "product_detail_user";
    }

    // カート情報をセッションから取得（共通処理をまとめたヘルパーメソッド）
    private List<CartItem> getCartFromSession(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }
}
