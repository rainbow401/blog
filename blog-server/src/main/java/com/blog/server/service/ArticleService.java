package com.blog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.entity.Article;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.dto.ArticleQueryDTO;
import com.blog.server.dto.ArticleUpdateDTO;
import com.blog.server.vo.ArticleVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yanzhihao
 * @since 2022-07-06
 */
public interface ArticleService extends IService<Article> {

    Page<Article> page(ArticleQueryDTO dto) throws IllegalAccessException;

    Long addArticle(ArticleCreationDTO dto);

    ArticleVO findById(String articleId);

    void updateArticle(ArticleUpdateDTO dto);

    void likeArticle(Long id);

    void collectArticle(Long id);

    void cancelLikeArticle(Long id);

    void cancelCollectArticle(Long id);

    void uploadMdArticle(Long tagId, List<MultipartFile> files) throws IOException;
}
