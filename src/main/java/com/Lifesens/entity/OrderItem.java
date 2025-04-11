package com.Lifesens.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 注文明細エンティティ（各注文に含まれる商品情報を保持）
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer itemId; // 明細ID（主キー）

	@Column(name = "order_id", nullable = false)
	private Integer orderId; // 対応する注文ID（外部キー）

	@Column(name = "product_id", nullable = false)
	private Integer productId; // 商品ID

	@Column(name = "quantity", nullable = false)
	private int quantity; // 注文数量

	@Column(name = "unit_price", nullable = false)
	private BigDecimal unitPrice; // 単価

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice; // 小計（単価 × 数量）

	@Column(name = "created_at")
	private LocalDateTime createdAt; // 登録日時

	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 更新日時

	@Column(name = "product_name")
	private String productName; // 商品名（表示用）

	@Column(name = "image_url")
	private String imageUrl; // 商品画像URL（表示用）

	// --- Getter & Setter ---

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
