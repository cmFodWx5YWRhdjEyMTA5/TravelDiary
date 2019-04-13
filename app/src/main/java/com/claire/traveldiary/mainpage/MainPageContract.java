package com.claire.traveldiary.mainpage;

import com.claire.traveldiary.base.BasePresenter;
import com.claire.traveldiary.base.BaseView;
import com.claire.traveldiary.data.Diary;

public interface MainPageContract {

    interface View extends BaseView<Presenter> {

        void openEditPage(Diary diary);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void openEdit(Diary diary);

    }
}
