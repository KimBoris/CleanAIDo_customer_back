package org.zerock.cleanaido_customer_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")  // 모든 URL에 대해 CORS 허용
                .allowedOrigins("http://localhost:5173")  // 허용할 출처(도메인) 설정
                .allowedOrigins("http://localhost:5174")  // 허용할 출처(도메인) 설정
                .allowedOrigins("http://localhost:5175")  // 허용할 출처(도메인) 설정
                .allowedOrigins("http://localhost:5176")  // 허용할 출처(도메인) 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드 설정
                .allowedHeaders("*")  // 허용할 헤더 설정
                .allowCredentials(false);  // 자격 증명 허용
    }
}
