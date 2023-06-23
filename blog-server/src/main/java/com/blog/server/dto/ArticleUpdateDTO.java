package com.blog.server.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ArticleUpdateDTO {

    /**
     * 文章id
     */
    @NotNull
    private Long articleId;

    /**
     * 文章标题
     */
    @NotNull
    @Length(min = 1, max = 100)
    private String title;

    /**
     * 文章内容
     */
    @NotNull
    @Length(min = 1, max = 65535)
    private String content;

    /**
     * 文章tag
     */
    @NotNull
    @Size(min = 1)
    private List<Long> tagIdList;
}
