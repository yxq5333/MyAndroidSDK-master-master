package com.crazyhuskar.myandroidsdk.util.XUtils;

import android.view.View;
import android.widget.ImageView.ScaleType;

/**
 * 获取图片配置信息<br><br>
 *
 *  view        要加载图片的控件，ImageView自动填充，其他用过监听返回结果<br>
 *  url         图片地址<br>
 *  loadIconRes 加载中显示的图片资源<br>
 *  failIconRes 加载失败显示的图片资源<br>
 *  widthDp     要加载的图片宽度，<=0视为加载原图宽度<br>
 *  heightDp    要加载的图片高度，<=0视为加载原图高度<br>
 *  radiusDp    圆角，<=0视为不加圆角<br>
 *  isCircular  是否加载圆形图片<br>
 *  scaleType   ImageView的填充方式<br>
 *  isFadeIn    是否要渐变填充<br>
 */
public class LoadImageOptions {

    public View view;
    public String url;
    public int loadIconRes;
    public int failIconRes;
    public int widthDp;
    public int heightDp;
    public int radiusDp;
    public boolean isCircular;
    public ScaleType scaleType;
    public boolean isFadeIn;

    /**
     * 新建配置
     */
    public LoadImageOptions() {

        this.url = "";
        this.failIconRes = 0;
        this.scaleType = ScaleType.CENTER_CROP;
        this.view = null;
        this.loadIconRes = 0;
        this.widthDp = 0;
        this.heightDp = 0;
        this.radiusDp = 0;
        this.isCircular = false;

        this.isFadeIn = true;
    }

    /**
     * 新建配置
     *
     * @param url         图片地址
     * @param failIconRes 加载失败显示的图片资源
     * @param scaleType   ImageView的填充方式
     */
    public LoadImageOptions(String url, int failIconRes, ScaleType scaleType) {

        this.url = url;
        this.failIconRes = failIconRes;
        this.scaleType = scaleType;

        this.view = null;
        this.loadIconRes = failIconRes;
        this.widthDp = 0;
        this.heightDp = 0;
        this.radiusDp = 0;
        this.isCircular = false;
        this.isFadeIn = true;
    }

    /**
     * 新建配置
     *
     * @param view        要加载图片的控件，ImageView自动填充，其他用过监听返回结果
     * @param url         图片地址
     * @param failIconRes 加载失败显示的图片资源
     * @param scaleType   ImageView的填充方式
     */
    public LoadImageOptions(View view, String url, int failIconRes, ScaleType scaleType) {

        this.view = view;
        this.url = url;
        this.failIconRes = failIconRes;
        this.scaleType = scaleType;
        this.loadIconRes = failIconRes;

        this.widthDp = 0;
        this.heightDp = 0;
        this.radiusDp = 0;
        this.isCircular = false;
        this.isFadeIn = true;
    }

    /**
     * 新建配置
     *
     * @param url         图片地址
     * @param loadIconRes 加载中显示的图片资源
     * @param failIconRes 加载失败显示的图片资源
     * @param widthDp     要加载的图片宽度，<=0视为加载原图宽度
     * @param heightDp    要加载的图片高度，<=0视为加载原图高度
     * @param radiusDp    圆角，<=0视为不加圆角
     * @param isCircular  是否加载圆形图片
     * @param scaleType   ImageView的填充方式
     */
    public LoadImageOptions(String url, int loadIconRes, int failIconRes, int widthDp, int heightDp, int radiusDp,
                            boolean isCircular, ScaleType scaleType) {

        this.url = url;
        this.loadIconRes = loadIconRes;
        this.failIconRes = failIconRes;
        this.scaleType = scaleType;
        this.widthDp = widthDp < 0 ? 0 : widthDp;
        this.heightDp = heightDp < 0 ? 0 : heightDp;
        this.radiusDp = radiusDp < 0 ? 0 : radiusDp;
        this.isCircular = isCircular;

        this.view = null;
        this.isFadeIn = true;
    }

    /**
     * 新建配置
     *
     * @param view        要加载图片的控件，ImageView自动填充，其他用过监听返回结果
     * @param url         图片地址
     * @param loadIconRes 加载中显示的图片资源
     * @param failIconRes 加载失败显示的图片资源
     * @param widthDp     要加载的图片宽度，<=0视为加载原图宽度
     * @param heightDp    要加载的图片高度，<=0视为加载原图高度
     * @param radiusDp    圆角，<=0视为不加圆角
     * @param isCircular  是否加载圆形图片
     * @param scaleType   ImageView的填充方式
     */
    public LoadImageOptions(View view, String url, int loadIconRes, int failIconRes, int widthDp, int heightDp,
                            int radiusDp, boolean isCircular, ScaleType scaleType) {

        this.view = view;
        this.url = url;
        this.loadIconRes = loadIconRes;
        this.failIconRes = failIconRes;
        this.scaleType = scaleType;
        this.widthDp = widthDp < 0 ? 0 : widthDp;
        this.heightDp = heightDp < 0 ? 0 : heightDp;
        this.radiusDp = radiusDp < 0 ? 0 : radiusDp;
        this.isCircular = isCircular;

        this.isFadeIn = true;
    }

}
