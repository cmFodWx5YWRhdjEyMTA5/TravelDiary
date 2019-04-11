package com.claire.traveldiary.mainpage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.claire.traveldiary.R;
import com.claire.traveldiary.data.Diary;

import java.util.ArrayList;

public class MainPageAdapter extends RecyclerView.Adapter {

    private MainPageContract.Presenter mPresenter;

    private ArrayList<Diary> mDiaryList;

    public MainPageAdapter(MainPageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private class MainPageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mMainImage;
        private TextView mDiaryTitle;
        private TextView mDiaryDate;

        public MainPageViewHolder(@NonNull View itemView) {
            super(itemView);

            mMainImage = itemView.findViewById(R.id.main_img_card);
            mMainImage.setAlpha(0.8f);
            mDiaryTitle = itemView.findViewById(R.id.edit_diary_title);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainPageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
