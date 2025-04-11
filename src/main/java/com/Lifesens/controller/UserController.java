package com.Lifesens.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lifesens.cart.CartItem;
import com.Lifesens.entity.UserInfo;
import com.Lifesens.entity.UserLogin;
import com.Lifesens.repository.UserInfoRepository;
import com.Lifesens.service.UserLoginService;

@Controller
public class UserController {

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    // ログインページの表示
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 新規登録フォームの表示
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "register";
    }

    // 新規登録処理
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String fullName,
                           @RequestParam String address,
                           @RequestParam String phoneNumber,
                           Model model) {
        Optional<UserLogin> existingUser = userLoginService.findByUsername(username);
        if (existingUser.isPresent()) {
            model.addAttribute("registerError", "このユーザー名はすでに使われています。");
            return "register";
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(fullName);
        userInfo.setAddress(address);
        userInfo.setPhoneNumber(phoneNumber);

        UserLogin newUser = new UserLogin();
        newUser.setUsername(username);
        newUser.setPassword(password);

        userLoginService.register(newUser, userInfo);

        return "redirect:/login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        Optional<UserLogin> optionalUser = userLoginService.findByUsername(username);
        if (optionalUser.isPresent() && userLoginService.checkPassword(optionalUser.get(), password)) {
            session.setAttribute("username", username);
            return "redirect:/";
        }

        model.addAttribute("loginError", "ユーザー名またはパスワードが間違っています");
        return "login";
    }

    // マイページトップ
    @GetMapping("/mypage")
    public String mypage(Model model, HttpSession session) {
        model.addAttribute("cartItems", getCartFromSession(session));
        return "mypage";
    }

    // 会員情報ページ
    @GetMapping("/mypage/info")
    public String mypageInfo(Model model, HttpSession session) {
        model.addAttribute("cartItems", getCartFromSession(session));

        String username = (String) session.getAttribute("username");

        if (username == null) {
            model.addAttribute("error", "ユーザーがログインしていません。");
            return "login";
        }

        Optional<UserLogin> userLoginOpt = userLoginService.findByUsername(username);
        if (userLoginOpt.isPresent()) {
            UserLogin userLogin = userLoginOpt.get();
            Optional<UserInfo> userInfoOpt = userInfoRepository.findById(userLogin.getUserId());

            if (userInfoOpt.isPresent()) {
                model.addAttribute("userInfo", userInfoOpt.get());
                model.addAttribute("userLogin", userLogin);
            } else {
                model.addAttribute("error", "ユーザー情報が見つかりませんでした。");
                model.addAttribute("userInfo", new UserInfo());
                model.addAttribute("userLogin", userLogin);
            }
        } else {
            model.addAttribute("error", "ユーザーが見つかりませんでした。");
            model.addAttribute("userInfo", new UserInfo());
            model.addAttribute("userLogin", new UserLogin());
        }

        return "mypage-info";
    }

    // セキュリティ（パスワード変更）ページの表示
    @GetMapping("/mypage/security")
    public String showSecurityPage(Model model, HttpSession session) {
        model.addAttribute("cartItems", getCartFromSession(session));
        return "mypage_security";
    }

    // パスワード変更処理
    @PostMapping("/mypage/security")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model,
                                 HttpSession session) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            model.addAttribute("error", "ユーザーがログインしていません。");
            return "login";
        }

        Optional<UserLogin> optionalUser = userLoginService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "ユーザーが見つかりませんでした。");
            model.addAttribute("cartItems", getCartFromSession(session));
            return "mypage_security";
        }

        UserLogin userLogin = optionalUser.get();

        if (!userLoginService.checkPassword(userLogin, currentPassword)) {
            model.addAttribute("error", "現在のパスワードが間違っています。");
            model.addAttribute("cartItems", getCartFromSession(session));
            return "mypage_security";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "新しいパスワードと確認パスワードが一致しません。");
            model.addAttribute("cartItems", getCartFromSession(session));
            return "mypage_security";
        }

        userLogin.setPassword(newPassword);
        userLoginService.updatePassword(userLogin);

        model.addAttribute("message", "パスワードが正常に変更されました。");
        model.addAttribute("cartItems", getCartFromSession(session));
        return "mypage_security";
    }

    // ログアウト処理
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // カート情報をセッションから取得する共通メソッド
    private List<CartItem> getCartFromSession(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }
}
