package com.blog.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.entity.User;
import com.blog.server.dto.LoginDTO;
import com.blog.server.util.EncryptUtil;
import com.blog.server.util.TokenStore;
import com.blog.server.dto.UserDTO;
import com.blog.server.mapper.UserMapper;
import com.blog.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final TokenStore tokenStore;

    private final UserMapper userMapper;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String login(LoginDTO dto) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
        query.eq(User::getUsername, dto.getUsername());

        User user = userMapper.selectOne(query);

        Assert.notNull(user, "not find user");

        verifyPassword(dto, user);

        return tokenStore.generateToken(user);
    }

    @Override
    public Long create(UserDTO dto) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery(User.class);
        query.eq(User::getUsername, dto.getUsername());

        Long count = userMapper.selectCount(query);
        Assert.isTrue(count == 0, "用户名已存在");

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(EncryptUtil.encryptPassword(dto.getPassword()));
        user.setCreateBy(0L);
        user.setUpdateBy(0L);

        userMapper.insert(user);

        return user.getId();
    }

    private void verifyPassword(LoginDTO dto, User user) {
        if (!user.getPassword().equals(EncryptUtil.encryptPassword(dto.getPassword()))) {
            throw new IllegalArgumentException("密码错误");
        }
    }
}
