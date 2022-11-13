package com.laiapplvup.myapplication.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MapSQL extends SQLiteOpenHelper {

    final String map_SQL = "CREATE TABLE map_tb(name text primary key NOT NULL,location text NOT NULL)";

    Context context;
    SQLiteDatabase database;

    public MapSQL(@Nullable Context context) {
        super(context, "name.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(map_SQL);
        db.execSQL("insert into map_tb(name,location) values ('FPT Poly HN','21.038291,105.746793')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("drop table if exists map_tb");
            onCreate(db);
        }
    }

}
