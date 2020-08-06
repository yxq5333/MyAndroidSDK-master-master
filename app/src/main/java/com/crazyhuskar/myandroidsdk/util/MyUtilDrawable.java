package com.crazyhuskar.myandroidsdk.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilDrawable {
    /**
     * 将View转成bitmap
     * 屏幕大小
     *
     * @param v
     * @return
     */
    public static Bitmap convertViewToBitmap(View v, Context context) {
        v.layout(0, 0, MyUtilDeviceInfo.getScreenWidth(context), MyUtilDeviceInfo.getScreenHeight(context));
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(MyUtilDeviceInfo.getScreenWidth(context), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(MyUtilDeviceInfo.getScreenHeight(context), View.MeasureSpec.UNSPECIFIED);
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        v.draw(c);
        return bmp;
    }

    /**
     * 将View转成bitmap
     * View大小
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * Bitmap转成圆形
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        // 圆形图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 正方形的边长
        int r = 0;
        // 取最短边做边长
        if (width > height) {
            r = height;
        } else {
            r = width;
        }
        // 构建一个bitmap
        Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        // new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        // 设置边缘光滑，去掉锯齿
        paint.setAntiAlias(true);
        // 宽高相等，即正方形
        RectF rect = new RectF(0, 0, r, r);
        // 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        // 且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, paint);
        // 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);
        // 返回已经绘画好的backgroundBmp
        return backgroundBmp;
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param path
     * @param name
     * @param quality 质量 0-100
     * @param isPng   是否为PNG图片
     */
    public static void savePhoto(Bitmap bitmap, String path, String name, int quality, boolean isPng) {

        FileOutputStream b = null;

        File file = new File(path);
        if (!file.exists()) {
            // 创建文件夹
            file.mkdirs();
        }
        String fileName = path + name;

        try {
            b = new FileOutputStream(fileName);
            if (isPng) {
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, b);// 把数据写入文件
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, b);// 把数据写入文件
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static final Bitmap createBitmapFromPath(String path, Context context) {
        return createBitmapFromPath(path, context, MyUtilDeviceInfo.getScreenWidth(context), MyUtilDeviceInfo.getScreenHeight(context));
    }

    public static final Bitmap createBitmapFromPath(String path, Context context, int maxResolutionX, int maxResolutionY) {
        Bitmap bitmap = null;
        Options options = null;
        if (path.endsWith(".3gp")) {
            return ThumbnailUtils.createVideoThumbnail(path, 1);
        } else {
            try {
                options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                int e = options.outWidth;
                int height = options.outHeight;
                options.inSampleSize = computeBitmapSimple(e * height, maxResolutionX * maxResolutionY);
                options.inPurgeable = true;
                options.inPreferredConfig = Config.RGB_565;
                options.inDither = false;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(path, options);
                return rotateBitmapByExif(bitmap, path, true);
            } catch (OutOfMemoryError var8) {
                options.inSampleSize *= 2;
                bitmap = BitmapFactory.decodeFile(path, options);
                return rotateBitmapByExif(bitmap, path, true);
            } catch (Exception var9) {
                var9.printStackTrace();
                return null;
            }
        }
    }

    public static final Bitmap createBitmapFromPath(byte[] data, Context context) {
        Bitmap bitmap = null;
        Options options = null;

        try {
            WindowManager e = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = e.getDefaultDisplay();
            int screenW = display.getWidth();
            int screenH = display.getHeight();
            options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            int width = options.outWidth;
            int height = options.outHeight;
            int maxResolutionX = screenW * 2;
            int maxResolutionY = screenH * 2;
            options.inSampleSize = computeBitmapSimple(width * height, maxResolutionX * maxResolutionY);
            options.inPurgeable = true;
            options.inPreferredConfig = Config.RGB_565;
            options.inDither = false;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            return bitmap;
        } catch (OutOfMemoryError var12) {
            options.inSampleSize *= 2;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            return bitmap;
        } catch (Exception var13) {
            var13.printStackTrace();
            return null;
        }
    }

    public static int computeBitmapSimple(int realPixels, int maxPixels) {
        try {
            if (realPixels <= maxPixels) {
                return 1;
            } else {
                int e;
                for (e = 2; realPixels / (e * e) > maxPixels; e *= 2) {
                    ;
                }

                return e;
            }
        } catch (Exception var3) {
            return 1;
        }
    }

    public static Bitmap rotateBitmapByExif(Bitmap bitmap, String path, boolean isRecycle) {
        int digree = getBitmapExifRotate(path);
        if (digree != 0) {
            bitmap = rotate((Context) null, bitmap, digree, isRecycle);
        }

        return bitmap;
    }

    public static int getBitmapExifRotate(String path) {
        short digree = 0;
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(path);
        } catch (IOException var4) {
            var4.printStackTrace();
            return 0;
        }

        if (exif != null) {
            int ori = exif.getAttributeInt("Orientation", 0);
            switch (ori) {
                case 3:
                    digree = 180;
                    break;
                case 6:
                    digree = 90;
                    break;
                case 8:
                    digree = 270;
                    break;
                default:
                    digree = 0;
            }
        }

        return digree;
    }

    public static Bitmap rotate(Context context, Bitmap bitmap, int degree, boolean isRecycle) {
        Matrix m = new Matrix();
        m.setRotate((float) degree, (float) bitmap.getWidth() / 2.0F, (float) bitmap.getHeight() / 2.0F);

        try {
            Bitmap ex = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            if (isRecycle) {
                bitmap.recycle();
            }

            return ex;
        } catch (OutOfMemoryError var6) {
            var6.printStackTrace();
            return null;
        }
    }

    /**
     * 获取视频的缩略图 本地/网络
     *
     * @param filePath 地址
     * @param kind     MediaStore.Video.Thumbnails.MICRO_KIND或MediaStore.Video.Thumbnails.MINI_KIND
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath, int kind) {

        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {
            return null;
        }

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }

        return bitmap;
    }

}
