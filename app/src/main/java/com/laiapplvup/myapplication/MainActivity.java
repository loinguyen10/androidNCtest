package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.laiapplvup.myapplication.Sound.SoundService;

public class MainActivity extends AppCompatActivity {

    Button loadRSS,loadMusic,loadAnime,loadMap,loadSound;
    ComponentName sv_play = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRSS = findViewById(R.id.loadRSS);
        loadMusic = findViewById(R.id.loadMusic);
        loadAnime = findViewById(R.id.loadAnime);
        loadMap = findViewById(R.id.loadMap);
        loadSound = findViewById(R.id.loadSound);

        loadRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
            }
        });

        loadMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MusicActivity.class));
            }
        });

        loadAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
            }
        });

        loadMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
            }
        });

        loadSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SoundService.class);
                if(sv_play == null){
                    // chống click nhiều
                    sv_play =  startService(intent);
                }else{
                    stopService(intent);
                    sv_play  = null;
                }
            }
        });
    }
}