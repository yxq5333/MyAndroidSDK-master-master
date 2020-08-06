package com.crazyhuskar.myandroidsdk.base.mvp;

import android.os.Bundle;

import com.crazyhuskar.myandroidsdk.base.MyBaseFragment;

import java.util.Objects;

import androidx.annotation.Nullable;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */
public abstract class MyBaseMvpFragment<P extends MyBaseMvpPresenter> extends MyBaseFragment implements MyBaseMvpView {

    protected P presenter;

    protected abstract P createPresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        Objects.requireNonNull(presenter, "Presenter未初始化");
        super.onActivityCreated(savedInstanceState);
        presenter.onMvpAttachView(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onMvpStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onMvpResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onMvpPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onMvpStop();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onMvpSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onMvpDetachView(false);
            presenter.onMvpDestroy();
        }
    }

}
