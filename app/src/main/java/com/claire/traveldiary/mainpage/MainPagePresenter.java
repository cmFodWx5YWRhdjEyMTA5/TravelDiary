package com.claire.traveldiary.mainpage;

import android.annotation.SuppressLint;

import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.room.DiaryDAO;
import com.claire.traveldiary.data.room.DiaryDatabase;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainPagePresenter implements MainPageContract.Presenter {

    private MainPageContract.View mView;

    private DiaryDatabase mDatabase;

    @SuppressLint("RestrictedApi")
    public MainPagePresenter(MainPageContract.View view) {
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
    public void openEdit(Diary diary) {
        mView.openEditPage(diary);
    }

    @Override
    public void deleteDiary(int id) {
        mView.deleteDiaryUi(id);
    }
}
