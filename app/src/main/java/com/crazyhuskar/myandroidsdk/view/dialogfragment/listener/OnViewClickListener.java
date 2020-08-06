package com.crazyhuskar.myandroidsdk.view.dialogfragment.listener;

import android.view.View;

import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.BindViewHolder;


public interface OnViewClickListener {
    void onViewClick(BindViewHolder viewHolder, View view, MyDialogFragment myDialogFragment);
}
