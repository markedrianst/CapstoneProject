package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.Map;

public class quizlevel extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quizlevel);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //To retrieve data into other class
        String quizCategory = getIntent().getStringExtra("quizCategory");
        TextView title = findViewById(R.id.title);
        title.setText(quizCategory);
        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        Map<Integer, String> difficulty = new HashMap<>();
        difficulty.put(R.id.easy, "Easy");
        difficulty.put(R.id.medium, "Medium");
        difficulty.put(R.id.hard, "Hard");

        for (Map.Entry<Integer, String> entry : difficulty.entrySet()) {
            MaterialCardView materialCardView = findViewById(entry.getKey());
            if (materialCardView != null) {
                materialCardView.setOnClickListener(v -> {
                    Intent intent = new Intent(quizlevel.this, quizActivity.class);
                    intent.putExtra("difficulty", entry.getValue()); // Pass difficulty dynamically
                    intent.putExtra("quizCategory", quizCategory); //  Pass category dynamically
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                });
            }
        }


    }
}