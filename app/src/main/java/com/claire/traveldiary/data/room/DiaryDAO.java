package com.claire.traveldiary.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.DiaryPlace;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DiaryDAO {

    @Insert
    void insert(Diary diaries);

    @Update
    void update(Diary diaries);

    @Delete
    void delete(Diary diaries);

    @Query("SELECT * FROM diary")
    List<Diary> getDiarys();

    @Query("DELETE FROM Diary WHERE mId = :id")
    void deleteDiarybyId(int id);


    @Query("SELECT * FROM diary WHERE mId = :id")
    Diary getDiarybyId(int id);


    @TypeConverters({ImagesConverter.class, TagsConverter.class, PlacesConverter.class})
    @Query("UPDATE diary SET mTitle = :title, mDate = :date, mDiaryPlace = :diaryPlace, mWeather = :weather, mImages = :images, mContent = :content WHERE mId = :id")
    void updateDiary(int id, String title, String date, DiaryPlace diaryPlace, String weather, ArrayList<String> images, String content);


    default void insertOrUpdate(Diary diary) {
        Diary diaryFromDB = getDiarybyId(diary.getId());

        if (diaryFromDB != null)
            updateDiary(diary.getId(), diary.getTitle(), diary.getDate(), diary.getDiaryPlace(), diary.getWeather(), diary.getImages(), diary.getContent());
        else
            insert(diary);
    }



}
