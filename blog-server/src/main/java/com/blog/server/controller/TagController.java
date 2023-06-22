package com.blog.server.controller;


import com.blog.common.entity.Tag;
import com.blog.common.resopnse.ResponseResult;
import com.blog.server.service.TagService;
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
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/list")
    public ResponseResult<List<Tag>> list() {
        return ResponseResult.success(tagService.list());
    }
}
