package com.claire.traveldiary.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.claire.traveldiary.data.Diary;

@Database(entities = {Diary.class}, version = 1, exportSchema = false)
public abstract class DiaryDatabase extends RoomDatabase {


    public abstract DiaryDAO getDiaryDAO();

}
