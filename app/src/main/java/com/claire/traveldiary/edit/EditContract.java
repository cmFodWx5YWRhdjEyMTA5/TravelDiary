package com.claire.traveldiary.edit;

import com.claire.traveldiary.base.BasePresenter;
import com.claire.traveldiary.base.BaseView;
import com.claire.traveldiary.data.Diary;

public interface EditContract {

    interface View extends BaseView<Presenter> {

        void openWeatherDialogUi();

        void openGalleryUi();

        void openDatePickerUi();

        void clickSaveDiaryUi();

        void openEditPageUi(Diary diary);

        void clickEditDiaryUi();


    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void openWeatherDialog();

        void openGallery();

        void openDatePicker();

        void clickSaveDiary();

        void loadDiaryData(Diary diary);

        void clickEditDiary();



    }
}
