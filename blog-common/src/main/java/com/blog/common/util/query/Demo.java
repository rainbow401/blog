package com.blog.common.util.query;

import lombok.Data;

@Data
public class Demo {

    @QueryExpression(value = Type.EQ)
    private String title;
}