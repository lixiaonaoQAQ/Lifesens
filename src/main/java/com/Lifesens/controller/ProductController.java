package com.Lifesens.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.entity.Category;
import com.Lifesens.entity.ProductImage;
import com.Lifesens.entity.ProductInfo;
import com.Lifesens.service.CategoryService;
import com.Lifesens.service.ProductImageService;
import com.Lifesens.service.ProductService;

/**
 * 商品管理のコントローラー
 */
@Controller
@RequestMapping("/admin/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductImageService productImageService;

	// 商品登録画面の表示
	@GetMapping("/add")
	public String showAddProductForm(Model model) {
		model.addAttribute("product", new ProductInfo());
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "product_add";
	}

	// 商品登録処理
	@PostMapping("/add")
	public String addProduct(@ModelAttribute("product") ProductInfo product,
			@RequestParam("categoryId") Integer categoryId) {
		LocalDateTime now = LocalDateTime.now();
		product.setCreatedAt(now);
		product.setUpdatedAt(now);

		// ✅ カテゴリーが存在するかチェック
		Category category = categoryService.findCategoryById(categoryId);
		if (category != null) {
			product.setCategory(category);
		} else {
			// ✅ 無効な categoryId の場合はエラーまたはデフォルトを設定
			System.out.println("⚠️ 指定された categoryId は存在しません: " + categoryId);
			return "redirect:/admin/products/add?error=invalid_category";
		}

		productService.addProduct(product);
		return "redirect:/admin/products/list";
	}

	// 商品編集画面の表示
	@GetMapping("/edit/{id}")
	public String showEditProductForm(@PathVariable("id") Integer id, Model model) {
		ProductInfo existingProduct = productService.findProductById(id);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("product", existingProduct);
		model.addAttribute("categories", categories);
		return "product_edit";
	}

	// 商品更新処理
	@PostMapping("/edit/{id}")
	public String updateProduct(@PathVariable("id") Integer id, @ModelAttribute("product") ProductInfo product) {
		product.setProductId(id);
		productService.updateProduct(product);
		return "redirect:/admin/products/list";
	}

	// 商品削除処理
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products/list";
	}

	// 商品一覧の表示
	@GetMapping("/list")
	public String showProductList(Model model) {
		List<ProductInfo> products = productService.findAllProducts();
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
		return "product_list";
	}

	// 商品詳細の表示（画像含む）
	@GetMapping("/detail/{id}")
	public String showProductDetail(@PathVariable("id") Integer id, Model model) {
		ProductInfo product = productService.findProductById(id);
		List<ProductImage> images = productImageService.getImagesByProductId(id); // ✅ 商品画像取得

		model.addAttribute("product", product);
		model.addAttribute("images", images);

		return "product_detail";
	}
}
