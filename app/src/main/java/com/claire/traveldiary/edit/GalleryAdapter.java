package com.claire.traveldiary.edit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.claire.traveldiary.R;
import com.claire.traveldiary.TravelDiaryApplication;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter {

    private static final String TAG = "GalleryAdapter";

    private EditContract.Presenter mPresenter;

    private Context mContext;
    private ArrayList<String> mImages;
    //private ImageView mImageGallery;


    public GalleryAdapter(EditContract.Presenter presenter, ArrayList<String> images) {
       mPresenter = presenter;
       mImages = images;
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageGallery;
        private ImageButton mButtonAdd;

        GalleryViewHolder(View itemView) {
            super(itemView);

            mImageGallery = itemView.findViewById(R.id.edit_gallery);
            mButtonAdd = itemView.findViewById(R.id.add_image);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GalleryViewHolder) {

            //upload photo
            ((GalleryViewHolder) holder).mButtonAdd.setOnClickListener(v -> {
                mPresenter.openGallery();
            });

            if (mImages != null) {

                for(int i = 0; i < mImages.size(); i++) {

                    ((GalleryViewHolder) holder).mButtonAdd.setVisibility(View.INVISIBLE);
                    ((GalleryViewHolder) holder).mImageGallery.setImageBitmap(BitmapFactory.decodeFile(mImages.get(position)));
                }
            }
        }
    }

    public void setImage(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mImages == null) {
            return 1;
        }
        return mImages.size();
    }

}
