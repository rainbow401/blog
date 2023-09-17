package com.blog.server.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.blog.server.component.context.ServiceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充
 *
 * @author yan zhihao
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final ServiceContext ctx;

    private final Long DEFAULT_USER_ID = -1L;
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = ctx.currentUserId();
        if (userId == null) {
            userId = DEFAULT_USER_ID;
        }

        this.strictInsertFill(metaObject, "createBy", Long.class, userId); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = ctx.currentUserId();
        if (userId == null) {
            userId = DEFAULT_USER_ID;
        }

        this.strictInsertFill(metaObject, "update_by", Long.class, userId); // 起始版本 3.3.0(推荐使用)
    }
}
