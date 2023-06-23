package com.blog.server.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author yanzhihao
 * @since 2023/4/28
 */
@Configuration
public class InterceptorHandlerAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private ServiceContextInterceptor serviceContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(serviceContextInterceptor)
                .excludePathPatterns("/user/login");
    }
}
