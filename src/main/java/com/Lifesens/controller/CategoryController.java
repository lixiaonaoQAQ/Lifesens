package com.Lifesens.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.entity.Category;
import com.Lifesens.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // カテゴリー一覧画面の表示
    @GetMapping("/list")
    public String showCategoryList(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category_list";
    }

    // カテゴリー名の更新処理
    @PostMapping("/update")
    public String updateCategory(@RequestParam("categoryId") Integer categoryId,
                                 @RequestParam("categoryName") String categoryName) {
        categoryService.updateCategoryName(categoryId, categoryName);
        return "redirect:/admin/categories/list";
    }
}
