package com.blog.server.controller;

import com.blog.common.resopnse.ResponseResult;
import com.blog.server.dto.LoginDTO;
import com.blog.server.dto.UserDTO;
import com.blog.server.service.UserService;
import com.blog.server.util.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenStore tokenStore;


    /**
     * 创建用户
     * @param dto
     * @return 用户id
     */
    @PostMapping
    public ResponseResult<Long> create(@RequestBody UserDTO dto) {
        return ResponseResult.success(userService.create(dto));
    }

    /**
     * 登陆
     * @param dto
     * @return token
     */
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody LoginDTO dto) {
        return ResponseResult.success(userService.login(dto));
    }

    /**
     * 检查token
     * @param token
     * @return boolean
     */
    @GetMapping("/check")
    public Boolean checkToken(@RequestHeader("Authorization") String token) {
        tokenStore.verifyToken(token);
        return true;
    }
}
