package com.claire.traveldiary.mainpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.claire.traveldiary.MainActivity;
import com.claire.traveldiary.R;
import com.claire.traveldiary.component.SpacesItemDecoration;
import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.room.DiaryDAO;
import com.claire.traveldiary.data.room.DiaryDatabase;
import com.claire.traveldiary.edit.EditFragment;
import com.claire.traveldiary.edit.EditPresenter;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainPageFragment extends Fragment implements MainPageContract.View {

    private static final String TAG = "MainPageFragment";

    private MainPageContract.Presenter mPresenter;
    private MainPageAdapter mMainPageAdapter;

    private EditPresenter mEditPresenter;

    private ArrayList<Diary> mDiaries;

    private DiaryDatabase mDatabase;

    private ImageButton mAddDiary;


    public MainPageFragment() {
    }

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(MainPageContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPageAdapter = new MainPageAdapter(mPresenter,getContext());

        mDatabase = DiaryDatabase.getIstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);

        mAddDiary = root.findViewById(R.id.btn_add_diary);

        if (mDatabase.getDiaryDAO().getDiarys().size() > 0) {
            mAddDiary.setVisibility(View.GONE);
        } else {
            mAddDiary.setVisibility(View.VISIBLE);
            mAddDiary.setOnClickListener(v -> {
                ((MainActivity) getActivity()).openEdit();
            });
        }

        RecyclerView recyclerView = root.findViewById(R.id.recycler_main_page);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mMainPageAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2,40,true));

        return root;
    }

    @Override
    public void openEditPage(Diary diary) {
        ((MainActivity) getActivity()).openEditFromMainPage(diary);
    }

    @Override
    public void deleteDiaryUi(int id) {
        DiaryDAO diaryDAO = mDatabase.getDiaryDAO();
        diaryDAO.deleteDiarybyId(id);
        mMainPageAdapter.deleteDiary(id);
    }
}
