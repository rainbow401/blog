package com.blog.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.entity.Article;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.dto.ArticleUpdateDTO;
import com.blog.server.vo.ArticleVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yanzhihao
 * @since 2022-07-06
 */
public interface ArticleService extends IService<Article> {

    Long addArticle(ArticleCreationDTO dto);

    ArticleVO findById(String articleId);

    void updateArticle(ArticleUpdateDTO dto);
}
