package com.crazyhuskar.myandroidsdk.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ItemViewDelegate;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @param <T>
 * @author HG
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T item, int position) {
                CommonAdapter.this.convert(holder, item, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T item, int position);

}
