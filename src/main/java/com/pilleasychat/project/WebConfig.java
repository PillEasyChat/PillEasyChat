package com.pilleasychat.project;

import com.pilleasychat.project.domain.user.UserRepository;
import com.pilleasychat.project.web.interceptor.AdditionalCheckInterceptor;
import com.pilleasychat.project.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UserRepository userRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/home", "/signup", "/login/**", "/css/**", "/*.ico", "/error", "/js/**", "/images/**",
                        "https://developers.kakao.com/sdk/js/kakao.min.js",
                        "https://apis.google.com/js/platform.js");

        registry.addInterceptor(new AdditionalCheckInterceptor(userRepository))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/home", "/signup/**", "/login/**", "/logout", "/css/**", "/*.ico", "/error", "/js/**", "/images/**",
                        "https://developers.kakao.com/sdk/js/kakao.min.js",
                        "https://apis.google.com/js/platform.js");
    }
}

