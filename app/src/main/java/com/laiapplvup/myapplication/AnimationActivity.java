package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class AnimationActivity extends AppCompatActivity {

    Button xoay,zoom,move,fade;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        txt = findViewById(R.id.txt);
        xoay = findViewById(R.id.rotate);
        zoom = findViewById(R.id.zoom);
        move = findViewById(R.id.move);
        fade = findViewById(R.id.fade);

//        X là ngang, Y là dọc

        xoay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Không xoay đc 2 lần
                ObjectAnimator anime = ObjectAnimator.ofFloat(txt,"rotation",360);
                anime.setDuration(2000);
                anime.start();
            }
        });

        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Hai cách:
//                1. Dùng AnimatorSet đây: Giống HomeBook
//                ObjectAnimator aniX = ObjectAnimator.ofFloat(txt,"scaleX",0f,2.5f);
//                ObjectAnimator aniY = ObjectAnimator.ofFloat(txt,"scaleY",0f,2.5f);
//                aniX.setDuration(1500);
//                aniY.setDuration(1500);
//                AnimatorSet set = new AnimatorSet();
//                set.play(aniX).with(aniY);
//                set.start();

//                2.Dùng anim
                Animation animation = AnimationUtils.loadAnimation(AnimationActivity.this,R.anim.zoom);
                txt.startAnimation(animation);
            }
        });

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anime = new TranslateAnimation(Animation.ABSOLUTE,1000,Animation.ABSOLUTE,-500);
                anime.setDuration(1000);
                txt.startAnimation(anime);
            }
        });

        fade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                biến mất
                ObjectAnimator alpha = ObjectAnimator.ofFloat(txt,"alpha",1f,0f);
                alpha.setDuration(1000);
                alpha.start();

//                hiện lại
                ObjectAnimator alphax = ObjectAnimator.ofFloat(txt,"alpha",0f,1f);
                alphax.setDuration(1000);
                alphax.start();
            }
        });

    }
}