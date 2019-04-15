package com.claire.traveldiary.edit;

import android.annotation.SuppressLint;
import android.util.Log;


import com.claire.traveldiary.data.Diary;

import static android.support.v4.util.Preconditions.checkNotNull;

public class EditPresenter implements EditContract.Presenter {

    private static final String TAG = "EditPresenter";

    private EditContract.View mView;



    @SuppressLint("RestrictedApi")
    public EditPresenter(EditContract.View view) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void openWeatherDialog() {
        mView.openWeatherDialogUi();
    }

    @Override
    public void openGallery() {
        mView.openGalleryUi();
    }

    @Override
    public void openDatePicker() {
        mView.openDatePickerUi();
    }


    @Override
    public void clickSaveDiary() {
        Log.d(TAG,"Successful save diary to room!");
        mView.clickSaveDiaryUi();
    }

    @Override
    public void loadDiaryData(Diary diary) {
        mView.openEditPageUi(diary);
    }

    @Override
    public void clickEditDiary() {
        mView.clickEditDiaryUi();
    }


}
