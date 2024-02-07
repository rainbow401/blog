package com.blog.common.util.query;

import lombok.Data;

@Data
public class Demo {

    @QueryExpression(type = Type.EQ)
    private String title;
}