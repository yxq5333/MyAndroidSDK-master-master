package com.crazyhuskar.myandroidsdk.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.util.bean.EventBean;
import com.crazyhuskar.myandroidsdk.view.CommonTitle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */
public abstract class MyBaseFragment extends Fragment {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Unbinder unbinder;

    protected Context mContext;
    /**
     * 当前加载的视图界面
     **/
    protected View mContentView;

    protected CommonTitle commonTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutID(), null);
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (registerFragment() != null) {
            init();
            initEventBus();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        if (registerFragment() != null) {
            EventBus.getDefault().unregister(registerFragment());
        }
        super.onDestroy();
    }

    /**
     * 初始化EventBus
     */
    public void initEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(registerFragment());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(EventBean eventBean) {
    }

    protected abstract int setLayoutID();

    protected abstract Fragment registerFragment();

    protected abstract void init();

    /**
     * 设置标题栏
     *
     * @param centerTxt 文字或id
     */
    public void setCommonTitle(Object centerTxt) {
        setCommonTitle(null, centerTxt, null, R.drawable.arrow_back_24dp, null, null, null);
    }

    /**
     * 设置标题栏
     *
     * @param centerTxt 文字或id
     * @param leftRes   左边按钮图片id
     */
    public void setCommonTitle(Object centerTxt, Object leftRes) {
        setCommonTitle(null, centerTxt, null, leftRes, null, null, null);
    }

    /**
     * 设置标题栏
     *
     * @param leftTxt   文字或id
     * @param centerTxt 文字或id
     * @param rightTxt  文字或id
     * @param leftRes   图片id
     * @param rightRes  图片id
     * @param bgColor   #ffffffff
     * @param lineColor #ffffffff
     */
    public void setCommonTitle(Object leftTxt, Object centerTxt, Object rightTxt, Object leftRes, Object rightRes,
                                Object bgColor, Object lineColor) {
        commonTitle = new CommonTitle(mContext, (ImageButton) mContentView.findViewById(R.id.common_title_left_img),
                (ImageButton) mContentView.findViewById(R.id.common_title_right_img),
                (TextView) mContentView.findViewById(R.id.common_title_left_text),
                (TextView) mContentView.findViewById(R.id.common_title_right_text),
                (TextView) mContentView.findViewById(R.id.common_title_center_text),
                mContentView.findViewById(R.id.view_common_title),
                mContentView.findViewById(R.id.view_common_title_line));
        commonTitle.addCommonTitle(leftTxt, centerTxt, rightTxt, leftRes, rightRes, bgColor, lineColor);
    }
}

