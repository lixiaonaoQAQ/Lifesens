package com.Lifesens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * ユーザー情報エンティティ
 * 会員の基本情報（氏名・住所・電話番号など）を保持
 */
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId; // ユーザーID（主キー）

    @Column(name = "full_name", nullable = false)
    private String fullName; // 氏名（フルネーム）

    @Column(name = "address", nullable = false)
    private String address; // 住所

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // 電話番号

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
