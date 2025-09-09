package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class arselection extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_arselection);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        // Handle Dctmap button click
        Button dctMapButton = findViewById(R.id.Dctmap);
        dctMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(arselection.this, dctlayout.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

//////         Handle Unity AR button click
//        // Handle Unity AR button click
//        Button unityARButton = findViewById(R.id.ARbutton);
//        unityARButton.setOnClickListener(v -> {
//            Intent intent = new Intent(this, com.unity3d.player.UnityPlayerActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });

        // Example button to launch Unity
//        findViewById(R.id.ARbutton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Unity's main activity
//                Intent intent = new Intent(arselection.this, com.unity3d.player.UnityPlayerActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
