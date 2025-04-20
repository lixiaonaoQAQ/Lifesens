package com.Lifesens.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.cart.CartItem;
import com.Lifesens.entity.Category;
import com.Lifesens.entity.Notification;
import com.Lifesens.entity.ProductImage;
import com.Lifesens.entity.ProductInfo;
import com.Lifesens.service.CategoryService;
import com.Lifesens.service.NotificationService;
import com.Lifesens.service.ProductImageService;
import com.Lifesens.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private NotificationService notificationService;

	// ホーム画面：商品カテゴリとカート情報を表示
	@GetMapping("/")
	public String showHome(Model model, HttpSession session) {
		List<CartItem> cart = getCartFromSession(session);
		model.addAttribute("cartItems", cart);

		// 商品カテゴリ情報（商品画像から取得）
		List<Category> categories = categoryService.findAll();
		List<Map<String, String>> categoryList = new ArrayList<>();

		for (Category category : categories) {
			List<ProductInfo> categoryProducts = productService.findByCategoryId(category.getCategoryId());

			for (ProductInfo product : categoryProducts) {
				List<ProductImage> images = productImageService.getImagesByProductId(product.getProductId());
				if (!images.isEmpty()) {
					product.setMainImageUrl(images.get(0).getImageUrl());
				}
			}

			String imageUrl = "/images/noimage.png";
			for (ProductInfo product : categoryProducts) {
				if (product.getMainImageUrl() != null) {
					imageUrl = product.getMainImageUrl();
					break;
				}
			}

			// ✅ 构建返回用的 Map
			Map<String, String> catMap = new HashMap<>();
			catMap.put("categoryId", String.valueOf(category.getCategoryId()));
			catMap.put("categoryName", category.getCategoryName());
			catMap.put("imageFileName", imageUrl);
			categoryList.add(catMap);
		}

		model.addAttribute("categoryList", categoryList);

		// カート内の合計金額を計算
		BigDecimal total = BigDecimal.ZERO;
		for (CartItem item : cart) {
			total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		}
		model.addAttribute("totalPrice", total);
		// 追加：通知情報を取得（公開日が現在以前のもの）
		List<Notification> publicNotices = notificationService.findAll().stream()
				.filter(n -> n.getPublishDate() != null && n.getPublishDate().isBefore(LocalDateTime.now()))
				.sorted((a, b) -> b.getPublishDate().compareTo(a.getPublishDate())) // 公開日の降順
				.limit(5)
				.toList();
		model.addAttribute("publicNotices", publicNotices);

		return "index";
	}

	// 商品一覧ページ（カートの件数も表示）
	@GetMapping("/product-list")
	public String showPublicProductList(@RequestParam(value = "category", required = false) Integer categoryId,
			Model model,
			HttpSession session) {
		List<CartItem> cart = getCartFromSession(session);
		model.addAttribute("cartItems", cart);

		List<ProductInfo> products;

		if (categoryId != null) {
			// 分類ID指定あり → 該当カテゴリの商品を取得
			products = productService.findByCategoryId(categoryId);

			// ✅ 追加部分：カテゴリ名を取得して画面に渡す
			Category category = categoryService.findCategoryById(categoryId);
			model.addAttribute("currentCategoryName", category != null ? category.getCategoryName() : "");
		} else {
			// 指定なし → 全商品
			products = productService.findAllProducts();
		}

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
