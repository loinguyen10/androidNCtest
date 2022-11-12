package com.laiapplvup.myapplication.Music;

import org.jetbrains.annotations.NotNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class FMusic {
    @PrimaryKey
    @NotNull
    String name;

    @ColumnInfo
    int userID;

    public FMusic(String name, int userID) {
        this.name = name;
        this.userID = userID;
    }

    public FMusic() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return ""+getName();
    }
}
