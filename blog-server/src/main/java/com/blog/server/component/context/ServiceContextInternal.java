package com.blog.server.component.context;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanzhihao
 * @date 2023/1/10
 * @Description
 */
public interface ServiceContextInternal {

    /**
     * 清除
     */
    void clear();

    /**
     * 存放信息
     */
    void extract(HttpServletRequest request);
}
