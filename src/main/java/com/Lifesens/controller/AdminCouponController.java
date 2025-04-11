package com.Lifesens.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Lifesens.entity.Coupon;
import com.Lifesens.service.CouponService;

/**
 * 管理者向けのクーポン管理コントローラー
 */
@Controller
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    @Autowired
    private CouponService couponService;

    /**
     * クーポン一覧を表示
     * @param model クーポンリストをビューに渡す
     * @return coupon_list テンプレート
     */
    @GetMapping
    public String showCouponList(Model model) {
        List<Coupon> coupons = couponService.findAll();
        model.addAttribute("coupons", coupons);
        return "coupon_list";
    }

    /**
     * クーポン登録画面の表示
     * @param model 新しいクーポンフォーム用の空のオブジェクトを追加
     * @return coupon_add テンプレート
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "coupon_add";
    }

    /**
     * クーポン登録処理
     * @param coupon フォームから送信されたクーポン情報
     * @return クーポン一覧画面へリダイレクト
     */
    @PostMapping("/save")
    public String saveCoupon(@ModelAttribute Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();
        coupon.setCreatedAt(now);
        coupon.setUpdatedAt(now);
        couponService.save(coupon);
        return "redirect:/admin/coupons";
    }
}
