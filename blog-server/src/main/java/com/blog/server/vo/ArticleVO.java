package com.blog.server.vo;

import com.blog.common.entity.Article;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVO {

    private Article article;

    private List<String> tagNameList;
}
