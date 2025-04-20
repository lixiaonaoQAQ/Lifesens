package com.Lifesens.service;

import java.util.List;

import com.Lifesens.entity.ProductInfo;

/**
 * 商品関連のビジネスロジックを定義するインターフェース
 */
public interface ProductService {

	// 全ての商品を取得する
	List<ProductInfo> findAllProducts();

	// ProductService.java
	List<ProductInfo> findByCategoryId(Integer categoryId);

	// IDで商品を取得する
	ProductInfo findProductById(Integer productId);

	// 新しい商品を追加する（上架）
	ProductInfo addProduct(ProductInfo productInfo);

	// 商品情報を更新する（編集）
	ProductInfo updateProduct(ProductInfo product);

	// 商品を削除する
	void deleteProduct(Integer productId);

}
