package com.Lifesens.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Lifesens.cart.CartItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    // カートに商品を追加（Ajax対応）
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addToCart(
            @RequestParam("productId") Integer productId,
            @RequestParam("productName") String productName,
            @RequestParam("price") BigDecimal price,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(productId, productName, price, quantity, imageUrl)); // ✅ 画像URLを含めて追加
        }

        session.setAttribute("cart", cart);

        int cartSize = cart.stream().mapToInt(CartItem::getQuantity).sum();
        Map<String, Object> result = new HashMap<>();
        result.put("status", "OK");
        result.put("cartSize", cartSize);
        return result;
    }

    // カートページの表示
    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
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

        return "cart_view";
    }

    // カートから商品を削除（POST方式）
    @PostMapping("/remove")
    public String removeItem(@RequestParam("productId") Integer productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProductId().equals(productId)) {
                    iterator.remove();
                    break;
                }
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart/view";
    }

    // カートから商品を削除（GET方式）
    @GetMapping("/remove")
    public String removeItemGet(@RequestParam("productId") Integer productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart/view";
    }

    // ✅ Ajaxで使用する：カート情報をJSONで返す
    @GetMapping("/info")
    @ResponseBody
    public Map<String, Object> getCartInfo(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : cart) {
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("items", cart);
        result.put("totalPrice", totalPrice);
        return result;
    }

    // カートのオフキャンバス片（fragments）を返す
    @GetMapping("/fragment")
    public String getCartFragment(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null)
            cart = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalPrice", total);

        // オフキャンバスのフラグメントを返す
        return "fragments/cart_offcanvas :: cartContent";
    }

    // ✅ 商品を削除（Ajax用）
    @PostMapping("/remove-ajax")
    @ResponseBody
    public Map<String, Object> removeItemAjax(@RequestParam("productId") Integer productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        session.setAttribute("cart", cart);

        // ✅ 総件数を再計算
        int totalQuantity = 0;
        if (cart != null) {
            for (CartItem item : cart) {
                totalQuantity += item.getQuantity();
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("status", "OK");
        result.put("cartSize", totalQuantity);
        return result;
    }

    // ✅ 商品数量を更新（Ajax用）
    @PostMapping("/update-quantity")
    @ResponseBody
    public Map<String, Object> updateQuantity(
            @RequestParam("productId") Integer productId,
            @RequestParam("quantity") int quantity,
            HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
            }
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            totalQuantity += item.getQuantity();
        }

        session.setAttribute("cart", cart);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "OK");
        result.put("totalPrice", totalPrice);
        result.put("cartSize", totalQuantity);
        return result;
    }

}
