package com.claire.traveldiary.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.data.DiaryPlace;

@Database(entities = {Diary.class, DiaryPlace.class}, version = 1, exportSchema = false)
public abstract class DiaryDatabase extends RoomDatabase {

    private static DiaryDatabase INSTANCE;

    public abstract DiaryDAO getDiaryDAO();

    public static DiaryDatabase getIstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), DiaryDatabase.class, "diary_db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
