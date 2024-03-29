package com.blog.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.entity.Article;
import com.blog.common.resopnse.ResponseResult;
import com.blog.server.annotation.Permission;
import com.blog.server.component.context.ServiceContext;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.dto.ArticleQueryDTO;
import com.blog.server.dto.ArticleUpdateDTO;
import com.blog.server.service.ArticleService;
import com.blog.server.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 文章
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

    private final ServiceContext ctx;

    /**
     * 分页查询文章
     *
     * @param dto 分页参数
     * @return ResponseResult<Page<Article>>
     */
    @PostMapping("/page")
    public ResponseResult<Page<Article>> page(@RequestBody ArticleQueryDTO dto) throws IllegalAccessException {
        return ResponseResult.success(articleService.page(dto));
    }

    /**
     * 查询所有文章
     *
     * @return ResponseResult<List<Article>>
     */
    @GetMapping("/list")
    public ResponseResult<List<Article>> list() {
        return ResponseResult.success(articleService.list());
    }

    /**
     * 创建文章
     *
     * @param dto 参数
     * @return ResponseResult<Long>
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

    /**
     * 更新文章
     * @param dto 参数
     * @return ResponseResult<Void>
     */
    @Permission
    @PutMapping
    public ResponseResult<Void> updateArticle(@RequestParam ArticleUpdateDTO dto) {
        articleService.updateArticle(dto);
        return ResponseResult.success();
    }

    /**
     * 批量上传md
     * @param files md
     */
    @Permission
    @PostMapping("/{tagId}/upload")
    public ResponseResult<Void> uploadMdArticle(
            @RequestPart("files") List<MultipartFile> files,
            @PathVariable("tagId") Long tagId,
            @RequestParam(required = false) Long tenantId) throws IOException {
        articleService.uploadMdArticle(tagId, tenantId, files);
        return ResponseResult.success();
    }

    //    /**
//     * 点赞文章
//     * @return ResponseResult<Void>
//     */
//    @PutMapping("/{id}/like")
//    public ResponseResult<Void> likeArticle(@PathVariable("id") Long id) {
//        articleService.likeArticle(id);
//        return ResponseResult.success();
//    }
//
//    /**
//     * 取消点赞文章
//     * @return ResponseResult<Void>
//     */
//    @PutMapping("/{id}/cancel/like")
//    public ResponseResult<Void> cancelLikeArticle(@PathVariable("id") Long id) {
//        articleService.cancelLikeArticle(id);
//        return ResponseResult.success();
//    }
//
//    /**
//     * 收藏文章
//     * @return ResponseResult<Void>
//     */
//    @PutMapping("/{id}/collect")
//    public ResponseResult<Void> collectArticle(@PathVariable("id") Long id) {
//        articleService.collectArticle(id);
//        return ResponseResult.success();
//    }
//
//    /**
//     * 取消收藏文章
//     * @return ResponseResult<Void>
//     */
//    @PutMapping("/{id}/cancel/collect")
//    public ResponseResult<Void> cancelCollectArticle(@PathVariable("id") Long id) {
//        articleService.cancelCollectArticle(id);
//        return ResponseResult.success();
//    }
}
