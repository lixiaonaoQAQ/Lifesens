package com.Lifesens.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.entity.Staff;
import com.Lifesens.service.StaffService;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private StaffService staffService;

    // ログイン画面表示
    @GetMapping("/login")
    public String showLoginPage() {
        return "admin_login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<Staff> staffOpt = staffService.findByUsername(username);
        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            if (staff.getPassword().equals(password) && Boolean.TRUE.equals(staff.getIsActive())) {
                session.setAttribute("adminUser", staff);
                return "redirect:/admin";  // 管理画面トップへ
            }
        }
        model.addAttribute("error", "ユーザー名またはパスワードが間違っています。");
        return "admin_login";
    }

    // ログアウト処理
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
