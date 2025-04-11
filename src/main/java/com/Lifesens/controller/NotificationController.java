package com.Lifesens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Lifesens.entity.Notification;
import com.Lifesens.service.NotificationService;

@Controller
@RequestMapping("/admin/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // お知らせ登録画面の表示
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("notification", new Notification());
        return "notification_add";
    }

    // お知らせ登録処理
    @PostMapping("/add")
    public String addNotification(@ModelAttribute Notification notification) {
        notificationService.save(notification);
        return "redirect:/admin/notifications/list";
    }

    // お知らせ一覧の表示
    @GetMapping("/list")
    public String listNotifications(Model model) {
        model.addAttribute("notifications", notificationService.findAll());
        return "notification_list";
    }

    // お知らせの削除
    @GetMapping("/delete/{id}")
    public String deleteNotification(@PathVariable("id") Integer id) {
        notificationService.deleteById(id);
        return "redirect:/admin/notifications/list";
    }
}
