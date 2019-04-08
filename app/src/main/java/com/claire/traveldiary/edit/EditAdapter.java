package com.claire.traveldiary.edit;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.claire.traveldiary.R;
import com.claire.traveldiary.TravelDiaryApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

public class EditAdapter extends RecyclerView.Adapter implements GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private EditContract.Presenter mPresenter;
    private GalleryAdapter mGalleryAdapter;

    private PlaceAutocompleteFragment mAutocompleteFragment;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;



    public EditAdapter(EditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private class EditViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerGallery;
        private EditText mTitle;
        private EditText mDate;
        private EditText mContent;
        private AutoCompleteTextView mLocation;

        public EditViewHolder(@NonNull View itemView) {
            super(itemView);

            mRecyclerGallery = itemView.findViewById(R.id.recycler_gallery);
            mTitle = itemView.findViewById(R.id.edit_diary_title);
            mDate = itemView.findViewById(R.id.edit_diary_date);
            mContent = itemView.findViewById(R.id.edit_diary_content);
            mLocation = itemView.findViewById(R.id.edit_diary_location);

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
            LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(((EditViewHolder) holder).getRecyclerGallery());

            ((EditViewHolder) holder).getRecyclerGallery().setLayoutManager(layoutManager);
            ((EditViewHolder) holder).getRecyclerGallery()
                    .setAdapter(new GalleryAdapter());

            //Location
            //((EditViewHolder) holder).mLocation.
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
