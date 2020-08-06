package com.crazyhuskar.myandroidsdk.view.dialogfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.DialogFragmentController;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.ListDialogAdapter;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnBindViewListener;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnViewClickListener;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author CrazyHuskar
 * @date 2018/11/20
 */
public class MyListDialogFragment extends MyDialogFragment {


    @Override
    protected void bindView(View view) {
        super.bindView(view);
        if (dialogFragmentController.getAdapter() != null) {//有设置列表
            //列表
            RecyclerView recyclerView = view.findViewById(R.id.dialogfragmentrecyclerview);
            if (recyclerView == null) {
                throw new IllegalArgumentException("自定义列表xml布局,请设置RecyclerView的控件id为recycler_view");
            }
            dialogFragmentController.getAdapter().setTDialog(this);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), dialogFragmentController.getOrientation(), false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(dialogFragmentController.getAdapter());
            dialogFragmentController.getAdapter().notifyDataSetChanged();
            if (dialogFragmentController.getAdapterItemClickListener() != null) {
                dialogFragmentController.getAdapter().setOnAdapterItemClickListener(dialogFragmentController.getAdapterItemClickListener());
            }
        } else {
            MyUtilLog.e("列表弹窗需要先调用setAdapter()方法!");
        }
    }

    /*********************************************************************
     * 使用Builder模式实现
     *
     */
    public static class Builder {

        DialogFragmentController.DialogFragmentParams params;

        public Builder(FragmentManager fragmentManager) {
            params = new DialogFragmentController.DialogFragmentParams();
            params.mFragmentManager = fragmentManager;
        }

        //各种setXXX()方法设置数据
        public MyListDialogFragment.Builder setLayoutRes(@LayoutRes int layoutRes) {
            params.mLayoutRes = layoutRes;
            return this;
        }

        //设置自定义列表布局和方向
        public MyListDialogFragment.Builder setListLayoutRes(@LayoutRes int layoutRes, int orientation) {
            params.listLayoutRes = layoutRes;
            params.orientation = orientation;
            return this;
        }

        /**
         * 设置弹窗宽度是屏幕宽度的比例 0 -1
         */
        public MyListDialogFragment.Builder setScreenWidthAspect(Activity activity, float widthAspect) {
            params.mWidth = (int) (getScreenWidth(activity) * widthAspect);
            return this;
        }

        public MyListDialogFragment.Builder setWidth(int widthPx) {
            params.mWidth = widthPx;
            return this;
        }

        /**
         * 设置屏幕高度比例 0 -1
         */
        public MyListDialogFragment.Builder setScreenHeightAspect(Activity activity, float heightAspect) {
            params.mHeight = (int) (getScreenHeight(activity) * heightAspect);
            return this;
        }

        public MyListDialogFragment.Builder setHeight(int heightPx) {
            params.mHeight = heightPx;
            return this;
        }

        public MyListDialogFragment.Builder setGravity(int gravity) {
            params.mGravity = gravity;
            return this;
        }

        public MyListDialogFragment.Builder setCancelOutside(boolean cancel) {
            params.mIsCancelableOutside = cancel;
            return this;
        }

        public MyListDialogFragment.Builder setDimAmount(float dim) {
            params.mDimAmount = dim;
            return this;
        }

        public MyListDialogFragment.Builder setTag(String tag) {
            params.mTag = tag;
            return this;
        }

        public MyListDialogFragment.Builder setOnBindViewListener(OnBindViewListener listener) {
            params.bindViewListener = listener;
            return this;
        }

        public MyListDialogFragment.Builder addOnClickListener(int... ids) {
            params.ids = ids;
            return this;
        }

        public MyListDialogFragment.Builder setOnViewClickListener(OnViewClickListener listener) {
            params.mOnViewClickListener = listener;
            return this;
        }

        //列表数据,需要传入数据和Adapter,和item点击数据
        public <A extends ListDialogAdapter> MyListDialogFragment.Builder setAdapter(A adapter) {
            params.adapter = adapter;
            return this;
        }

        public MyListDialogFragment.Builder setOnAdapterItemClickListener(ListDialogAdapter.OnAdapterItemClickListener listener) {
            params.adapterItemClickListener = listener;
            return this;
        }

        public MyListDialogFragment.Builder setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
            params.mOnDismissListener = dismissListener;
            return this;
        }

        public MyListDialogFragment create() {
            MyListDialogFragment dialog = new MyListDialogFragment();
            //将数据从Buidler的DjParams中传递到DjDialog中
            params.apply(dialog.dialogFragmentController);
            return dialog;
        }
    }
}
