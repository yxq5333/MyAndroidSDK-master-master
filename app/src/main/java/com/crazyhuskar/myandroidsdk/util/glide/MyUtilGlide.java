package com.crazyhuskar.myandroidsdk.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.EmptySignature;
import com.crazyhuskar.myandroidsdk.SystemConfig;
import com.crazyhuskar.myandroidsdk.util.MyUtil;
import com.crazyhuskar.myandroidsdk.util.MyUtilFile;

import java.io.File;
import java.io.IOException;


/**
 * @author CrazyHuskar
 * @date 2018/6/11
 */
public class MyUtilGlide {

    /**
     * 图片加载
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     */
    public static void load(Context context, ImageView imageView, Object url) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop();
        Glide.with(context).load(url).apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 图片加载
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage   加载失败后图片
     */
    public static void load(Context context, ImageView imageView, Object url, int defaultImage,
                            int errorImage) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .placeholder(defaultImage)
                .error(errorImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop();
        Glide.with(context).load(url).apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 圆角图片加载
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage   加载失败后图片
     * @param radius       图片圆角半径
     */
    public static void loadByRound(Context context, ImageView imageView, Object url, int defaultImage,
                                   int errorImage, int radius) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .dontAnimate()
                .placeholder(defaultImage)
                .error(errorImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(new GlideRoundTransform(context, radius));
        Glide.with(context).load(url).apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage   加载失败后图片
     */
    public static void loadByCircle(Context context, ImageView imageView, Object url, int defaultImage,
                                    int errorImage) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .dontAnimate()
                .placeholder(defaultImage)
                .error(errorImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(new GlideCircleTransform(context));
        Glide.with(context).load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 加载高斯模糊图片
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage   加载失败后图片
     */
    public static void loadByBlur(Context context, ImageView imageView, Object url, int defaultImage,
                                  int errorImage) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .dontAnimate()
                .placeholder(defaultImage)
                .error(errorImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(new BlurTransformation(context));
        Glide.with(context).load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 自定义加载图片
     *
     * @param context        上下文
     * @param url            图片链接
     * @param defaultImage   默认占位图片
     * @param errorImage     加载失败后图片
     * @param transformation transformation
     * @param simpleTarget   simpleTarget
     */
    public static void loadByDefined(Context context, Object url, int defaultImage,
                                     int errorImage, Transformation<Bitmap> transformation, SimpleTarget<Drawable> simpleTarget) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .dontAnimate()
                .placeholder(defaultImage)
                .error(errorImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(transformation);
        Glide.with(context).load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(simpleTarget);
    }

    /**
     * 获取缓存图片
     *
     * @param url     图片链接
     * @param context 上下文
     * @return 图片
     */
    public static File getCacheFile(String url, Context context) {
        OriginalKey originalKey = new OriginalKey(url, EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(originalKey);
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(new File(context.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Glide磁盘缓存大小
     *
     * @return 大小
     */
    public static String getCacheSize() {
        try {
            return MyUtil.getFormatSize(MyUtilFile.getFolderSize(new File(SystemConfig.PHOTO_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    /**
     * 清除Glide磁盘缓存，自己获取缓存文件夹并删除方法
     *
     * @return 状态
     */
    public static boolean cleanCatchDisk() {
        return MyUtilFile.deleteFolderFile(SystemConfig.PHOTO_CACHE_DIR, true);
    }

}
