package com.blog.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.entity.Article;
import com.blog.common.entity.ArticleTag;
import com.blog.common.entity.BaseEntity;
import com.blog.common.entity.Tag;
import com.blog.server.component.context.ServiceContext;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.convert.ArticleConvertMapper;
import com.blog.server.dto.ArticleUpdateDTO;
import com.blog.server.mapper.ArticleMapper;
import com.blog.server.mapper.ArticleTagMapper;
import com.blog.server.mapper.TagMapper;
import com.blog.server.service.ArticleService;
import com.blog.server.service.ArticleTagService;
import com.blog.server.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yanzhihao
 * @since 2022-07-06
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleTagMapper articleTagMapper;
    private final TagMapper tagMapper;

    private final ArticleTagService articleTagService;

    private final ServiceContext ctx;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addArticle(ArticleCreationDTO dto) {

        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Article::getTitle, dto.getTitle());
        if (articleMapper.selectList(queryWrapper).size() > 0) {
            throw new IllegalStateException("文章标题重复，请重新填写");
        }

        Article article = ArticleConvertMapper.convertDtoToEntity(dto);
        article.setCreateBy(ctx.currentUserId());
        articleMapper.insert(article);

        insertArticleTag(article.getId(), dto.getTagIdList());

        return article.getId();
    }

    private void insertArticleTag(Long articleId, List<Long> tagIdList) {

        List<ArticleTag> articleTagList = new ArrayList<>();
        for (Long tagId : tagIdList) {
            ArticleTag temp = new ArticleTag();
            temp.setArticleId(articleId);
            temp.setTagId(tagId);
            temp.setCreateBy(ctx.currentUserId());
            articleTagList.add(temp);
        }

        articleTagService.saveBatch(articleTagList);
    }

    @Override
    public ArticleVO findById(String articleId) {
        ArticleVO vo = new ArticleVO();
        Article article = articleMapper.selectById(articleId);
        List<String> tagNameList = articleTagMapper.findAllTagNameByArticleId(articleId);
        vo.setArticle(article);
        vo.setTagNameList(tagNameList);
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticle(ArticleUpdateDTO dto) {
        LambdaQueryWrapper<Article> articleQuery = Wrappers.lambdaQuery();
        articleQuery
                .eq(Article::getTitle, dto.getTitle())
                .ne(Article::getId, dto.getArticleId());
        Long articleCount = articleMapper.selectCount(articleQuery);
        if (0 != articleCount) {
            throw new IllegalStateException("文章标题重复");
        }

        LambdaQueryWrapper<Tag> tagQuery = Wrappers.lambdaQuery();
        tagQuery
                .in(Tag::getId, dto.getTagIdList());
        Long tagCount = tagMapper.selectCount(tagQuery);
        if (dto.getTagIdList().size() != tagCount) {
            throw new IllegalStateException("标签不存在");
        }

        Article article = ArticleConvertMapper.convertDtoToEntity(dto);
        article.setUpdateBy(ctx.currentUserId());
        articleMapper.updateById(article);

        LambdaUpdateWrapper<ArticleTag> articleTagUpdate = Wrappers.lambdaUpdate();
        articleTagUpdate
                .eq(ArticleTag::getArticleId, dto.getArticleId())
                .set(BaseEntity::getUpdateBy, ctx.currentUserId())
                .set(BaseEntity::getDeleted, true);
        articleTagMapper.update(null, articleTagUpdate);

        insertArticleTag(dto.getArticleId(), dto.getTagIdList());
    }
}
