package com.crazyhuskar.myandroidsdk.adapter.recyclerview.callback;

import android.view.View;

/**
 * 顶部标签点击监听
 *
 * @author HG
 */
public interface OnHeaderClickListener {

    void onHeaderClick(View view, int id, int position);

    void onHeaderLongClick(View view, int id, int position);

    void onHeaderDoubleClick(View view, int id, int position);

}
