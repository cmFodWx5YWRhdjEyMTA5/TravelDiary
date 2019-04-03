package com.claire.traveldiary.edit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claire.traveldiary.R;

import static android.support.v4.util.Preconditions.checkNotNull;

public class EditFragment extends Fragment implements EditContract.View{

    private EditContract.Presenter mPresenter;

    public EditFragment() {
    }

    public static EditFragment newInstance() {
        return new EditFragment();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        return root;
    }


}
