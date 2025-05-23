package com.Lifesens.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 管理者用画面のルーティングを制御するコントローラー
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	/**
	 * 管理画面トップページの表示
	 * @return lifesens_admin.html を返す
	 */
	@GetMapping("")
	public String adminPage() {
		return "lifesens_admin"; // templates/lifesens_admin.html に対応
	}

	/**
	 * 商品管理セクションへの導線
	 * ProductController にリダイレクトする
	 */
	@GetMapping("/manage/products")
	public String showProductManagementPage() {
		return "redirect:/admin/products/list";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 管理者ログアウト（セッション削除）
		return "redirect:/admin/login"; // ログイン画面へ戻る
	}
}
