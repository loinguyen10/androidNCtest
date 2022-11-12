package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loadRSS,loadMusic,loadAnime,loadMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRSS = findViewById(R.id.loadRSS);
        loadMusic = findViewById(R.id.loadMusic);
        loadAnime = findViewById(R.id.loadAnime);
        loadMap = findViewById(R.id.loadMap);

        loadRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
            }
        });
    }
}