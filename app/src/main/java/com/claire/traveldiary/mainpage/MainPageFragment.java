package com.claire.traveldiary.mainpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claire.traveldiary.R;
import com.claire.traveldiary.component.GridSpacingItemDecoration;
import com.claire.traveldiary.component.SpacesItemDecoration;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainPageFragment extends Fragment implements MainPageContract.View {

    private MainPageContract.Presenter mPresenter;
    private MainPageAdapter mMainPageAdapter;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPageAdapter = new MainPageAdapter(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_main_page);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mMainPageAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2,40,true));

        return root;
    }
}
