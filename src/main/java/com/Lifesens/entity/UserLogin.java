package com.Lifesens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * ユーザーログイン情報エンティティ
 * ユーザー名・パスワードなどの認証用データを保持
 */
@Entity
@Table(name = "user_login")
public class UserLogin {

    @Id
    @Column(name = "user_id")
    private Integer userId; // ユーザーID（主キー・user_info と連携）

    @Column(unique = true, nullable = false)
    private String username; // ユーザー名（ログイン用）

    @Column(nullable = false)
    private String password; // パスワード（プレーンテキスト）

    @Column(name = "is_active")
    private Boolean isActive; // アカウントの有効状態

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 登録日時

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 更新日時

    // --- Getter & Setter ---

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
