package com.blog.server.dto;

import com.blog.common.dto.PageDTO;
import com.blog.common.util.query.QueryExpression;
import com.blog.common.util.query.Type;
import lombok.Data;

@Data
public class ArticleQueryDTO extends PageDTO {

    @QueryExpression(value = Type.LIKE)
    private String title;

    @QueryExpression(value = Type.EQ)
    private String tagId;
    @QueryExpression(value = Type.EQ)
    private Long createBy;
}
