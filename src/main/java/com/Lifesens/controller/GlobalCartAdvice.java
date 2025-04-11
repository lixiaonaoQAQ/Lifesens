package com.Lifesens.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Lifesens.cart.CartItem;

@ControllerAdvice
public class GlobalCartAdvice {

    // 全ページで使用可能な「カート内商品の総数」を取得してモデルに追加する
    @ModelAttribute("cartTotalQuantity")
    public int getCartTotalQuantity(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return 0;

        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
