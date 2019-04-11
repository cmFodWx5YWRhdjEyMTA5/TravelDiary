package com.claire.traveldiary.edit;


import android.arch.persistence.room.Room;
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
import android.widget.TextView;

import com.claire.traveldiary.R;
import com.claire.traveldiary.TravelDiaryApplication;
import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.DiaryPlace;
import com.claire.traveldiary.data.room.DiaryDAO;
import com.claire.traveldiary.data.room.DiaryDatabase;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;


public class EditAdapter extends RecyclerView.Adapter {


    private static final String TAG = "EditAdapter";

    private Diary mDiary;
    private DiaryPlace mDiaryPlace;

    private EditContract.Presenter mPresenter;

    private Context mContext;

    //Weather
    private String mImageName;
    private ImageButton mWeather;

    //gallery
    private ArrayList<String> mImagesUri;
    private GalleryAdapter mGalleryAdapter;

    //date
    private String mStringDate;


    public EditAdapter(EditContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }


    public class EditViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerGallery;
        private EditText mTitle;
        private TextView mDate;
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
            mGalleryAdapter = new GalleryAdapter(mPresenter,mImagesUri);
            ((EditViewHolder) holder).mRecyclerGallery.setLayoutManager(new LinearLayoutManager(TravelDiaryApplication.getAppContext(),
                    LinearLayoutManager.HORIZONTAL,false));
            if (((EditViewHolder) holder).mRecyclerGallery.getOnFlingListener() == null) {
                new LinearSnapHelper().attachToRecyclerView(((EditViewHolder) holder).mRecyclerGallery);
            }
            ((EditViewHolder) holder).mRecyclerGallery.setAdapter(mGalleryAdapter);

            //Choose date
            ((EditViewHolder) holder).mDate.setOnClickListener(v -> {
                mPresenter.openDatePicker();
            });
            ((EditViewHolder) holder).mDate.setText(mStringDate);

            //Choose Location
            chooseLocation();


            //Choose Weather
            mWeather.setOnClickListener(v -> {
                mPresenter.openWeatherDialog();
            });

            mDiary = new Diary();
            mDiary.setTitle(((EditViewHolder) holder).mTitle.getText().toString());
            mDiary.setDate(((EditViewHolder) holder).mDate.getText().toString());
            mDiary.setDiaryPlace(mDiaryPlace);
            mDiary.setWeather(mImageName);
            mDiary.setImages(mImagesUri);
            mDiary.setContent(((EditViewHolder) holder).mContent.getText().toString());
            mDiary.setTags(((EditViewHolder) holder).mTags.getTags());
        }

    }


    public void updateWeather(String id) {
        mImageName = id;
        mWeather.setImageResource(mContext.getResources().getIdentifier(mImageName,"mipmap",mContext.getPackageName()));
    }

    public void updateImage(ArrayList<String> images) {
        mImagesUri = images;
        notifyDataSetChanged();
    }

    public void updateDate(String date) {
        mStringDate = date;
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

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
                mDiaryPlace = new DiaryPlace();
                mDiaryPlace.setPlaceId(place.getId());
                mDiaryPlace.setPlaceName(place.getName());
                mDiaryPlace.setLatLng(place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    public void saveDiaryDataToRoom() {
        mPresenter.saveDiaryToRoom(mDiary);
        Log.i(TAG, "Save! ");
    }

    public void unEditDiary() {

    }


    @Override
    public int getItemCount() {
        return 1;
    }


}
