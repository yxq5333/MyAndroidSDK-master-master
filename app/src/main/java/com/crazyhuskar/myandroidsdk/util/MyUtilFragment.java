package com.crazyhuskar.myandroidsdk.util;


import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyUtilFragment {
    private List<Fragment> meunList;
    private FragmentManager manager;
    private Fragment mCurrentFrgment;
    private static MyUtilFragment instance;
    private int viewID;

    public MyUtilFragment(FragmentManager manager, int viewID, List<Fragment> meunList) {
        this.manager = manager;
        this.viewID = viewID;
        this.meunList = meunList;
    }

    public void show(int i) {

        FragmentTransaction ft = manager.beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) {
            ft.hide(mCurrentFrgment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = manager.findFragmentByTag(meunList.get(i).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = meunList.get(i);
        }
        mCurrentFrgment = fragment;
        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(viewID, fragment, fragment.getClass().getName());
            ft.show(fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }
}
