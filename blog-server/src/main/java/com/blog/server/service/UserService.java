package com.blog.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.entity.User;
import com.blog.server.dto.LoginDTO;
import com.blog.server.dto.UserDTO;


public interface UserService extends IService<User> {

    String login(LoginDTO dto);

    Long create(UserDTO dto);
}
