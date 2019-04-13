package com.claire.traveldiary.edit.chooseweather;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
                Uri imageUri_sun = Uri.parse("android.resource://com.claire.traveldiary/" + R.mipmap.ic_sunny);
                setResult(imageUri_sun.toString());
                dismiss();
                break;

            case R.id.img_cloud_sun:
                Uri imageUri_cloud_sun = Uri.parse("android.resource://com.claire.traveldiary/" + R.mipmap.ic_cloud_sun);
                setResult(imageUri_cloud_sun.toString());
                dismiss();
                break;

            case R.id.img_cloudy:
                Uri imageUri_cloudy = Uri.parse("android.resource://com.claire.traveldiary/" + R.mipmap.ic_cloudy);
                setResult(imageUri_cloudy.toString());
                dismiss();
                break;

            case R.id.img_windy:
                Uri imageUri_windy = Uri.parse("android.resource://com.claire.traveldiary/" + R.mipmap.ic_windy);
                setResult(imageUri_windy.toString());
                dismiss();
                break;

            case R.id.img_rain:
                Uri imageUri_rainy = Uri.parse("android.resource://com.claire.traveldiary/" + R.mipmap.ic_raining);
                setResult(imageUri_rainy.toString());
                dismiss();
                break;

            case R.id.img_snow:
                Uri imageUri_snow = Uri.parse("android.resource://com.claire.traveldiary/" + R.mipmap.ic_snowflake);
                setResult(imageUri_snow.toString());
                dismiss();
                break;
        }
    }

    protected void setResult(String imageUri) {

        if (getTargetFragment() == null) {
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(IMAGE, imageUri);
            getTargetFragment().onActivityResult(EditFragment.REQUEST, Activity.RESULT_OK, intent);
        }
    }
}
