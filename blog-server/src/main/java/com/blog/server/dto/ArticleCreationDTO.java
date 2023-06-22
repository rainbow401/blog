package com.blog.server.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ArticleCreationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @NotBlank(message = "请填写文章标题")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "请填写文章标题")
    private String content;

    /**
     * 标签
     */
    @NotNull(message = "请选择标签")
    private List<Long> tagIdList;
}
