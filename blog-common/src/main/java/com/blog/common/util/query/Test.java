package com.blog.common.util.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class Test {

    public static void main(String[] args) throws IllegalAccessException {
        Demo demo = new Demo();
        demo.setTitle("1");

        QueryWrapper<Test> convert = QueryUtil.convert(demo, Object.class);
    }


}
