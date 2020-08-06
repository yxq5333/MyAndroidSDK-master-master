package com.crazyhuskar.myandroidsdk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.R;


/**
 * Created by JSK on 2017/3/17.
 */
@SuppressLint("NewApi")
public class CommonTitle {
    /**
     * 公共标题左边图片
     **/
    private ImageButton leftTitleImg;
    /**
     * 公共标题右边图片
     **/
    private ImageButton rightTitleImg;
    /**
     * 公共标题左边文字
     **/
    private TextView leftTitleText;
    /**
     * 公共标题右边文字片
     **/
    private TextView rightTitleText;
    /**
     * 公共标题中间文字片
     **/
    private TextView centerTitleText;
    /**
     * 公共标题背景界面
     **/
    private View titleBg;
    /**
     * 公共标题下方分割线
     **/
    private View titleLine;

    private Context context;

    public CommonTitle(Context context, ImageButton leftTitleImg, ImageButton rightTitleImg, TextView leftTitleText, TextView rightTitleText, TextView centerTitleText, View titleBg, View titleLine) {
        this.leftTitleImg = leftTitleImg;

        this.rightTitleImg = rightTitleImg;

        this.leftTitleText = leftTitleText;

        this.rightTitleText = rightTitleText;

        this.centerTitleText = centerTitleText;

        this.titleBg = titleBg;

        this.titleLine = titleLine;

        this.context = context;
    }

    public void addCommonTitle(Object leftTxt, Object centerTxt, Object rightTxt, Object leftRes, Object rightRes,
                               Object bgColor, Object lineColor) {

        String tip = "";

        if (leftTxt != null) {
            leftTitleText.setVisibility(View.VISIBLE);
            try {
                setLeftTitleText(leftTxt);
            } catch (Exception e) {
                tip += "leftTxt ";
            }
        } else {
            leftTitleText.setVisibility(View.GONE);
        }

        if (centerTxt != null) {
            centerTitleText.setVisibility(View.VISIBLE);
            try {
                setCenterTitleText(centerTxt);
            } catch (Exception e) {
                tip += "centerTxt ";
            }
        } else {
            centerTitleText.setVisibility(View.GONE);
        }

        if (rightTxt != null) {
            rightTitleText.setVisibility(View.VISIBLE);
            try {
                setRightTitleText(rightTxt);
            } catch (Exception e) {
                tip += "rightTxt ";
            }
        } else {
            rightTitleText.setVisibility(View.GONE);
        }

        if (leftRes != null) {
            leftTitleImg.setVisibility(View.VISIBLE);
            try {
                setLeftTitleImg(leftRes);
            } catch (Exception e) {
                tip += "leftRes ";
            }
        } else {
            leftTitleImg.setVisibility(View.GONE);
        }

        if (rightRes != null) {
            rightTitleImg.setVisibility(View.VISIBLE);
            try {
                setRightTitleImg(rightRes);
            } catch (Exception e) {
                tip += "rightRes ";
            }
        } else {
            rightTitleImg.setVisibility(View.GONE);
        }

        if (bgColor != null) {
            try {
                setTitleBackgroundColor(bgColor);
            } catch (Exception e) {
                tip += "bgColor ";
            }
        } else {
            setTitleBackgroundColor(R.color.commontitle_bg);
        }

        if (lineColor != null) {
            try {
                setTitleLineColor(lineColor);
            } catch (Exception e) {
                tip += "lineColor ";
            }
        }

    }

    /**
     * 设置左标题图片
     *
     * @param resource
     */
    private void setLeftTitleImg(Object resource) {

        if (resource instanceof Integer) {
            leftTitleImg.setImageResource((Integer) resource);
        } else if (resource instanceof Bitmap) {
            leftTitleImg.setImageBitmap((Bitmap) resource);
        } else if (resource instanceof Drawable) {
            leftTitleImg.setImageDrawable((Drawable) resource);
        }
    }

    /**
     * 设置左标题文字
     *
     * @param text
     */
    private void setLeftTitleText(Object text) {

        if (text instanceof String) {
            leftTitleText.setText((String) text);
        } else if (text instanceof Integer) {
            leftTitleText.setText(context.getString((Integer) text));
        }
    }

    /**
     * 设置左标题文字颜色
     *
     * @param color
     */
    public void setLeftTitleTextColor(String color) {
        leftTitleText.setTextColor(Color.parseColor(color));
    }

    /**
     * 设置右标题图片
     *
     * @param resource
     */
    private void setRightTitleImg(Object resource) {

        if (resource instanceof Integer) {
            rightTitleImg.setImageResource((Integer) resource);
        } else if (resource instanceof Bitmap) {
            rightTitleImg.setImageBitmap((Bitmap) resource);
        } else if (resource instanceof Drawable) {
            rightTitleImg.setImageDrawable((Drawable) resource);
        }
    }

    /**
     * 设置右标题文字
     *
     * @param text
     */
    private void setRightTitleText(Object text) {

        if (text instanceof String) {
            rightTitleText.setText((String) text);
        } else if (text instanceof Integer) {
            rightTitleText.setText(context.getString((Integer) text));
        }
    }

    /**
     * 设置右标题文字颜色
     *
     * @param color(#FFFFFFFF)
     */
    public void setRightTitleTextColor(String color) {
        rightTitleText.setTextColor(Color.parseColor(color));
    }

    /**
     * 设置中标题文字
     *
     * @param text
     */
    private void setCenterTitleText(Object text) {

        if (text instanceof String) {
            centerTitleText.setText((String) text);
        } else if (text instanceof Integer) {
            centerTitleText.setText(context.getString((Integer) text));
        }
    }

    /**
     * 设置中标题文字颜色
     *
     * @param color(#FFFFFFFF)
     */
    public void setCenterTitleTextColor(String color) {
        centerTitleText.setTextColor(Color.parseColor(color));
    }

    /**
     * 设置标题背景颜色
     *
     * @param color
     */
    private void setTitleBackgroundColor(Object color) {

        if (color instanceof String) {
            titleBg.setBackgroundColor(Color.parseColor((String) color));
        } else if (color instanceof Integer) {
            titleBg.setBackgroundResource(((Integer) color));
        } else if (color instanceof Drawable) {
            titleBg.setBackground((Drawable) color);
        }
    }

    /**
     * 设置标题分割线颜色
     *
     * @param color
     */
    private void setTitleLineColor(Object color) {

        if (color instanceof String) {
            titleLine.setBackgroundColor(Color.parseColor((String) color));
        } else if (color instanceof Integer) {
            titleLine.setBackgroundResource(((Integer) color));
        } else if (color instanceof Drawable) {
            titleLine.setBackground((Drawable) color);
        }
    }

    /**
     * 设置公共标题左边的监听
     */
    public void setCommonTitleLeftListener(View.OnClickListener onClickListener) {
        leftTitleImg.setOnClickListener(onClickListener);
        leftTitleText.setOnClickListener(onClickListener);
    }

    /**
     * 设置公共标题中间的监听
     */
    public void setCommonTitleCenterListener(View.OnClickListener onClickListener) {
        centerTitleText.setOnClickListener(onClickListener);
    }

    /**
     * 设置公共标题右边的监听
     */
    public void setCommonTitleRightListener(View.OnClickListener onClickListener) {
        rightTitleImg.setOnClickListener(onClickListener);
        rightTitleText.setOnClickListener(onClickListener);
    }
}
