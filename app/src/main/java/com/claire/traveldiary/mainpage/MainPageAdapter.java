package com.claire.traveldiary.mainpage;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.claire.traveldiary.R;
import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.room.DiaryDAO;
import com.claire.traveldiary.data.room.DiaryDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MainPageAdapter";

    private MainPageContract.Presenter mPresenter;

    private Context mContext;

    private DiaryDatabase mDatabase;

    private List<Diary> mDiaryList;


    public MainPageAdapter(MainPageContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    private class MainPageViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout mLayout;
        private CardView mCard;
        private ImageView mMainImage;
        private TextView mDiaryTitle;
        private TextView mDiaryDate;

        private ImageButton mDelete;
        private ImageButton mShare;

        public MainPageViewHolder(@NonNull View itemView) {
            super(itemView);

            mLayout = itemView.findViewById(R.id.card_space);
            mCard = itemView.findViewById(R.id.card_view);
            mMainImage = itemView.findViewById(R.id.main_img_card);
            mMainImage.setAlpha(0.9f);
            mDiaryTitle = itemView.findViewById(R.id.edit_diary_title);
            mDiaryDate = itemView.findViewById(R.id.tv_diary_date);
            mDelete = itemView.findViewById(R.id.btn_delete_diary);
            mShare = itemView.findViewById(R.id.btn_share_diary);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainPageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MainPageViewHolder) {

            mDatabase = DiaryDatabase.getIstance(mContext);

            mDiaryList = mDatabase.getDiaryDAO().getDiarys();

            //click space return original card view
            ((MainPageViewHolder) holder).mLayout.setOnClickListener(v -> {
                ((MainPageViewHolder) holder).mMainImage.setAlpha(0.9f);
                ((MainPageViewHolder) holder).mDelete.setVisibility(View.GONE);
                ((MainPageViewHolder) holder).mShare.setVisibility(View.GONE);
                ((MainPageViewHolder) holder).mDiaryTitle.setVisibility(View.VISIBLE);
                ((MainPageViewHolder) holder).mDiaryDate.setVisibility(View.VISIBLE);
            });

            //click card
            ((MainPageViewHolder) holder).mCard.setOnClickListener(v -> {
                mPresenter.openEdit(mDatabase.getDiaryDAO().getDiarys().get(position));
            });

            //show card
            if (mDiaryList.size() > 0) {
                if (mDiaryList.get(position).getImages() != null) {
                    ((MainPageViewHolder) holder).mMainImage.setImageBitmap(BitmapFactory.decodeFile(mDiaryList.get(position).getImages().get(0)));
                }

                if (mDiaryList.get(position).getTitle() != null) {
                    ((MainPageViewHolder) holder).mDiaryTitle.setText(mDiaryList.get(position).getTitle());
                } else {
                    ((MainPageViewHolder) holder).mDiaryTitle.setText("Travel");
                }

                if (mDiaryList.get(position).getDate() != null) {
                    ((MainPageViewHolder) holder).mDiaryDate.setText(mDiaryList.get(position).getDate());
                } else {
                    ((MainPageViewHolder) holder).mDiaryDate.setText("11 January 2019");
                }
            }


            //long click delete or share
            ((MainPageViewHolder) holder).mCard.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((MainPageViewHolder) holder).mMainImage.setAlpha(0.4f);

                    ((MainPageViewHolder) holder).mDelete.setVisibility(View.VISIBLE);
                    ((MainPageViewHolder) holder).mDelete.setOnClickListener(v1 -> {
                        mPresenter.deleteDiary(mDiaryList.get(position).getId());
                        mDiaryList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Delete Diary", Toast.LENGTH_SHORT).show();
                        ((MainPageViewHolder) holder).mMainImage.setAlpha(0.9f);
                        ((MainPageViewHolder) holder).mDelete.setVisibility(View.GONE);
                        ((MainPageViewHolder) holder).mShare.setVisibility(View.GONE);
                        ((MainPageViewHolder) holder).mDiaryTitle.setVisibility(View.VISIBLE);
                        ((MainPageViewHolder) holder).mDiaryDate.setVisibility(View.VISIBLE);

                    });

                    ((MainPageViewHolder) holder).mShare.setVisibility(View.VISIBLE);
                    ((MainPageViewHolder) holder).mShare.setOnClickListener(v12 -> {
                        Log.d(TAG, "Oh Share.....");
                        ((MainPageViewHolder) holder).mMainImage.setAlpha(0.9f);
                        ((MainPageViewHolder) holder).mShare.setVisibility(View.GONE);
                        ((MainPageViewHolder) holder).mDelete.setVisibility(View.GONE);
                        ((MainPageViewHolder) holder).mDiaryTitle.setVisibility(View.VISIBLE);
                        ((MainPageViewHolder) holder).mDiaryDate.setVisibility(View.VISIBLE);
                    });

                    ((MainPageViewHolder) holder).mDiaryTitle.setVisibility(View.GONE);
                    ((MainPageViewHolder) holder).mDiaryDate.setVisibility(View.GONE);
                    return true;
                }
            });
        }
    }

    public void updateData(ArrayList<Diary> diaries) {
        mDiaryList = diaries;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mDiaryList == null) {
            return 1;
        }
        return mDiaryList.size();
    }
}
