package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

public class lessons_module extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_module);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Animation slide1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation slide2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation slide3 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation slide4 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

        CardView btnOne = findViewById(R.id.rectangle1);
        CardView btnTwo = findViewById(R.id.rectangle2);
        CardView btnThree = findViewById(R.id.rectangle3);
        CardView btnAbout = findViewById(R.id.rectangle4);
        btnOne.setVisibility(View.INVISIBLE);
        btnTwo.setVisibility(View.INVISIBLE);
        btnThree.setVisibility(View.INVISIBLE);
        btnAbout.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(() -> {
            btnOne.setVisibility(View.VISIBLE);
            btnOne.startAnimation(slide1);
        }, 300);

        new Handler().postDelayed(() -> {
            btnTwo.setVisibility(View.VISIBLE);
            btnTwo.startAnimation(slide2);
        }, 600);

        new Handler().postDelayed(() -> {
            btnThree.setVisibility(View.VISIBLE);
            btnThree.startAnimation(slide3);
        }, 900);

        new Handler().postDelayed(() -> {
            btnAbout.setVisibility(View.VISIBLE);
            btnAbout.startAnimation(slide4);
        }, 1200);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(lessons_module.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
            finish();
        });


        // Oval Button 1 - opens Dctculture activity
        Button ovalButton1 = findViewById(R.id.ovalButton1);
        ovalButton1.setOnClickListener(v -> {
            Intent intent = new Intent(lessons_module.this, Dctculture.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Oval Button 2 - opens SnP activity
        Button ovalButton2 = findViewById(R.id.ovalButton2);
        ovalButton2.setOnClickListener(v -> {
            Intent intent = new Intent(lessons_module.this, SnP.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Oval Button 3 - opens IOL3 activity
        Button ovalButton3 = findViewById(R.id.ovalButton3);
        ovalButton3.setOnClickListener(v -> {
            Intent intent = new Intent(lessons_module.this, IOL3.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Oval Button 4 - open EAL4 activity
        Button ovalButton4 = findViewById(R.id.ovalButton4);
        ovalButton4.setOnClickListener(v -> {
            Intent intent = new Intent(lessons_module.this, EAL4.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Oval Button 5 - open IOEAL5 activity
        Button ovalButton5 = findViewById(R.id.ovalButton5);
        ovalButton5.setOnClickListener(v -> {
            Intent intent = new Intent(lessons_module.this, IOEAL5.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}
