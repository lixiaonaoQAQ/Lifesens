package com.Lifesens.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.InventoryInfo;
import com.Lifesens.entity.ProductInfo;
import com.Lifesens.repository.InventoryRepository;

/**
 * 在庫管理に関するサービスクラス
 */
@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductService productService;

	/**
	 * すべての在庫記録を取得する
	 * @return 在庫記録のリスト
	 */
	public List<InventoryInfo> getAllInventoryRecords() {
		return inventoryRepository.findAll();
	}

	/**
	 * 在庫変更記録を追加する（入庫または出庫）
	 * @param productId 商品ID
	 * @param operationCategory 操作区分（例：入庫・出庫）
	 * @param stockChange 在庫変動数（正数 or 負数）
	 * @param purchasePrice 仕入価格（任意）
	 */
	public void addInventoryRecord(Integer productId, String operationCategory, Integer stockChange,
			BigDecimal purchasePrice) {

		// 対象商品を取得
		ProductInfo product = productService.findProductById(productId);
		if (product == null) {
			throw new RuntimeException("商品が見つかりません");
		}

		// 新しい在庫数を計算
		int newStock = product.getStockQuantity() + stockChange;

		// 商品の在庫を更新
		product.setStockQuantity(newStock);
		productService.updateProduct(product);

		// 在庫記録を作成
		InventoryInfo record = new InventoryInfo();
		record.setProduct(product);
		record.setOperationCategory(operationCategory); // 入庫・出庫など
		record.setStockChange(stockChange);             // 増減数
		record.setStockAfter(newStock);                 // 変更後の在庫数
		record.setPurchasePrice(purchasePrice);         // 仕入価格
		record.setSalePrice(product.getPrice());        // 販売価格
		record.setCreatedAt(LocalDateTime.now());
		record.setUpdatedAt(LocalDateTime.now());

		// データベースに保存
		inventoryRepository.save(record);
	}
}
