package com.blog.common.util.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanzhihao
 * @since 2023/6/29
 */
public class QueryUtil {

    private static final Map<Class<?>, Map<String, Type>>
            CLASS_QUERY_EXPRESSION_CACHE = new ConcurrentHashMap<>(256);

    private static final Map<Class<?>, Map<String, QueryExpression>>
            CLASS_QUERY_EXPRESSION_ANNOTATION_CACHE = new ConcurrentHashMap<>(256);

    public static <T, R> QueryWrapper<T> convert(R data, Class<?> groupClazz) throws IllegalAccessException {
        QueryWrapper<T> queryWrapper = Wrappers.query();

        // 获取data属性
        Class<?> clazz = data.getClass();
        Map<String, QueryExpression> classQueryExpressionAnnotationMap = CLASS_QUERY_EXPRESSION_ANNOTATION_CACHE.get(clazz);
        Field[] fields = clazz.getDeclaredFields();


        if (classQueryExpressionAnnotationMap == null) {
            classQueryExpressionAnnotationMap = new HashMap<>();
            for (Field field : fields) {

                field.setAccessible(true);

                QueryExpression annotation = field.getAnnotation(QueryExpression.class);
                if (annotation == null) {
                    continue;
                }

                String fieldName = field.getName();
                classQueryExpressionAnnotationMap.put(fieldName, annotation);

                Object fieldValue = field.get(data);
                if (isIllegalValue(fieldValue)) {
                    continue;
                }

                Type type = annotation.type();
                Class<?>[] group = annotation.group();
                List<Class<?>> groupList = Arrays.asList(group);
                if (!groupList.contains(groupClazz)) {
                    continue;
                }

                setExpression(queryWrapper, fieldName, type, fieldValue);
            }

            CLASS_QUERY_EXPRESSION_ANNOTATION_CACHE.put(clazz, classQueryExpressionAnnotationMap);
        } else {
            for (Field field : fields) {

                field.setAccessible(true);

                String fieldName = field.getName();

                QueryExpression annotation = classQueryExpressionAnnotationMap.get(fieldName);

                Object fieldValue = field.get(data);
                if (isIllegalValue(fieldValue)) {
                    continue;
                }

                Type type = annotation.type();
                Class<?>[] group = annotation.group();
                List<Class<?>> groupList = Arrays.asList(group);
                if (!groupList.contains(groupClazz)) {
                    continue;
                }

                setExpression(queryWrapper, fieldName, type, fieldValue);
            }
        }

        return queryWrapper;
    }

    private static Boolean isIllegalValue(Object fieldValue) throws IllegalAccessException {
        if (fieldValue == null) {
            return true;
        }

        if (fieldValue instanceof String) {
            return StringUtils.isBlank((String) fieldValue);
        }

        return false;
    }

    public static <T, R> LambdaQueryWrapper<T> convertLambdaQuery(R data) throws IllegalAccessException {
        QueryWrapper<T> queryWrapper = convert(data, Object.class);
        return queryWrapper.lambda();
    }

    public static <T, R> LambdaQueryWrapper<T> convertLambdaQuery(R data, Class<?> groupClazz) throws IllegalAccessException {
        QueryWrapper<T> queryWrapper = convert(data, groupClazz);
        return queryWrapper.lambda();
    }


    public static <T> QueryWrapper<T> convert(List<QueryParam> params) throws IllegalAccessException {
        QueryWrapper<T> queryWrapper = Wrappers.query();

        return queryWrapper;
    }

    private static <T> void setExpression(QueryWrapper<T> queryWrapper, String fieldName, Type queryExpressionValue, Object value) {

        fieldName = camelToSnake(fieldName);

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

    public static String camelToSnake(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        char[] chars = camelCase.toCharArray();

        for (char c : chars) {
            // Convert uppercase letter to underscore followed by lowercase letter
            if (Character.isUpperCase(c)) {
                snakeCase.append('_').append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }

        return snakeCase.toString();
    }
}
