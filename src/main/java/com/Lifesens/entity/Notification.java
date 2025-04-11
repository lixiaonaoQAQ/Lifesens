package com.Lifesens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * お知らせ情報（管理者による通知）を表すエンティティ
 */
@Entity
@Table(name = "notification_management")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer notificationId; // 通知ID（主キー）

	private String title; // タイトル

	@Column(columnDefinition = "TEXT")
	private String content; // 通知内容（HTMLや長文可能）

	@Column(name = "publish_date")
	private LocalDateTime publishDate; // 公開日

	@Column(name = "created_at")
	private LocalDateTime createdAt; // 作成日時

	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 更新日時

	// ----- Getter & Setter -----
	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
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
