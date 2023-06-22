package com.blog.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.ArticleTag;
import com.blog.common.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yanzhihao
 * @since 2022-07-06
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    List<String> findAllTagNameByArticleId(@Param("articleId") String articleId);

    int removeByArticleId(Long articleId);
}
