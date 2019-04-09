package com.claire.traveldiary.edit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.claire.traveldiary.R;
import com.claire.traveldiary.util.ImageManager;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mImages;

//    public GalleryAdapter(ArrayList<String> images) {
//        mImages = images;
//    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageGallery;
        private ImageButton mButtonAdd;

        GalleryViewHolder(View itemView) {
            super(itemView);

            mImageGallery = itemView.findViewById(R.id.edit_gallery);
            mButtonAdd = itemView.findViewById(R.id.add_image);
        }

        ImageView getImageGallery() {
            return mImageGallery;
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

//        if (holder instanceof GalleryViewHolder) {
//            if (mImages.size() > 0) {
//
//                ImageManager.getInstance().setImageByUrl(
//                        ((GalleryViewHolder) holder).getImageGallery(),
//                        mImages.get(position % mImages.size())); // Real position: position % mImages.size()
//
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//                ((GalleryViewHolder) holder).getImageGallery().setLayoutParams(new ConstraintLayout
//                        .LayoutParams(displayMetrics.widthPixels,
//                        mContext.getResources().getDimensionPixelSize(R.dimen.height_gallery)));
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
