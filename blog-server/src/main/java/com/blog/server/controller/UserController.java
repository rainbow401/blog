package com.blog.server.controller;

import com.blog.common.resopnse.ResponseResult;
import com.blog.server.dto.UserDTO;
import com.blog.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public ResponseResult<Void> create(UserDTO dto) {
        userService.create(dto);
        return ResponseResult.success();
    }
}
