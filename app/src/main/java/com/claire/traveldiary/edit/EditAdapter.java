package com.claire.traveldiary.edit;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.claire.traveldiary.R;
import com.claire.traveldiary.TravelDiaryApplication;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;


public class EditAdapter extends RecyclerView.Adapter {


    private EditContract.Presenter mPresenter;
    private GalleryAdapter mGalleryAdapter;

    private Context mContext;

    //Weather
    private String mImageName;
    private ImageButton mWeather;



    public EditAdapter(EditContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }


    public class EditViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerGallery;

        private EditText mTitle;
        private EditText mDate;
        private EditText mContent;
        private mabbas007.tagsedittext.TagsEditText mTags;

        public EditViewHolder(@NonNull View itemView) {
            super(itemView);

            mRecyclerGallery = itemView.findViewById(R.id.recycler_gallery);

            mTitle = itemView.findViewById(R.id.edit_diary_title);
            mDate = itemView.findViewById(R.id.edit_diary_date);
            mContent = itemView.findViewById(R.id.edit_diary_content);
            mWeather = itemView.findViewById(R.id.choose_weather);
            mTags = itemView.findViewById(R.id.edit_tags);

        }

        public RecyclerView getRecyclerGallery() {
            return mRecyclerGallery;
        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EditViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edit, parent, false));

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof EditViewHolder) {

            //Gallery
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    TravelDiaryApplication.getAppContext(), LinearLayoutManager.HORIZONTAL, false);
            if(((EditViewHolder) holder).getRecyclerGallery().getOnFlingListener() == null) {
                new LinearSnapHelper().attachToRecyclerView(((EditViewHolder) holder).getRecyclerGallery());
            }
            ((EditViewHolder) holder).getRecyclerGallery().setLayoutManager(layoutManager);
            ((EditViewHolder) holder).getRecyclerGallery()
                    .setAdapter(new GalleryAdapter());


            //Choose Location
            chooseLocation();


            //Choose Weather
            mWeather.setOnClickListener(v -> {
                mPresenter.openWeatherDialog();
            });


        }

    }

    public void setWeatherIcon(String id) {
        mImageName = id;
        mWeather.setImageResource(mContext.getResources().getIdentifier(mImageName,"mipmap",mContext.getPackageName()));
        notifyDataSetChanged();
    }

    private void chooseLocation() {

        // Initialize Places.
        Places.initialize(mContext, "AIzaSyBJPVkA_f-Tp2PvzTWQ2p-_IOetu6g-9Q0");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(mContext);

        if (!Places.isInitialized()) {
            Places.initialize(TravelDiaryApplication.getAppContext(), "AIzaSyBJPVkA_f-Tp2PvzTWQ2p-_IOetu6g-9Q0");
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

    }


    @Override
    public int getItemCount() {
        return 1;
    }


}
