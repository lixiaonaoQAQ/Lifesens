package com.Lifesens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * 商品画像エンティティ
 * 各商品に紐づく画像情報を管理するためのクラス
 */
@Data
@Entity
@Table(name = "product_image_management")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId; // 画像ID（主キー）

    private Integer productId; // 対応する商品ID（外部キー）

    private String imageUrl; // 画像の保存パスまたはURL

    private LocalDateTime createdAt; // 作成日時

    private LocalDateTime updatedAt; // 更新日時

    // ※ @Data により、getter/setter/toString などは自動生成される
}
