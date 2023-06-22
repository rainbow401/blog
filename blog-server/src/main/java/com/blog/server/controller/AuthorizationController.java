package com.blog.server.controller;

import com.blog.common.resopnse.ResponseResult;
import com.blog.server.config.AuthProperties;
import com.blog.server.dto.LoginDTO;
import com.blog.server.util.TokenStore;
import com.blog.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthProperties authProperties;

    private final UserService userService;

    private final TokenStore tokenStore;

    @PostMapping("/login")
    public ResponseResult<String> login(LoginDTO dto) {
        return ResponseResult.success(userService.login(dto));
    }

    @GetMapping("/check")
    public Boolean checkToken(@RequestHeader("Authorization") String token) {
        tokenStore.verifyToken(token);
        return true;
    }

}