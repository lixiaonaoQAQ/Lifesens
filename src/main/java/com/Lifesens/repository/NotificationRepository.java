package com.Lifesens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lifesens.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
