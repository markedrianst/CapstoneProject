package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

public class dctlayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dctlayout);
        initializeButtons();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(dctlayout.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });

    }

    private void initializeButtons() {
        // Back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Building buttons with responsive dialogs
        setupButton(R.id.btn_st_nicholas,
                "St. Nicholas Building",
                R.drawable.sn,
                getString(R.string.st_nicholas_description));

        setupButton(R.id.btn_san_lorenzo,
                "San Lorenzo Ruiz Building",
                R.drawable.slr,
                getString(R.string.san_lorenzo_description));

        setupButton(R.id.btn_our_lady_peace,
                "Our Lady of Peace Building",
                R.drawable.olp,
                getString(R.string.our_lady_peace_description));

        setupButton(R.id.btn_holy_rosary,
                "Holy Rosary Building",
                R.drawable.hr,
                getString(R.string.holy_rosary_description));

        setupButton(R.id.btn_st_dominic,
                "St. Dominic Building",
                R.drawable.sd,
                getString(R.string.st_dominic_description));

        setupButton(R.id.btn_our_lady_fatima,
                "Our Lady of Fatima Building",
                R.drawable.olf,
                getString(R.string.our_lady_fatima_description));

        setupButton(R.id.btn_st_catherine,
                "St. Catherine of Siena Building",
                R.drawable.scs,
                getString(R.string.st_catherine_description));
    }

    private void setupButton(int buttonId, String title, int imageResId, String description) {
        findViewById(buttonId).setOnClickListener(v ->
                showResponsiveDialog(title, imageResId, description));
    }

    private void showResponsiveDialog(String title, int imageResId, String description) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_dialogbox, null);

        TextView titleView = dialogView.findViewById(R.id.dialogTitle);
        ImageView imageView = dialogView.findViewById(R.id.dialogImage);
        WebView webView = dialogView.findViewById(R.id.webViewDescription);

        titleView.setText(title);
        imageView.setImageResource(imageResId);

        // Configure responsive WebView
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVerticalScrollBarEnabled(false);

        String htmlContent = "<!DOCTYPE html>" +
                "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<style>" +
                "body {" +
                "  text-align: justify;" +
                "  color: #000000;" +
                "  font-size: calc(14px + 0.5vw);" +
                "  line-height: 1.6;" +
                "  padding: 8px;" +
                "  margin: 0;" +
                "  font-family: sans-serif;" +
                "}" +
                "</style></head>" +
                "<body>" + description.replace("\n", "<br>") + "</body></html>";

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .create();

        dialog.show();

        // Adjust dialog width for different screens
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        dialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }
}