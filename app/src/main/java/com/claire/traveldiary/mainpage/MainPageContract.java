package com.claire.traveldiary.mainpage;

import com.claire.traveldiary.base.BasePresenter;
import com.claire.traveldiary.base.BaseView;

public interface MainPageContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

    }
}
