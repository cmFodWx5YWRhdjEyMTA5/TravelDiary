package com.claire.traveldiary.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.claire.traveldiary.data.room.ImagesConverter;
import com.claire.traveldiary.data.room.PlacesConverter;
import com.claire.traveldiary.data.room.TagsConverter;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Diary {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;

    private String mTitle;

    private String mDate;

    @TypeConverters(PlacesConverter.class)
    private DiaryPlace mDiaryPlace;

    private String mWeather;

    @TypeConverters(ImagesConverter.class)
    private ArrayList<String> mImages;

    private String mContent;

    @TypeConverters(TagsConverter.class)
    private List<String> mTags;


    public Diary() {

        mId = -1;
        mTitle = "";
        mDate = "";
        mDiaryPlace = new DiaryPlace();
        mWeather = "ic_sunny";
        mImages = new ArrayList<>();
        mContent = "";
        mTags = null;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public DiaryPlace getDiaryPlace() {
        return mDiaryPlace;
    }

    public void setDiaryPlace(DiaryPlace diaryPlace) {
        mDiaryPlace = diaryPlace;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String weather) {
        mWeather = weather;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<String> images) {
        mImages = images;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        mTags = tags;
    }
}
