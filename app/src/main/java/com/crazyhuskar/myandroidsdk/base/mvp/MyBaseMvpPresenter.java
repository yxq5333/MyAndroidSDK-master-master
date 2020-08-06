package com.crazyhuskar.myandroidsdk.base.mvp;

import android.os.Bundle;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */
public interface MyBaseMvpPresenter<V extends MyBaseMvpView> {

    void onMvpAttachView(V view, Bundle savedInstanceState);

    void onMvpStart();

    void onMvpResume();

    void onMvpPause();

    void onMvpStop();

    void onMvpSaveInstanceState(Bundle savedInstanceState);

    void onMvpDetachView(boolean retainInstance);

    void onMvpDestroy();
}