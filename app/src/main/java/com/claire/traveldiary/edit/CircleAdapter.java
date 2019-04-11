package com.claire.traveldiary.edit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.claire.traveldiary.R;
import com.claire.traveldiary.TravelDiaryApplication;

public class CircleAdapter extends RecyclerView.Adapter {

    private int mSelectedPosition = -1;
    private int mCount;

    public CircleAdapter(int count) {
        mCount = count;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CircleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_circle, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CircleViewHolder) {

            ((CircleViewHolder) holder).getImageCircle().setBackground(new ShapeDrawable(new Shape() {
                @Override
                public void draw(Canvas canvas, Paint paint) {

                    paint.setColor(TravelDiaryApplication.getAppContext().getColor(R.color.white));
                    paint.setAntiAlias(true);
                    if (position == mSelectedPosition) {
                        paint.setStyle(Paint.Style.FILL);

                    } else {
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(TravelDiaryApplication.getAppContext()
                                .getResources().getDimensionPixelSize(R.dimen.edge_detail_circle));
                    }

                    canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2,
                            TravelDiaryApplication.getAppContext().getResources()
                                    .getDimensionPixelSize(R.dimen.radius_detail_circle), paint);
                }
            }));
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    private class CircleViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageCircle;

        CircleViewHolder(View itemView) {
            super(itemView);

            mImageCircle = itemView.findViewById(R.id.image_detail_circle);
        }

        public ImageView getImageCircle() {
            return mImageCircle;
        }
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }
}
