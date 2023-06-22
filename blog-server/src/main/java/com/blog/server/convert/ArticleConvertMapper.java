package com.blog.server.convert;

import com.blog.common.entity.Article;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.dto.ArticleUpdateDTO;

public class ArticleConvertMapper {

    public static Article convertDtoToEntity(ArticleCreationDTO dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return article;
    }

    public static Article convertDtoToEntity(ArticleUpdateDTO dto) {
        Article article = new Article();
        article.setId(dto.getArticleId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return article;
    }
}
