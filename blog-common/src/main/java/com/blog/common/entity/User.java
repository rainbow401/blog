package com.blog.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseAutoIdEntity {

    /**
     * 用戶名
     */
    private String username;

    /**
     * 密碼
     */
    private String password;

}
