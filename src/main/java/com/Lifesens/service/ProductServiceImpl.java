package com.Lifesens.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.ProductImage;
import com.Lifesens.entity.ProductInfo;
import com.Lifesens.repository.ProductImageRepository;
import com.Lifesens.repository.ProductInfoRepository;

/**
 * 商品関連ビジネスロジックの実装クラス
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductInfoRepository productInfoRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	/**
	 * 商品一覧取得（カテゴリ名と主画像も取得）
	 */
	@Override
	public List<ProductInfo> findAllProducts() {
		// カテゴリ付きの商品リストを取得
		List<ProductInfo> products = productInfoRepository.findAllWithCategory();
		for (ProductInfo product : products) {
			// 商品画像を取得（最初の画像を主画像に設定）
			List<ProductImage> images = productImageRepository.findByProductId(product.getProductId());
			if (!images.isEmpty()) {
				product.setMainImageUrl(images.get(0).getImageUrl());
			}
		}
		return products;
	}

	/**
	 * 商品を削除
	 * @param productId 商品ID
	 */
	@Override
	public void deleteProduct(Integer productId) {
		productInfoRepository.deleteById(productId);
	}

	/**
	 * 商品IDで詳細取得
	 * @param productId 商品ID
	 * @return 該当商品の情報
	 */
	@Override
	public ProductInfo findProductById(Integer productId) {
		return productInfoRepository.findById(productId).orElse(null);
	}

	/**
	 * 商品を追加（新規登録）
	 * @param productInfo 商品情報
	 * @return 登録後の商品情報
	 */
	@Override
	public ProductInfo addProduct(ProductInfo productInfo) {
		return productInfoRepository.save(productInfo);
	}

	/**
	 * 商品情報を更新（編集）
	 * @param product 更新対象の商品
	 * @return 更新後の商品情報
	 */
	@Override
	public ProductInfo updateProduct(ProductInfo product) {
		// 既存の作成日時を保持したまま更新
		ProductInfo existing = productInfoRepository.findById(product.getProductId()).orElse(null);
		if (existing == null) {
			throw new IllegalArgumentException("商品が存在しません: ID = " + product.getProductId());
		}

		product.setCreatedAt(existing.getCreatedAt()); // 作成日時は変更しない
		product.setUpdatedAt(LocalDateTime.now());     // 更新日時を現在時刻に設定

		return productInfoRepository.save(product);
	}
}
