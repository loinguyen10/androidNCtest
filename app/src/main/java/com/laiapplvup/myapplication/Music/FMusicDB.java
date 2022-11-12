package com.laiapplvup.myapplication.Music;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FMusic.class}, version = 1)
public abstract class FMusicDB extends RoomDatabase {
    public abstract FMusicDAO getDAO();
}
