package com.blog.server.component.context.impl;

import com.blog.server.component.context.ServiceContext;
import com.blog.server.component.context.Token;
import com.blog.server.exceptions.AuthorizationException;
import com.blog.server.util.TokenStore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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
    public void extract(HttpServletRequest request) throws AuthorizationException {
        String header = request.getHeader(Token.TOKEN);
        if (StringUtils.isNotBlank(header)) {
            throw new AuthorizationException();
        }

        String tokenStr = header.substring(7);
        Token token = tokenStore.extract(tokenStr);
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
