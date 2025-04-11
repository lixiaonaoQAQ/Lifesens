package com.Lifesens.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * クーポン情報を表すエンティティクラス
 */
@Data
@Entity
@Table(name = "coupon_management") // データベースのテーブル名
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId; // クーポンID（主キー）

    @Column(nullable = false, unique = true, length = 50)
    private String couponCode; // クーポンコード（ユニーク、必須）

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountRate; // 割引率（例：0.10 → 10%）

    private LocalDateTime startDate; // クーポン使用開始日時
    private LocalDateTime endDate;   // クーポン使用終了日時

    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
