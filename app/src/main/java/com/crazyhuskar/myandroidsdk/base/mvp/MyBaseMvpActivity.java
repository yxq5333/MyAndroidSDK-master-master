package com.crazyhuskar.myandroidsdk.base.mvp;

import android.os.Bundle;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;

import java.util.Objects;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */
public abstract class MyBaseMvpActivity<P extends MyBaseMvpPresenter> extends MyBaseActivity implements MyBaseMvpView {

    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = createPresenter();
        Objects.requireNonNull(presenter, "Presenter未初始化");
        super.onCreate(savedInstanceState);
        presenter.onMvpAttachView(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onMvpStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onMvpResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onMvpPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onMvpStop();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onMvpSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onMvpDetachView(false);
            presenter.onMvpDestroy();
        }
    }

}
