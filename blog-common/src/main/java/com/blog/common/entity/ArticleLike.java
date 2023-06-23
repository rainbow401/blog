package com.blog.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author yan zhihao
 * @since 2023-06-23
 */
@Data
public class ArticleLike extends BaseEntity {
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 用户id
     */
    private Long userId;
}
