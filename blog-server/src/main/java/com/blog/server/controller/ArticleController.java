package com.blog.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.dto.PageDTO;
import com.blog.common.entity.Article;
import com.blog.common.resopnse.ResponseResult;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.dto.ArticleUpdateDTO;
import com.blog.server.service.ArticleService;
import com.blog.server.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/article")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 分页查询文章
     *
     * @param dto 分页参数
     * @return ResponseResult<Page < Article>>
     */
    @GetMapping("/page")
    public ResponseResult<Page<Article>> page(PageDTO dto) {
        return ResponseResult.success(articleService.page(new Page<>(dto.getPageNo(), dto.getPageSize())));
    }

    /**
     * 查询所有文章
     *
     * @return ResponseResult<List < Article>>
     */
    @GetMapping("/list")
    public ResponseResult<List<Article>> list() {
        return ResponseResult.success(articleService.list());
    }

    /**
     * 创建文章
     *
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseResult<Long> createArticle(@RequestBody ArticleCreationDTO dto) {
        return ResponseResult.success(articleService.addArticle(dto));
    }

    /**
     * 查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    @GetMapping
    public ResponseResult<ArticleVO> findArticle(@RequestParam String articleId) {
        return ResponseResult.success(articleService.findById(articleId));
    }

    @PutMapping
    public ResponseResult<Void> updateArticle(@RequestParam ArticleUpdateDTO dto) {
        articleService.updateArticle(dto);
        return ResponseResult.success();
    }
}
