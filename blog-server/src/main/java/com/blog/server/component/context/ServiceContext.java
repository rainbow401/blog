package com.blog.server.component.context;

/**
 * @author yanzhihao
 * @date 2023/1/10
 * @Description
 */
public interface ServiceContext extends ServiceContextInternal {

    Token currentToken();

    Long currentUserId();
}
