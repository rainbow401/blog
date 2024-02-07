package com.blog.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.Article;
import com.blog.common.util.query.Demo;
import com.blog.common.util.query.QueryUtil;
import com.blog.server.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueryParamConvertTest {

	@Autowired
	private ArticleMapper articleMapper;

	@Test
	void contextLoads() throws IllegalAccessException {
		Demo demo = new Demo();
		demo.setTitle("sdsad");
		QueryWrapper<Article> convert = QueryUtil.convert(demo, Object.class);

		articleMapper.selectList(convert);
	}



}
