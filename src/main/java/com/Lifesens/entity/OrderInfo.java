package com.Lifesens.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * 注文情報エンティティ（注文の基本情報を保持）
 */
@Entity
@Table(name = "order_info")
public class OrderInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer orderId; // 注文ID（主キー）

	@Column(name = "user_id", nullable = false)
	private Integer userId; // 購入ユーザーID

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice; // 合計金額（割引含む）

	@Column(name = "status", nullable = false)
	private String status; // 注文ステータス（例：未発送、発送済み）

	@Column(name = "cancel_reason")
	private String cancelReason; // キャンセル理由（任意）

	@Column(name = "created_at")
	private LocalDateTime createdAt; // 作成日時

	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 更新日時

	// --- 配送先情報 ---
	@Column(name = "receiver_name")
	private String receiverName; // 受取人氏名

	@Column(name = "receiver_phone")
	private String receiverPhone; // 電話番号

	@Column(name = "receiver_address")
	private String receiverAddress; // 配送先住所

	@Column(name = "shipping_method")
	private String shippingMethod; // 配送方法（例：ゆうパックなど）

	@Column(name = "payment_method")
	private String paymentMethod; // 支払方法（例：カード、代引き）

	@Transient
	private List<OrderItem> items; // 注文明細（DBには保存されない一時情報）

	// --- Getter & Setter ---

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
}
