package com.blog.server.component.context.impl;

import com.blog.server.component.context.ServiceContext;
import com.blog.server.component.context.Token;
import com.blog.server.util.TokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author yanzhihao
 * @Date 2023/1/10
 * @Description
 */
@Component
public class ServiceContextImpl implements ServiceContext {

    private static final ThreadLocal<Long> USERID = new ThreadLocal<>();
    private static final ThreadLocal<Token> TOKEN = new ThreadLocal<>();

    @Resource
    private TokenStore tokenStore;

    @Override
    public void extract(HttpServletRequest request) {
        String tokenStr = request.getHeader(Token.TOKEN);
        Token token = tokenStr != null ? tokenStore.extract(tokenStr) : null;
        if (token != null) {
            TOKEN.set(token);
            USERID.set(token.getUserid());
        }
    }

    @Override
    public Token currentToken() {
        return TOKEN.get();
    }

    @Override
    public Long currentUserId() {
        return USERID.get();
    }

    @Override
    public void clear() {
        TOKEN.remove();
        USERID.remove();
    }
}
