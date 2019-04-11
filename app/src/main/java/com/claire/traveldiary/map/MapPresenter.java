package com.claire.traveldiary.map;

import android.annotation.SuppressLint;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;

    @SuppressLint("RestrictedApi")
    public MapPresenter(MapContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }
}
