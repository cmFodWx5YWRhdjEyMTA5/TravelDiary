package com.claire.traveldiary.edit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claire.traveldiary.R;
import com.claire.traveldiary.edit.chooseweather.WeatherContract;
import com.claire.traveldiary.edit.chooseweather.WeatherDialog;
import com.claire.traveldiary.edit.chooseweather.WeatherPresenter;

import static android.support.v4.util.Preconditions.checkNotNull;

public class EditFragment extends Fragment implements EditContract.View{

    public static final int REQUEST = 1;

    private EditContract.Presenter mPresenter;
    private WeatherContract.Presenter mWeatherPresenter;
    private EditAdapter mEditAdapter;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditAdapter = new EditAdapter(mPresenter,getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_edit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mEditAdapter);

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST) {
            String imageName = data.getStringExtra(WeatherDialog.IMAGE);
            Log.d("EditFragment", imageName);
            mEditAdapter.setWeatherIcon(imageName);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.autocomplete_fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openWeatherDialogUi() {

        WeatherDialog dialog =
                (WeatherDialog) (getActivity().getSupportFragmentManager().findFragmentByTag("WeatherDialog"));

        if (dialog == null) {

            dialog = new WeatherDialog();
            mWeatherPresenter = new WeatherPresenter(dialog);

            dialog.setPresenter(mWeatherPresenter);
            dialog.setTargetFragment(EditFragment.this, REQUEST);
            dialog.show((getActivity().getSupportFragmentManager()),"WeatherDialog");

        } else if (!dialog.isAdded()) {

            dialog.show(getActivity().getSupportFragmentManager(), "WeatherDialog");
        }
    }


}
