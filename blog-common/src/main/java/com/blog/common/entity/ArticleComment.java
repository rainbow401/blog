package com.blog.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanzhihao
 * @since 2022-07-06
 */
@Data
public class ArticleComment extends BaseEntity {
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论id
     */
    private Long commentId;
}
