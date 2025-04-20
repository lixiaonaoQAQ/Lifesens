package com.Lifesens.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security のセキュリティ設定クラス
 */
@Configuration
public class SecurityConfig {

   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * セキュリティフィルタチェーンの設定
     * ログインページ・静的リソース・カート機能などのURLを全て許可し、
     * Spring Security のデフォルトログイン・ログアウト機能は無効にする
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF対策を無効化（必要に応じて調整可能）
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/login", "/register", "/logout", 
                    "/css/**", "/js/**", "/images/**", 
                    "/product-list", "/product-detail/**", "/cart/**", 
                    "/checkout/**", "/order/**", "/mypage/**"
                ).permitAll() // 上記のパスは認証不要（全ユーザーアクセス可能）
                .anyRequest().permitAll() // それ以外もすべて許可（今回は認証不要）
            )
            .formLogin().disable() // Spring Security 標準のログインフォームを無効化
            .logout().disable();   // Spring Security 標準のログアウト機能を無効化

        return http.build();
    }
}
