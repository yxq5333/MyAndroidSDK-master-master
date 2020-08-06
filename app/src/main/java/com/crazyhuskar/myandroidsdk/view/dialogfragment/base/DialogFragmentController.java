package com.crazyhuskar.myandroidsdk.view.dialogfragment.base;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnBindViewListener;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnViewClickListener;

import java.io.Serializable;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author CrazyHuskar
 * @date 2018/11/20
 */
public class DialogFragmentController<A extends ListDialogAdapter> implements Parcelable, Serializable {

    private FragmentManager fragmentManager;
    private int layoutRes;
    private int height;
    private int width;
    private float dimAmount;
    private int gravity;
    private String tag;
    private int[] ids;
    private boolean isCancelableOutside;
    private OnViewClickListener onViewClickListener;
    private OnBindViewListener onBindViewListener;
    private A adapter;
    private ListDialogAdapter.OnAdapterItemClickListener adapterItemClickListener;
    private int orientation;
    private int dialogAnimationRes;
    private View dialogView;
    private DialogInterface.OnDismissListener onDismissListener;
    private DialogInterface.OnKeyListener onKeyListener;


    public DialogFragmentController() {
    }

    protected DialogFragmentController(Parcel in) {
        layoutRes = in.readInt();
        height = in.readInt();
        width = in.readInt();
        dimAmount = in.readFloat();
        gravity = in.readInt();
        tag = in.readString();
        ids = in.createIntArray();
        isCancelableOutside = in.readByte() != 0;
        orientation = in.readInt();
    }

    public static final Creator<DialogFragmentController> CREATOR = new Creator<DialogFragmentController>() {
        @Override
        public DialogFragmentController createFromParcel(Parcel in) {
            return new DialogFragmentController(in);
        }

        @Override
        public DialogFragmentController[] newArray(int size) {
            return new DialogFragmentController[size];
        }
    };

    //内容描述接口,不用管
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(layoutRes);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeFloat(dimAmount);
        dest.writeInt(gravity);
        dest.writeString(tag);
        dest.writeIntArray(ids);
        dest.writeByte((byte) (isCancelableOutside ? 1 : 0));
        dest.writeInt(orientation);
    }

    //get
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int mWidth) {
        this.width = mWidth;
    }

    public float getDimAmount() {
        return dimAmount;
    }

    public int getGravity() {
        return gravity;
    }

    public String getTag() {
        return tag;
    }

    public int[] getIds() {
        return ids;
    }

    public boolean isCancelableOutside() {
        return isCancelableOutside;
    }

    public OnViewClickListener getOnViewClickListener() {
        return onViewClickListener;
    }

    public int getOrientation() {
        return orientation;
    }

    public OnBindViewListener getOnBindViewListener() {
        return onBindViewListener;
    }

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public DialogInterface.OnKeyListener getOnKeyListener() {
        return onKeyListener;
    }

    public View getDialogView() {
        return dialogView;
    }

    //列表
    public A getAdapter() {
        return adapter;
    }

    public void setAdapter(A adapter) {
        this.adapter = adapter;
    }

    public ListDialogAdapter.OnAdapterItemClickListener getAdapterItemClickListener() {
        return adapterItemClickListener;
    }

    public void setAdapterItemClickListener(ListDialogAdapter.OnAdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public int getDialogAnimationRes() {
        return dialogAnimationRes;
    }

    /**************************************************************************
     */
    public static class DialogFragmentParams<A extends ListDialogAdapter> {
        public FragmentManager mFragmentManager;
        public int mLayoutRes;
        public int mWidth;
        public int mHeight;
        public float mDimAmount = 0.2f;
        public int mGravity = Gravity.CENTER;
        public String mTag = "MyDialogFragment";
        public int[] ids;
        public boolean mIsCancelableOutside = true;
        public OnViewClickListener mOnViewClickListener;
        public OnBindViewListener bindViewListener;
        public int mDialogAnimationRes = 0;//弹窗动画
        //列表
        public A adapter;
        public ListDialogAdapter.OnAdapterItemClickListener adapterItemClickListener;
        public int listLayoutRes;
        public int orientation = LinearLayoutManager.VERTICAL;//默认RecyclerView的列表方向为垂直方向
        public View mDialogView;//直接使用传入进来的View,而不需要通过解析Xml
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mKeyListener;

        public void apply(DialogFragmentController dialogFragmentController) {
            dialogFragmentController.fragmentManager = mFragmentManager;
            if (mLayoutRes > 0) {
                dialogFragmentController.layoutRes = mLayoutRes;
            }
            if (mDialogView != null) {
                dialogFragmentController.dialogView = mDialogView;
            }
            if (mWidth > 0) {
                dialogFragmentController.width = mWidth;
            }
            if (mHeight > 0) {
                dialogFragmentController.height = mHeight;
            }
            dialogFragmentController.dimAmount = mDimAmount;
            dialogFragmentController.gravity = mGravity;
            dialogFragmentController.tag = mTag;
            if (ids != null) {
                dialogFragmentController.ids = ids;
            }
            dialogFragmentController.isCancelableOutside = mIsCancelableOutside;
            dialogFragmentController.onViewClickListener = mOnViewClickListener;
            dialogFragmentController.onBindViewListener = bindViewListener;
            dialogFragmentController.onDismissListener = mOnDismissListener;
            dialogFragmentController.dialogAnimationRes = mDialogAnimationRes;
            dialogFragmentController.onKeyListener = mKeyListener;

            if (adapter != null) {
                dialogFragmentController.setAdapter(adapter);
                if (listLayoutRes <= 0) {//使用默认的布局
                    dialogFragmentController.setLayoutRes(R.layout.dialogfragment_recyclerview);
                } else {
                    dialogFragmentController.setLayoutRes(listLayoutRes);
                }
                dialogFragmentController.orientation = orientation;
            } else {
                if (dialogFragmentController.getLayoutRes() <= 0 && dialogFragmentController.getDialogView() == null) {
                    throw new IllegalArgumentException("请先调用setLayoutRes()方法设置弹窗所需的xml布局!");
                }
            }
            if (adapterItemClickListener != null) {
                dialogFragmentController.setAdapterItemClickListener(adapterItemClickListener);
            }

            //如果宽高都没有设置,则默认给弹窗提供宽度为600
            if (dialogFragmentController.width <= 0 && dialogFragmentController.height <= 0) {
                dialogFragmentController.width = 600;
            }
        }
    }
}
