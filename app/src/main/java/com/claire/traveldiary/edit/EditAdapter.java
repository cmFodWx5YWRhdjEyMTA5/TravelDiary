package com.claire.traveldiary.edit;


import android.content.Context;
import android.net.Uri;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import mabbas007.tagsedittext.TagsEditText;


public class EditAdapter extends RecyclerView.Adapter {


    private static final String TAG = "EditAdapter";

    private Diary mDiary;
    private Diary mNewDiary;
    private DiaryPlace mDiaryPlace;

    private EditContract.Presenter mPresenter;

    private Context mContext;

    //view holder
    private AutocompleteSupportFragment mSupportFragment;

    //Weather
    private String mWeatherUri;

    //gallery
    private ArrayList<String> mImagesList;
    private GalleryAdapter mGalleryAdapter;

    //date
    private String mStringDate;

    //tags
    private ArrayList<String> mTagsList;

    //database
    private DiaryDatabase mDatabase;


    public EditAdapter(EditContract.Presenter presenter, Context context,Diary diary) {
        mPresenter = presenter;
        mContext = context;
        mDiary = diary;
    }


    public class EditViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerGallery;
        private EditText mTitle;
        private TextView mDate;
        private ImageButton mWeather;
        private EditText mContent;
        private TagsEditText mTags;

        public EditViewHolder(@NonNull View itemView) {
            super(itemView);

            mRecyclerGallery = itemView.findViewById(R.id.recycler_gallery);

            mSupportFragment = (AutocompleteSupportFragment)
                    ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof EditViewHolder) {

            if (mDiary != null) {
                Log.d(TAG,"diary not null!" + mDiary.getTitle());

                //can't editable
                ((EditViewHolder) holder).mTitle.setFocusableInTouchMode(false);
                ((EditViewHolder) holder).mDate.setClickable(false);
                ((EditViewHolder) holder).mWeather.setClickable(false);
                //location can't input haven't done

                ((EditViewHolder) holder).mContent.setFocusableInTouchMode(false);
                ((EditViewHolder) holder).mTags.setFocusableInTouchMode(false);

                mGalleryAdapter = new GalleryAdapter(mPresenter, mDiary.getImages());
                ((EditViewHolder) holder).mRecyclerGallery.setLayoutManager(new LinearLayoutManager(TravelDiaryApplication.getAppContext(),
                        LinearLayoutManager.HORIZONTAL,false));
                if ((((EditViewHolder) holder).mRecyclerGallery.getOnFlingListener() == null)) {
                    new LinearSnapHelper().attachToRecyclerView((((EditViewHolder) holder).mRecyclerGallery));
                }
                ((EditViewHolder) holder).mRecyclerGallery.setAdapter(mGalleryAdapter);
                ((EditViewHolder) holder).mTitle.setText(mDiary.getTitle());
                ((EditViewHolder) holder).mDate.setText(mDiary.getDate());
                ((EditViewHolder) holder).mWeather.setImageURI(Uri.parse(mDiary.getWeather()));
                mSupportFragment.setText(mDiary.getDiaryPlace().getPlaceName());
                ((EditViewHolder) holder).mContent.setText(mDiary.getContent());
                //tags haven't done


            } else {
                Log.d(TAG,"diary is null!");

                //Gallery
                mGalleryAdapter = new GalleryAdapter(mPresenter, mImagesList);
                ((EditViewHolder) holder).mRecyclerGallery.setLayoutManager(new LinearLayoutManager(TravelDiaryApplication.getAppContext(),
                        LinearLayoutManager.HORIZONTAL,false));
                if ((((EditViewHolder) holder).mRecyclerGallery.getOnFlingListener() == null)) {
                    new LinearSnapHelper().attachToRecyclerView((((EditViewHolder) holder).mRecyclerGallery));
                }
                ((EditViewHolder) holder).mRecyclerGallery.setAdapter(mGalleryAdapter);

                //Choose date
                ((EditViewHolder) holder).mDate.setOnClickListener(v -> {
                    mPresenter.openDatePicker();
                });
                ((EditViewHolder) holder).mDate.setText(mStringDate);

                //Choose Weather
                ((EditViewHolder) holder).mWeather.setOnClickListener(v -> {
                    mPresenter.openWeatherDialog();
                });
                //init weather icon
                if (mWeatherUri == null) {
                    ((EditViewHolder) holder).mWeather.setImageURI(Uri.parse("android.resource://com.claire.traveldiary/2131558417"));
                } else {
                    ((EditViewHolder) holder).mWeather.setImageURI(Uri.parse(mWeatherUri));
                }

                //Choose Location
                chooseLocation();

                //set tags haven't done

                //random diary id
                Random random = new Random();
                int id = random.nextInt(10000000);

                //save diary to room
                mNewDiary = new Diary();
                mNewDiary.setId(id);
                mNewDiary.setTitle((((EditViewHolder) holder).mTitle.getText().toString()));
                mNewDiary.setDate((((EditViewHolder) holder).mDate.getText().toString()));
                mNewDiary.setDiaryPlace(mDiaryPlace);
                mNewDiary.setWeather(mWeatherUri);
                mNewDiary.setImages(mImagesList);
                mNewDiary.setContent(((EditViewHolder) holder).mContent.getText().toString());
                mNewDiary.setTags(((EditViewHolder) holder).mTags.getTags());

            }
        }
    }


    public void updateWeather(String imageUri) {
        mWeatherUri = imageUri;
        notifyDataSetChanged();
    }

    public void updateImage(ArrayList<String> images) {
        mImagesList = images;
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


        mSupportFragment.setHint("Where am I ?");

        mSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        mSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
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
        notifyDataSetChanged();

        mDatabase = DiaryDatabase.getIstance(mContext);
        DiaryDAO diaryDAO = mDatabase.getDiaryDAO();

        diaryDAO.insertOrUpdate(mNewDiary);

        Log.d(TAG,"Diary" + diaryDAO.getDiarys().size());
        Log.d(TAG,"Diary" + diaryDAO.getDiarys().get(0).getId()+
                diaryDAO.getDiarys().get(0).getTitle()+
                diaryDAO.getDiarys().get(0).getDate()+
                diaryDAO.getDiarys().get(0).getWeather()+
                diaryDAO.getDiarys().get(0).getDiaryPlace().getLatLng()+
                diaryDAO.getDiarys().get(0).getTags()+
                diaryDAO.getDiarys().get(0).getImages()+
                diaryDAO.getDiarys().get(0).getContent());
        Log.i(TAG, "Save! ");

        showDiary(mNewDiary);

    }

    public void showDiary(Diary diary) {
        mDiary = diary;
        notifyDataSetChanged();
    }

    public void editDiary(int id) {

    }


    @Override
    public int getItemCount() {
        return 1;
    }


}
