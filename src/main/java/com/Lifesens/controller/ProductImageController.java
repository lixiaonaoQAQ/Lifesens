package com.Lifesens.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Lifesens.entity.ProductImage;
import com.Lifesens.service.ProductImageService;

@Controller
@RequestMapping("/admin/product-images")
public class ProductImageController {

	@Autowired
	private ProductImageService productImageService;

	// 画像管理画面の表示
	@GetMapping
	public String manageProductImages(Model model) {
		List<ProductImage> images = productImageService.getAllImages();
		model.addAttribute("images", images);
		return "product_image_list";
	}

	// 画像アップロード処理
	@PostMapping("/upload")
	public String uploadImages(@RequestParam("productId") Integer productId,
			@RequestParam("image") MultipartFile[] files) {
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				productImageService.saveProductImage(productId, file);
			}
		}
		return "redirect:/admin/product-images";
	}

	// 画像削除処理
	@GetMapping("/delete/{imageId}")
	public String deleteImage(@PathVariable Integer imageId) {
		productImageService.deleteProductImage(imageId);
		return "redirect:/admin/product-images";
	}
}
