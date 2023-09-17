package com.blog.server.component.context.impl;

import com.blog.server.component.context.ServiceContext;
import com.blog.server.component.context.Token;
import com.blog.server.exceptions.AuthorizationException;
import com.blog.server.util.TokenStore;
import org.apache.commons.lang3.StringUtils;
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

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<Token> TOKEN = new ThreadLocal<>();

    @Resource
    private TokenStore tokenStore;

    @Override
    public void extract(HttpServletRequest request) throws AuthorizationException {
        String authorizationHeader = request.getHeader(Token.TOKEN);
        if (StringUtils.isBlank(authorizationHeader)) {
            throw new AuthorizationException();
        }

        String tokenString = getTokenString(authorizationHeader);
        Token token = tokenStore.extract(tokenString);

        TOKEN.set(token);
        USER_ID.set(token.getUserId());
    }

    private String getTokenString(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    @Override
    public Long getUserIdWithExtract(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(Token.TOKEN);
        if (StringUtils.isBlank(authorizationHeader)) {
            return null;
        }

        String tokenString = getTokenString(authorizationHeader);
        Token token = tokenStore.extract(tokenString);

        USER_ID.set(token.getUserId());
        TOKEN.set(token);

        return token.getUserId();
    }

    @Override
    public Token currentToken() {
        return TOKEN.get();
    }

    @Override
    public Long currentUserId() {
        return USER_ID.get();
    }

    @Override
    public void clear() {
        TOKEN.remove();
        USER_ID.remove();
    }
}
