package com.Lifesens.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.Notification;
import com.Lifesens.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Integer id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Integer id) {
        return notificationRepository.findById(id).orElse(null);
    }
}
