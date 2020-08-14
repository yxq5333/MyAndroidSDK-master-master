package com.crazyhuskar.myandroidsdk.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * 类工具类
 * <p>
 * Created by
 */
public class MyUtilBean {

    /**
     * 复制
     * <p>
     * 改方法转为静态方法
     *
     * @param source 源类
     * @param type   源类类型
     * @param <T>    接收类型
     * @return 根据接收类型确定
     */
    public static <T> T copy(Object source, Type type) {

        if (source == null) {
            return null;
        }

        String str = new Gson().toJson(source);

        return new Gson().fromJson(str, type);
    }

    /**
     * 集合 是否为 null 或者 size = 0
     *
     * @param collection Collection<?> like List
     * @return boolean
     */
    public static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Map 是否为 null 或者 size = 0
     *
     * @param map Map<?, ?>
     * @return boolean
     */
    public static boolean isMapEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 对象 是否为 null
     *
     * @param obj Object
     * @return boolean
     */
    public static boolean isObjNull(Object obj) {
        return obj == null;
    }

    /**
     * 数组 是否为 null 或者 length = 0
     *
     * @param array Object[]
     * @return boolean
     */
    public static boolean isArrayEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * int数组 是否为 null 或者 length = 0
     *
     * @param array int[]
     * @return boolean
     */
    public static boolean isIntArrayEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * float数组 是否为 null 或者 length = 0
     *
     * @param array float[]
     * @return boolean
     */
    public static boolean isFloatArrayEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * double数组 是否为 null 或者 length = 0
     *
     * @param array double[]
     * @return boolean
     */
    public static boolean isDoubleArrayEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * boolean数组 是否为 null 或者 length = 0
     *
     * @param array boolean[]
     * @return boolean
     */
    public static boolean isBooleanArrayEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 获取Map的值
     *
     * @param map          Map
     * @param key          K 键
     * @param defaultValue T key获取的value为null时的返回值
     * @return Map的值
     */
    @SuppressWarnings({"unchecked"})
    public static <T, K, V> T getMapValue(Map<K, V> map, K key, T defaultValue) {

        if (map == null || key == null) {
            return null;
        }

        Object obj = map.get(key);

        if (defaultValue == null) {
            return (T) obj;
        }

        return obj == null ? defaultValue : (T) obj;
    }
}
