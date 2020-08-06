package com.crazyhuskar.myandroidsdk.util;

import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilSnackbar {
    /**
     * 显示短底部提示
     *
     * @param tip
     */
    public static void showShortSnackbar(View view, String tip) {
        ViewGroup contentView = (ViewGroup) view;
        View includeView = view;
        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }
        Snackbar.make(includeView, tip, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示短底部提示并附带可点击文字
     *
     * @param tip
     * @param clickText
     */
    public static void showShortSnackbar(View view, String tip, String clickText, View.OnClickListener onClickListener) {
        ViewGroup contentView = (ViewGroup) view;
        View includeView = view;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }
        Snackbar.make(includeView, tip, Snackbar.LENGTH_SHORT).setAction(clickText, onClickListener).show();
    }

    /**
     * 显示长底部提示
     *
     * @param tip
     */
    public static void showLongSnackbar(View view, String tip) {
        ViewGroup contentView = (ViewGroup) view;
        View includeView = view;
        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }
        Snackbar.make(includeView, tip, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示长底部提示并附带可点击文字
     *
     * @param tip
     * @param clickText
     * @param onClickListener
     */
    public static void showLongSnackbar(View view, String tip, String clickText, View.OnClickListener onClickListener) {
        ViewGroup contentView = (ViewGroup) view;
        View includeView = view;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }
        Snackbar.make(includeView, tip, Snackbar.LENGTH_LONG).setAction(clickText, onClickListener).show();
    }
}
