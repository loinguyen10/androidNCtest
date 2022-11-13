package com.laiapplvup.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.laiapplvup.myapplication.News.Entry;

import java.util.ArrayList;
import java.util.List;

public class NewsDB extends SQLiteOpenHelper {
    Context context;
    public NewsDB(Context context) {
        super(context, "data.db", null , 1);
        this.context = context;
    }
    public static final String TABALE_NAME = "Newsdb";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TILTLE = "title";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_DES = "description";
    public static final String COLUMN_DATE = "date";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE  " + TABALE_NAME + "(" + COLUMN_ID + " TEXT PRIMARY KEY  ," + COLUMN_TILTLE + " TEXT," + COLUMN_LINK + " TEXT," + COLUMN_DES + " TEXT, " + COLUMN_DATE +"  TEXT )";
        db.execSQL(CREATE_TABLE);

    }
    public long insertTK(Entry nguoiDung) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, nguoiDung.getId());
        values.put(COLUMN_TILTLE, nguoiDung.getTitle());
        values.put(COLUMN_LINK, nguoiDung.getLink());
        values.put(COLUMN_DES, nguoiDung.getDescription());
        values.put(COLUMN_DATE, nguoiDung.getDate());
        long kq = database.insert(TABALE_NAME, null, values);

//        Toast.makeText(context, "hừm" + kq, Toast.LENGTH_SHORT).show();

        return kq;
    }
    public List<Entry> getAllTK() {
        List<Entry> list = new ArrayList<>();
        String SELECT = "SELECT * FROM " + TABALE_NAME;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(SELECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                //Lấy ra dữ liệu ở các cột
//                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TILTLE));
                String link = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
                String des = cursor.getString(cursor.getColumnIndex(COLUMN_DES));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                //Thêm dữ liệu vừa lấy ra từ cột vào đối tượng Laptop
                Entry nguoiDung = new Entry();
//                nguoiDung.setId(id);
                nguoiDung.setTitle(title);
                nguoiDung.setLink(link);
                nguoiDung.setDescription(des);
                nguoiDung.setDate(date);
                //Thêm đối tượng vào danh sách
                list.add(nguoiDung);
                //
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
