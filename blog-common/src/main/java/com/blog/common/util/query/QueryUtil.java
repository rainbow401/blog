package com.blog.common.util.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanzhihao
 * @since 2023/6/29
 */
public class QueryUtil {

    private static final Map<Class<?>, Map<String, Type>>
            CLASS_QUERY_EXPRESSION_CACHE = new ConcurrentHashMap<>(256);

    public static <T, R> QueryWrapper<T> convert(R data) throws IllegalAccessException {
        QueryWrapper<T> queryWrapper = Wrappers.query();

        // 获取data属性
        Class<?> clazz = data.getClass();
        Field[] fields = clazz.getDeclaredFields();

        // 获取缓存
        Map<String, Type> classExpression = CLASS_QUERY_EXPRESSION_CACHE.get(data.getClass());
        if (classExpression != null) {
            // 有缓存
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(data);
                String name = field.getName();
                if (value != null) {
                    Type queryExpressionValue = classExpression.getOrDefault(field.getName(), Type.EQ);
                    setExpression(queryWrapper, name, queryExpressionValue, value);
                }
            }
        } else {
            // 没有缓存
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(data);
                if (value != null) {

                    String name = field.getName();
                    Type queryExpressionValue = getOrDefault(field, Type.EQ);

                    // 保存缓存
                    Map<String, Type> cache = CLASS_QUERY_EXPRESSION_CACHE
                                    .computeIfAbsent(clazz, (key) -> new HashMap<>());
                    cache.put(name, queryExpressionValue);

                    // 设置queryWrapper
                    setExpression(queryWrapper, name, queryExpressionValue, value);
                }
            }
        }

        return queryWrapper;
    }


    public static <T> QueryWrapper<T> convert(List<QueryParam> params) throws IllegalAccessException {
        QueryWrapper<T> queryWrapper = Wrappers.query();

        return queryWrapper;
    }

    private static Type getOrDefault(Field field, Type type) {
        QueryExpression annotation = field.getAnnotation(QueryExpression.class);
        return annotation != null ? annotation.value() : type;
    }

    private static <T> void setExpression(QueryWrapper<T> queryWrapper, String fieldName, Type queryExpressionValue, Object value) {
        switch (queryExpressionValue) {
            case EQ: {
                queryWrapper.eq(fieldName, value);
                break;
            }
            case NE: {
                queryWrapper.ne(fieldName, value);
                break;
            }
            case LIKE: {
                queryWrapper.like(fieldName, value);
                break;
            }
            case GT: {
                queryWrapper.gt(fieldName, value);
                break;
            }
            case GE: {
                queryWrapper.ge(fieldName, value);
                break;
            }
            case LT: {
                queryWrapper.lt(fieldName, value);
                break;
            }
            case LE: {
                queryWrapper.le(fieldName, value);
                break;
            }
            case NOT_NULL: {
                queryWrapper.isNotNull(fieldName);
                break;
            }
            case IS_NULL: {
                queryWrapper.isNull(fieldName);
                break;
            }
            case IN: {
                queryWrapper.in(fieldName, value);
                break;
            }
        }
    }
}
