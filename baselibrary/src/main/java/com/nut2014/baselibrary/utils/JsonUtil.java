package com.nut2014.baselibrary.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.nut2014.baselibrary.base.BaseResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {
    /**
     * json 转为 list
     *
     * @param json  字符串
     * @param clazz list类型
     * @param <T>   类型
     * @return list
     */
    public static <T> List<T> parseString2List(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        return JSON.parseObject(json, type);
    }

    public static final <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 解析带泛型的类
     *
     * @param jsonStr json
     * @param t       类型
     * @param <T>     类型
     * @return 类型
     * <p>
     * 使用方法
     * BaseResponse<String> stringBaseResponse = parseBaseResponse(text, "");
     */
    public static <T> BaseResponse<T> parseBaseResponse(String jsonStr, T t) {
        return JSON.parseObject(jsonStr, new TypeReference<BaseResponse<T>>() {
        });
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }


    }
}
