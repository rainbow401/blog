package com.blog.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.dto.PageDTO;
import com.blog.common.entity.Message;
import com.blog.common.resopnse.ResponseResult;
import com.blog.server.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yanzhihao
 * @since 2022-07-06
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/page")
    public ResponseResult<Page<Message>> page(PageDTO dto) {
        return ResponseResult.success(messageService.page(new Page<>(dto.getPageNo(), dto.getPageSize())));
    }

    @GetMapping("/list")
    public ResponseResult<List<Message>> list() {
        return ResponseResult.success(messageService.list());
    }
}
