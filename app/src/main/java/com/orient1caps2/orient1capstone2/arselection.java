package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class arselection extends AppCompatActivity {
 int LAUNCH_SECOND_ACTIVITY=1;
    // Load Unity libraries manually to prevent UnsatisfiedLinkError
    static {
        try {
            System.loadLibrary("main");
            System.loadLibrary("Unity");
            System.loadLibrary("il2cpp");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native library loading failed: " + e.getMessage());
        }
    }

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

        // Handle AR button click with proper error handling
        Button btnLaunchUnity = findViewById(R.id.ARbutton);

        btnLaunchUnity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName(arselection.this, "com.unity3d.player.UnityPlayerActivity");
                startActivity(i);

            }
        });
    }
}