package com.Lifesens.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 在庫記録（入出庫履歴）を表すエンティティ
 */
@Entity
@Table(name = "inventory_info")
public class InventoryInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer inventoryId; // 在庫記録ID（主キー）

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private ProductInfo product; // 関連する商品情報

	@Column(name = "operation_category", nullable = false)
	private String operationCategory; // 操作区分（"入庫" または "出庫"）

	@Column(name = "stock_change", nullable = false)
	private Integer stockChange; // 変動した数量（プラスまたはマイナス）

	@Column(name = "stock_after", nullable = false)
	private Integer stockAfter; // 操作後の在庫数

	@Column(name = "purchase_price")
	private BigDecimal purchasePrice; // 仕入価格（任意）

	@Column(name = "sale_price")
	private BigDecimal salePrice; // 販売価格（任意）

	@Column(name = "order_id")
	private Integer orderId; // 関連注文ID（出庫時など）

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt; // 作成日時

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt; // 更新日時

	// ----- Getter & Setter -----
	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public ProductInfo getProduct() {
		return product;
	}

	public void setProduct(ProductInfo product) {
		this.product = product;
	}

	public String getOperationCategory() {
		return operationCategory;
	}

	public void setOperationCategory(String operationCategory) {
		this.operationCategory = operationCategory;
	}

	public Integer getStockChange() {
		return stockChange;
	}

	public void setStockChange(Integer stockChange) {
		this.stockChange = stockChange;
	}

	public Integer getStockAfter() {
		return stockAfter;
	}

	public void setStockAfter(Integer stockAfter) {
		this.stockAfter = stockAfter;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
