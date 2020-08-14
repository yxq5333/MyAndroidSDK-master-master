package com.crazyhuskar.myandroidsdk.db;

import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.crazyhuskar.myandroidsdk.util.XUtils.XDBUtils;

import org.xutils.ex.DbException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

/**
 *
 * 本地数据库
 * */
public class MyDBHelper<T> {
    public T findFirst() {

        try {
            return Objects.requireNonNull(XDBUtils.getDbManager()).findFirst(getTypeTClass());
        } catch (DbException e) {
            MyUtilLog.e(e.getMessage());
        }

        return null;
    }

    public List<T> findAll() {

        try {
            return Objects.requireNonNull(XDBUtils.getDbManager()).findAll(getTypeTClass());
        } catch (DbException e) {
            MyUtilLog.e(e.getMessage());
        }

        return null;
    }

    public void delete(T t) {
        try {
            Objects.requireNonNull(XDBUtils.getDbManager()).delete(t);
        } catch (DbException e) {
            MyUtilLog.e(e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            Objects.requireNonNull(XDBUtils.getDbManager()).delete(getTypeTClass());
        } catch (DbException e) {
            MyUtilLog.e(e.getMessage());
        }
    }

    public void save(T t) {
        try {
            Objects.requireNonNull(XDBUtils.getDbManager()).save(t);
        } catch (DbException e) {
            MyUtilLog.e(e.getMessage());
        }
    }

    public void save(List<T> t) {
        try {
            Objects.requireNonNull(XDBUtils.getDbManager()).save(t);
        } catch (DbException e) {
            MyUtilLog.e(e.getMessage());
        }
    }

    public void updateAll(T t) {
        deleteAll();
        save(t);
    }

    public void updateAll(List<T> t) {
        deleteAll();
        save(t);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getTypeTClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

}
