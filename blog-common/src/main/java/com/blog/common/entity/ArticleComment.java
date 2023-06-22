package com.blog.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yanzhihao
 * @since 2022-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleComment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论id
     */
    private Long commentId;
}
