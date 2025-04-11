package com.Lifesens.cart;

import java.math.BigDecimal;

/**
 * カート内の商品を表すクラス
 */
public class CartItem {

	// 商品ID
	private Integer productId;

	// 商品名
	private String productName;

	// 単価
	private BigDecimal price;

	// 数量
	private int quantity;

	// 商品画像のURL
	private String imageUrl;

	/**
	 * コンストラクタ（画像付き）
	 */
	public CartItem(Integer productId, String productName, BigDecimal price, int quantity, String imageUrl) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.imageUrl = imageUrl;
	}

	/**
	 * コンストラクタ（画像なし）
	 */
	public CartItem(Integer productId, String productName, BigDecimal price, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * 小計（単価 × 数量）を取得
	 */
	public BigDecimal getSubtotal() {
		return price.multiply(BigDecimal.valueOf(quantity));
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
