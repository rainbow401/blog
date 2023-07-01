package com.blog.common.util.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
        /**
         * 等于
         */
        EQ("eq"),
        /**
         * 不等于
         */
        NE("ne"),
        /**
         * 模糊
         */
        LIKE("like"),
        /**
         * 大于
         */
        GT("gt"),
        /**
         * 大于等于
         */
        GE("ge"),
        /**
         * 小于
         */
        LT("lt"),
        /**
         * 小于等于
         */
        LE("le"),
        NOT_NULL("not null"),
        IS_NULL("is null"),
        IN("in");

        private final String value;
    }