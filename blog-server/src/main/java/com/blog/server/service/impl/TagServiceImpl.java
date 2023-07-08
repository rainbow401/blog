package com.blog.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.entity.Tag;
import com.blog.server.mapper.TagMapper;
import com.blog.server.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yanzhihao
 * @since 2022-07-06
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long add(String name) {
        boolean exists = tagMapper.exists(Wrappers.lambdaQuery(Tag.class).eq(Tag::getName, name));
        Assert.isTrue(!exists, "名称已存在");

        Tag tag = new Tag();
        tag.setName(name);
        this.save(tag);

        return tag.getId();
    }
}
