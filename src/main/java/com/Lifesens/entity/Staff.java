package com.Lifesens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * スタッフ情報エンティティ
 * 管理者やスタッフのログイン情報やステータスを保持するクラス
 */
@Entity
@Table(name = "staff_info")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer staffId; // スタッフID（主キー）

	private String username; // ユーザー名（ログインID）
	private String password; // パスワード（※プレーンテキストのまま使用中）

	@Column(name = "is_active")
	private Boolean isActive; // 有効フラグ（true=有効、false=無効）

	@Column(name = "created_at")
	private LocalDateTime createdAt; // 登録日時

	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 更新日時

	// --- Getter & Setter ---
	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
