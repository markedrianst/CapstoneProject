package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class dctlayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dctlayout);
        initializeButtons();
        initializeLocationButtons();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // HIDE ALL CLOUD LABELS INITIALLY
        hideAllCloudLabels();

        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(dctlayout.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    // Method to hide all cloud labels
    private void hideAllCloudLabels() {
        int[] cloudLabelIds = {
                R.id.label_st_nicholas,
                R.id.label_san_lorenzo,
                R.id.label_our_lady_peace,
                R.id.label_holy_rosary,
                R.id.label_st_dominic,
                R.id.label_st_catherine,
                R.id.label_our_lady_fatima
        };

        for (int id : cloudLabelIds) {
            findViewById(id).setVisibility(View.GONE);
        }
    }

    // Add this new method to initialize location buttons
    private void initializeLocationButtons() {
        setupLocationButton(R.id.location_st_nicholas, R.id.label_st_nicholas);
        setupLocationButton(R.id.location_san_lorenzo, R.id.label_san_lorenzo);
        setupLocationButton(R.id.location_our_lady_peace, R.id.label_our_lady_peace);
        setupLocationButton(R.id.location_holy_rosary, R.id.label_holy_rosary);
        setupLocationButton(R.id.location_st_dominic, R.id.label_st_dominic);
        setupLocationButton(R.id.location_st_catherine, R.id.label_st_catherine);
        setupLocationButton(R.id.location_our_lady_fatima, R.id.label_our_lady_fatima);
    }

    // Updated method to setup location button click listeners - ONLY SHOWS CLOUD LABELS
    private void setupLocationButton(int locationIconId, int cloudLabelId) {
        findViewById(locationIconId).setOnClickListener(v -> {
            // Hide all other cloud labels first
            hideAllCloudLabels();
            // Show this cloud label
            TextView cloudLabel = findViewById(cloudLabelId);
            cloudLabel.setVisibility(View.VISIBLE);
        });
    }

    private void initializeButtons() {
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        setupButton(R.id.btn_st_nicholas, "St. Nicholas Building", R.drawable.sn, getString(R.string.st_nicholas_description));
        setupButton(R.id.btn_san_lorenzo, "San Lorenzo Ruiz Building", R.drawable.slr, getString(R.string.san_lorenzo_description));
        setupButton(R.id.btn_our_lady_peace, "Our Lady of Peace Building", R.drawable.olp, getString(R.string.our_lady_peace_description));
        setupButton(R.id.btn_holy_rosary, "Holy Rosary Building", R.drawable.hr, getString(R.string.holy_rosary_description));
        setupButton(R.id.btn_st_dominic, "St. Dominic Building", R.drawable.sd, getString(R.string.st_dominic_description));
        setupButton(R.id.btn_our_lady_fatima, "Our Lady of Fatima Building", R.drawable.olf, getString(R.string.our_lady_fatima_description));
        setupButton(R.id.btn_st_catherine, "St. Catherine of Siena Building", R.drawable.scs, getString(R.string.st_catherine_description));
    }

    private void setupButton(int buttonId, String title, int imageResId, String description) {
        findViewById(buttonId).setOnClickListener(v -> {
            // Show main dialog directly (no quick popup for taps)
            showModernDialog(title, imageResId, description);
        });
    }

    private void showModernDialog(String title, int imageResId, String description) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_dialogbox, null);

        TextView titleView = dialogView.findViewById(R.id.dialogTitle);
        ImageView imageView = dialogView.findViewById(R.id.dialogImage);
        WebView webView = dialogView.findViewById(R.id.webViewDescription);
        Button closeButton = dialogView.findViewById(R.id.closeButton);

        titleView.setText(title);
        imageView.setImageResource(imageResId);

        // Configure WebView
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        String htmlContent = "<!DOCTYPE html>" +
                "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<style>" +
                "body {" +
                "  text-align: justify;" +
                "  color: #2D3748;" +
                "  font-size: 16px;" +
                "  line-height: 1.7;" +
                "  padding: 8px;" +
                "  margin: 0;" +
                "  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;" +
                "}" +
                "</style></head>" +
                "<body>" + description.replace("\n", "<br>") + "</body></html>";

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        closeButton.setOnClickListener(v -> alertDialog.dismiss());

        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);
            window.setLayout(width, height);
        }

        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Handler cleanup removed since we removed popup functionality
    }
}