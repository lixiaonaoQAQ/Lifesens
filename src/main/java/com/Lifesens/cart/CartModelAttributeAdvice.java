package com.Lifesens.cart;

import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 全コントローラーに対してカート情報を自動的にモデルに追加するクラス
 */
@ControllerAdvice
public class CartModelAttributeAdvice {

    /**
     * カート情報（cartItemsとtotalPrice）をModelに追加する
     * すべてのコントローラーの処理前に自動的に呼び出される
     */
    @ModelAttribute
    public void addCartInfoToModel(Model model, HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

        if (cartItems != null) {
            // カート内商品リストをモデルに追加
            model.addAttribute("cartItems", cartItems);

            // カート合計金額を計算してモデルに追加
            BigDecimal totalPrice = cartItems.stream()
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            model.addAttribute("totalPrice", totalPrice);
        }
    }
}
