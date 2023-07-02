package com.blog.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.entity.*;
import com.blog.common.util.query.QueryUtil;
import com.blog.server.component.context.ServiceContext;
import com.blog.server.dto.ArticleCreationDTO;
import com.blog.server.convert.ArticleConvertMapper;
import com.blog.server.dto.ArticleQueryDTO;
import com.blog.server.dto.ArticleUpdateDTO;
import com.blog.server.mapper.*;
import com.blog.server.service.ArticleService;
import com.blog.server.service.ArticleTagService;
import com.blog.server.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final ArticleLikeMapper articleLikeMapper;
    private final ArticleCollectionMapper articleCollectionMapper;
    private final TagMapper tagMapper;

    private final ArticleTagService articleTagService;

    private final ServiceContext ctx;

    @Override
    public Page<Article> page(ArticleQueryDTO dto) throws IllegalAccessException {
        QueryWrapper<Article> query = QueryUtil.convert(dto);
        Page<Article> page = new Page<>(dto.getPageNo(), dto.getPageSize());

        if (dto.getMyself()) {
            // 仅查看我自己
            LambdaQueryWrapper<Article> lambdaQuery = query.lambda();
            lambdaQuery.eq(BaseEntity::getCreateBy, ctx.currentUserId());

            return articleMapper.selectPage(page, lambdaQuery);
        } else {
            return articleMapper.selectPage(page, query);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addArticle(ArticleCreationDTO dto) {

        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Article::getTitle, dto.getTitle());
        if (articleMapper.selectList(queryWrapper).size() > 0) {
            throw new IllegalStateException("文章标题重复，请重新填写");
        }

        Article article = ArticleConvertMapper.convertDtoToEntity(dto);
        articleMapper.insert(article);

        insertArticleTag(article.getId(), dto.getTagIdList());

        return article.getId();
    }

    public void insertArticleTag(Long articleId, List<Long> tagIdList) {

        List<ArticleTag> articleTagList = new ArrayList<>();
        for (Long tagId : tagIdList) {
            ArticleTag temp = new ArticleTag();
            temp.setArticleId(articleId);
            temp.setTagId(tagId);
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
        articleMapper.updateById(article);

        LambdaQueryWrapper<ArticleTag> articleTagUpdate = Wrappers.lambdaQuery();
        articleTagUpdate
                .eq(ArticleTag::getArticleId, dto.getArticleId());
        articleTagMapper.delete(articleTagUpdate);

        insertArticleTag(dto.getArticleId(), dto.getTagIdList());
    }

    @Override
    public void likeArticle(Long id) {

        validArticleExist(id);

        ArticleLike articleLike = new ArticleLike();
        articleLike.setArticleId(id);
        articleLike.setUserId(ctx.currentUserId());
        articleLikeMapper.insert(articleLike);
    }

    @Override
    public void collectArticle(Long id) {

        validArticleExist(id);

        ArticleCollection articleCollection = new ArticleCollection();
        articleCollection.setArticleId(id);
        articleCollection.setUserId(ctx.currentUserId());
        articleCollectionMapper.insert(articleCollection);
    }

    @Override
    public void cancelLikeArticle(Long id) {
        LambdaQueryWrapper<ArticleLike> queryWrapper = Wrappers.lambdaQuery(ArticleLike.class);
        queryWrapper.eq(ArticleLike::getArticleId, id);
        queryWrapper.eq(ArticleLike::getUserId, ctx.currentUserId());

        articleLikeMapper.delete(queryWrapper);
    }

    @Override
    public void cancelCollectArticle(Long id) {
        LambdaQueryWrapper<ArticleCollection> queryWrapper = Wrappers.lambdaQuery(ArticleCollection.class);
        queryWrapper.eq(ArticleCollection::getArticleId, id);
        queryWrapper.eq(ArticleCollection::getUserId, ctx.currentUserId());

        articleCollectionMapper.delete(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadMdArticle(Long tagId, List<MultipartFile> files) throws IOException {

        LambdaQueryWrapper<Tag> queryWrapper = Wrappers.lambdaQuery(Tag.class).eq(Tag::getId, tagId);
        boolean exists = tagMapper.exists(queryWrapper);

        Assert.isTrue(exists, "标签不存在");

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            Assert.isTrue(fileName != null, "文件名为空");

            // 验证文件扩展名
            Assert.isTrue(fileName.toLowerCase().endsWith(".md") ||
                            fileName.toLowerCase().endsWith(".markdown"),
                    "文件格式错误，只能导入`.md、.markdown`");

            importMdArticle(file);
        }
    }

    private void importMdArticle(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());

        Assert.hasText(content, "文章内容不能为空");

        // 提取标题
        String title = getFirstTitle(content);
        if (StringUtils.isBlank(title)) {
            // 提取不到标题内容则取前20个字符
            title = content.substring(0, Math.min(content.length(), 20)) + "...";
        }

        // 提取摘要
        String summary = getSummary(content);
        if (StringUtils.isBlank(summary)) {
            summary = content.substring(0, Math.min(content.length(), 100)) + "...";
        }

        Article article = new Article();
        article.setContent(content);
        article.setTitle(title);
        article.setSummary(summary);

        articleMapper.insert(article);
    }

    private String getSummary(String content) {
        String regex = "^(?!#).{1,}";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String text = matcher.group().trim();
            return text.substring(0, Math.min(text.length(), 100));
        }

        return null;
    }

    private static String getFirstTitle(String content) {
        String regex = "^(#+)\\s+(.*)$";

        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }

    public void validArticleExist(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new IllegalStateException("文章不存在");
        }
    }
}
