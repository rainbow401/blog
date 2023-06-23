package com.blog.common.entity;

import lombok.Data;

@Data
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
