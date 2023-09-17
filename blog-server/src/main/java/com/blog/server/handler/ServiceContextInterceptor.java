package com.blog.server.handler;

import com.blog.server.annotation.Permission;
import com.blog.server.component.context.ServiceContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanzhihao
 * @since 2023/4/28
 */
@Configuration
public class ServiceContextInterceptor implements HandlerInterceptor {

    @Resource
    private ServiceContext context;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {

            Permission annotation;

            // 获取方法上的权限注解
            annotation = ((HandlerMethod) handler).getMethod().getAnnotation(Permission.class);

            // 获取类上的权限注解
            if (annotation == null) {
                annotation = ((HandlerMethod) handler).getBeanType().getAnnotation(Permission.class);
            }

            if (annotation != null) {
                context.extract(request);
            }
        }

        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                           @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        context.clear();
    }
}
