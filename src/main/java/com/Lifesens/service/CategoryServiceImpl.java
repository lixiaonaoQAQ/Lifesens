package com.Lifesens.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.Category;
import com.Lifesens.repository.CategoryRepository;

/**
 * カテゴリサービスの実装クラス
 * カテゴリ情報の取得・更新などのビジネスロジックを提供
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 全てのカテゴリを取得する
     * @return カテゴリ一覧
     */
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        System.out.println("取得したカテゴリ一覧: " + categories);
        return categories;
    }

    /**
     * 指定されたIDのカテゴリ名を更新する
     * @param categoryId カテゴリID
     * @param categoryName 新しいカテゴリ名
     */
    @Override
    public void updateCategoryName(Integer categoryId, String categoryName) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
    }

    /**
     * 指定されたIDのカテゴリを取得する
     * @param categoryId カテゴリID
     * @return 該当するカテゴリ（存在しない場合は null）
     */
    @Override
    public Category findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
