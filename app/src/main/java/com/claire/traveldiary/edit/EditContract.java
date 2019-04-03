package com.claire.traveldiary.edit;

import com.claire.traveldiary.base.BasePresenter;
import com.claire.traveldiary.base.BaseView;

public interface EditContract {

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void editDiary();

        void saveDiary();

        void finishEdit();

    }
}
