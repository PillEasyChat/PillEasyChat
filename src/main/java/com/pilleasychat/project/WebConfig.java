package com.pilleasychat.project;

import com.pilleasychat.project.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/chat/agent", "/api/chat/pdf2", "/api/chat/pdf", "/api/chat/user", "/api/chat", "/home", "/", "/signup/**", "/login/**", "/logout", "/css/**", "/*.ico", "/error", "/js/**");
    }
}
