package com.laiapplvup.myapplication.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MapDAO {

    MapSQL Msql;
    SQLiteDatabase db;

    public MapDAO(Context context) {
        Msql = new MapSQL(context);
        db = Msql.getWritableDatabase();
    }

    public List<MapModel> start() {
        List<MapModel> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from map_tb", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String name = c.getString(0);
            String location = c.getString(1);
            MapModel x = new MapModel(name, location);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public long add(MapModel x) {
        ContentValues values = new ContentValues();
        values.put("name", x.getName());
        values.put("location", x.getLocation());
        long kq = db.insert("map_tb", null, values);
        return kq;
    }
}
