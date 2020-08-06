package com.crazyhuskar.myandroidsdk.base.mvp;

/**
 * @author CrazyHuskar
 * @date 2018/6/12
 */
public class MyDataModel {
    public static MyBaseModel request(String token) {
        MyBaseModel model = null;
        try {
            model = (MyBaseModel) Class.forName(token).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return model;
    }
}
