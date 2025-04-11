package com.Lifesens.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Lifesens.entity.Staff;
import com.Lifesens.service.StaffService;

@Controller
@RequestMapping("/admin/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // スタッフ一覧画面の表示
    @GetMapping("/list")
    public String listStaff(Model model) {
        model.addAttribute("staffList", staffService.findAll());
        return "staff_list";
    }

    // スタッフ追加画面の表示
    @GetMapping("/add")
    public String showAddStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff_add";
    }

    // スタッフの新規登録処理
    @PostMapping("/add")
    public String addStaff(@ModelAttribute Staff staff) {
        staff.setCreatedAt(LocalDateTime.now());
        staff.setUpdatedAt(LocalDateTime.now());
        staffService.save(staff);
        return "redirect:/admin/staff/list";
    }

    // スタッフ削除処理
    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable("id") Integer id) {
        staffService.deleteById(id);
        return "redirect:/admin/staff/list";
    }
}
