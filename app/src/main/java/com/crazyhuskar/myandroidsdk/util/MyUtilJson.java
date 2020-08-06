package com.crazyhuskar.myandroidsdk.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */

public class MyUtilJson {
    /**
     * 把JSON文本parse成JsonObject
     *
     * @param text json文本
     * @return json对象
     */
    public static JsonObject parseObject(String text) {
        try {
            return new JsonParser().parse(text).getAsJsonObject();
        } catch (Exception e) {
            return new JsonParser().parse("{}").getAsJsonObject();
        }
    }

    /**
     * 把JSON文本parse成JsonArray
     *
     * @param text json文本
     * @return josnarray
     */
    public static JsonArray parseArray(String text) {
        try {
            return new JsonParser().parse(text).getAsJsonArray();
        } catch (Exception e) {
            return new JsonParser().parse("[]").getAsJsonArray();
        }
    }

    /**
     * 把JSON文本parse为JavaBean
     *
     * @param text  json文本
     * @param clazz javabean
     * @param <T>   javabean
     * @return javabean
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(text, clazz);
        } catch (Exception e) {
            return gson.fromJson("{}", clazz);
        }
    }

    /**
     * 把Map parse为JavaBean
     *
     * @param map   map
     * @param clazz javabean
     * @return javabean
     */
    public static <T> T parseObject(Map<String, Object> map, Class<T> clazz) {
        return parseObject(toJSONString(map), clazz);
    }

    /**
     * 把JSON文本parse为Map
     *
     * @param jsonStr json文本
     * @return map
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            return gson.fromJson("{}", new TypeToken<Map<String, Object>>() {
            }.getType());
        }
    }

    /**
     * 把JSON文本parse成JavaBean集合
     *
     * @param text  json文本
     * @param clazz 对象
     * @param <T>   对象
     * @return list
     */
    public static <T> List<T> parseList(String text, Class<T> clazz) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(text).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, clazz));
            }
        } catch (Exception e) {
        }
        return list;

    }

    /**
     * 将JavaBean序列化为JSON文本
     *
     * @param object 对象
     * @return 文本
     */
    public static String toJSONString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
