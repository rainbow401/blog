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
 * @author yan zhihao
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final ServiceContext ctx;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", Long.class, ctx.currentUserId()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateBy", Long.class, ctx.currentUserId()); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "update_by", Long.class, ctx.currentUserId()); // 起始版本 3.3.0(推荐使用)
    }
}
