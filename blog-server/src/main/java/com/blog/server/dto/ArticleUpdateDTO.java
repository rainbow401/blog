package com.blog.server.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ArticleUpdateDTO {

    @NotNull
    private Long articleId;

    @NotNull
    @Length(min = 1, max = 100)
    private String title;

    @NotNull
    @Length(min = 1, max = 65535)
    private String content;

    @NotNull
    @Size(min = 1)
    private List<Long> tagIdList;
}
