package com.laiapplvup.myapplication.Music;

import android.util.Log;

public class MusicDTO {
    public String name;
    public String file_path;
    public String toString(){
        Log.d("zzzzzz", "toString: file path " + file_path);
        return  name;
    }
}
