package com.Lifesens.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.entity.InventoryInfo;
import com.Lifesens.service.InventoryService;
import com.Lifesens.service.ProductService;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	// 在庫一覧の表示
	@GetMapping("")
	public String showInventoryList(Model model) {
		List<InventoryInfo> inventoryList = inventoryService.getAllInventoryRecords();
		model.addAttribute("inventoryList", inventoryList);
		return "inventory_list";
	}

	// 在庫変更フォームの表示
	@GetMapping("/change")
	public String showInventoryChangeForm(Model model) {
		model.addAttribute("products", productService.findAllProducts());
		return "inventory_change";
	}

	// 在庫変更処理（登録）
	@PostMapping("/change")
	public String processInventoryChange(
			@RequestParam Integer productId,
			@RequestParam String operationCategory,
			@RequestParam Integer stockChange,
			@RequestParam BigDecimal purchasePrice) {

		inventoryService.addInventoryRecord(productId, operationCategory, stockChange, purchasePrice);
		return "redirect:/admin/inventory";
	}
}
