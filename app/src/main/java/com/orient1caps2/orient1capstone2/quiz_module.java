package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class quiz_module extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_module);
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back button line
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(quiz_module.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
            finish();
        });

        // Dynamic Quiz Category Handling
        Map<Integer, String> quizCategories = new HashMap<>();
        quizCategories.put(R.id.ovalButton1, "Dominican College of Tarlac Culture");
        quizCategories.put(R.id.ovalButton2, "Study and Prayer Life");
        quizCategories.put(R.id.ovalButton3, "Introduction on Student Life");
        quizCategories.put(R.id.ovalButton4, "Education And Learning");
        quizCategories.put(R.id.ovalButton5, "Importance of Education And Learning");

        for (Map.Entry<Integer, String> entry : quizCategories.entrySet()) {
            Button button = findViewById(entry.getKey());
            if (button != null) {
                button.setOnClickListener(v -> {
                    Intent intent = new Intent(quiz_module.this, quizlevel.class);
                    intent.putExtra("quizCategory", entry.getValue()); // Pass category dynamically
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                });
            }
        }
    }
}