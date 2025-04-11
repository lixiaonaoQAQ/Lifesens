package com.Lifesens.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

/**
 * 商品情報エンティティ
 * 商品の基本情報（名前、価格、在庫、カテゴリなど）を保持するクラス
 */
@Entity
public class ProductInfo {

	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private Integer productId; // 商品ID（主キー）

	@Column(name = "product_name", length = 100, nullable = false)
	private String productName; // 商品名

	@Column(nullable = false)
	private BigDecimal price; // 価格

	@Column(name = "stock_quantity", nullable = false)
	private Integer stockQuantity; // 在庫数量

	@Column(columnDefinition = "TEXT")
	private String description; // 商品説明

	@Column(name = "status", length = 20, nullable = false)
	private String status; // 状態（例：「販売中」「停止中」など）

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt; // 登録日時

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt; // 更新日時

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category; // 紐づくカテゴリ情報

	@Transient
	private String mainImageUrl; // メイン画像のURL（表示用、DBには保存しない）

	// --- Getter & Setter ---
	public String getMainImageUrl() {
	    return mainImageUrl;
	}

	public void setMainImageUrl(String mainImageUrl) {
	    this.mainImageUrl = mainImageUrl;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
