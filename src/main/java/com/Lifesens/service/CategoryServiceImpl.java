package com.Lifesens.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.Category;
import com.Lifesens.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        System.out.println("取得したカテゴリ一覧: " + categories);
        return categories;
    }

    @Override
    public List<Category> findAll() {
        // getAllCategories() をそのまま再利用
        return getAllCategories();
    }

    @Override
    public void updateCategoryName(Integer categoryId, String categoryName) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
    }

    @Override
    public Category findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
