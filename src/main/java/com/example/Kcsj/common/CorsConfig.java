package com.example.Kcsj.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    private CorsConfiguration buildConfig() {  //创建并配置CorsConfiguration对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址（任何域名都可以访问）
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头（任何请求头都可以通过）
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法（任何HTTP方法（GET、POST等）都可以通过）
        corsConfiguration.setMaxAge(MAX_AGE);  // 预检请求的有效期为MAX_AGE
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {  //创建并返回一个CorsFilter实例
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置，注册CORS配置，对所有URL（/**）应用buildConfig方法中配置的CORS设置
        return new CorsFilter(source);
    }
}
