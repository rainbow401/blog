package com.blog.common.util.query;

import java.lang.annotation.*;

/**
 * @author yanzhihao
 * @since 2023/6/30
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryExpression {

    Type value() default Type.EQ;
}
