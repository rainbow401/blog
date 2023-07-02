package com.blog.server.controller;


import com.blog.common.entity.Tag;
import com.blog.common.resopnse.ResponseResult;
import com.blog.server.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 标签
 * @author yanzhihao
 * @since 2022-07-06
 */
@Validated
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/list")
    public ResponseResult<List<Tag>> list() {
        return ResponseResult.success(tagService.list());
    }

    @PostMapping("/{name}")
    public ResponseResult<Long> add(@PathVariable("name") @NotBlank String name) {
        return ResponseResult.success(tagService.add(name));
    }
}
