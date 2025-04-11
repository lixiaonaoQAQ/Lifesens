package com.Lifesens.service;

import java.util.List;

import com.Lifesens.entity.Notification;

public interface NotificationService {
    Notification save(Notification notification);
    void deleteById(Integer id);
    List<Notification> findAll();
    Notification findById(Integer id);
}
