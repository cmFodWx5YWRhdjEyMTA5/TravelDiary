package com.claire.traveldiary.edit.chooseweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.claire.traveldiary.R;
import com.claire.traveldiary.edit.EditFragment;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

public class WeatherDialog extends DialogFragment implements WeatherContract.View, View.OnClickListener {

    public static final String IMAGE = "weathericon";

    private WeatherContract.Presenter mPresenter;

    private ConstraintLayout mLayout;
    private ImageButton mSunny;
    private ImageButton mSunCloud;
    private ImageButton mCloudy;
    private ImageButton mWindy;
    private ImageButton mRainy;
    private ImageButton mSnow;

    public WeatherDialog() {}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        setStyle(STYLE_NO_FRAME, R.style.WeatherDialog);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.dialog_choose_weather, container, false);

        mLayout = dialogView.findViewById(R.id.layout_choose_weather);
        mLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_up));

        mSunny = dialogView.findViewById(R.id.img_sunny);
        mSunny.setOnClickListener(this);
        mSunCloud = dialogView.findViewById(R.id.img_cloud_sun);
        mSunCloud.setOnClickListener(this);
        mCloudy = dialogView.findViewById(R.id.img_cloudy);
        mCloudy.setOnClickListener(this);
        mWindy = dialogView.findViewById(R.id.img_windy);
        mWindy.setOnClickListener(this);
        mRainy = dialogView.findViewById(R.id.img_rain);
        mRainy.setOnClickListener(this);
        mSnow = dialogView.findViewById(R.id.img_snow);
        mSnow.setOnClickListener(this);


        return dialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void dismiss() {

        mLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_down));

        new Handler().postDelayed(super::dismiss, 200);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_sunny:
                setResult("ic_sunny");
                dismiss();
                break;

            case R.id.img_cloud_sun:
                setResult("ic_cloud_sun");
                dismiss();
                break;

            case R.id.img_cloudy:
                setResult("ic_cloudy");
                dismiss();
                break;

            case R.id.img_windy:
                setResult("ic_windy");
                dismiss();
                break;

            case R.id.img_rain:
                setResult("ic_raining");
                dismiss();
                break;

            case R.id.img_snow:
                setResult("ic_snowflake");
                dismiss();
                break;
        }
    }

    protected void setResult(String id) {

        if (getTargetFragment() == null) {
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(IMAGE, id);
            getTargetFragment().onActivityResult(EditFragment.REQUEST, Activity.RESULT_OK, intent);
        }
    }
}
