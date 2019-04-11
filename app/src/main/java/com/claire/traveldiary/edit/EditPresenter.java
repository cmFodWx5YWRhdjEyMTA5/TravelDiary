package com.claire.traveldiary.edit;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;

import com.claire.traveldiary.TravelDiaryApplication;
import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.room.DiaryDAO;
import com.claire.traveldiary.data.room.DiaryDatabase;

import static android.support.v4.util.Preconditions.checkNotNull;

public class EditPresenter implements EditContract.Presenter {

    private static final String TAG = "EditPresenter";

    private EditContract.View mView;

    private DiaryDatabase mDatabase;
    private Diary mDiary;

    private Context mContext;



    @SuppressLint("RestrictedApi")
    public EditPresenter(EditContract.View view, Context context) {
        mView = checkNotNull(view, "view cannot be null!");
        mView.setPresenter(this);
        mContext = context;
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
    public void editDiary() {
        Log.d(TAG,"You can edit diary now!");
    }

    @Override
    public void unEditDiary() {
        Log.d(TAG,"You can't edit diary now!");
        mView.unEditDiaryUi();
    }

    @Override
    public void clickSaveDiary() {
        Log.d(TAG,"Successful save diary to room!");
        mView.clickSaveDiaryUi();
    }

    @Override
    public void saveDiaryToRoom(Diary diary) {
        mDiary = diary;

        mDatabase = Room
                .databaseBuilder(mContext.getApplicationContext(), DiaryDatabase.class, "Diary.db")
                .allowMainThreadQueries()
                .build();
        DiaryDAO diaryDAO = mDatabase.getDiaryDAO();
        diaryDAO.insertOrUpdate(mDiary);

        Log.d(TAG,"Diary" + diaryDAO.getDiarys().size());
        Log.d(TAG,"Diary" + diaryDAO.getDiarys().get(0).getTitle()+
                diaryDAO.getDiarys().get(0).getDate()+
                diaryDAO.getDiarys().get(0).getDiaryPlace().getLatLng()+
                diaryDAO.getDiarys().get(0).getTags()+
                diaryDAO.getDiarys().get(0).getImages());
    }

}
