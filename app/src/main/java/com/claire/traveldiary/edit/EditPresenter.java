package com.claire.traveldiary.edit;

import android.annotation.SuppressLint;

import static android.support.v4.util.Preconditions.checkNotNull;

public class EditPresenter implements EditContract.Presenter {

    private EditContract.View mView;

    @SuppressLint("RestrictedApi")
    public EditPresenter(EditContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}