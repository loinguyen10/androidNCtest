package com.laiapplvup.myapplication.Music;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.laiapplvup.myapplication.Music.FMusic;

import java.util.List;

@Dao
public interface FMusicDAO {

    @Insert
    void add(FMusic fMusic);

    @Delete
    void delete(FMusic fMusic);

    @Query("select * from FMusic where name like :name")
    List<FMusic> getName(String name);

    @Query("select * from FMusic where userID like :id")
    List<FMusic> getAll(int id);
}
