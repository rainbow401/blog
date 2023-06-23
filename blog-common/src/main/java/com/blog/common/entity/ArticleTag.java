package com.blog.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanzhihao
 * @since 2022-07-06
 */
@Data
public class ArticleTag extends BaseEntity {
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;
}
