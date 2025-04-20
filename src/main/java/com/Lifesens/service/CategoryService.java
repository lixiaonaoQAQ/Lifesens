package com.Lifesens.service;

import java.util.List;

import com.Lifesens.entity.Category;

public interface CategoryService {

    /**
     * 全てのカテゴリ情報を取得する
     * @return カテゴリ一覧
     */
    List<Category> getAllCategories();

    /**
     * ホーム画面などで使う用のカテゴリ取得（findAllエイリアス）
     */
    List<Category> findAll();

    /**
     * 指定されたカテゴリIDのカテゴリ名を更新する
     * @param categoryId カテゴリID
     * @param categoryName 新しいカテゴリ名
     */
    void updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 指定されたIDのカテゴリを取得する
     * @param categoryId カテゴリID
     * @return 該当するカテゴリ（存在しない場合はnull）
     */
    Category findCategoryById(Integer categoryId);
}
