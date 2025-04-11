package com.Lifesens.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静的リソース（画像など）のマッピング設定クラス
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * /uploads/ というURLパスに対して、
     * プロジェクトのルートにある「uploads」フォルダ内のファイルを対応させる
     * 例）http://localhost:8080/uploads/sample.jpg → uploads/sample.jpg を返す
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
