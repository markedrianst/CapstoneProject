package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class dctlayout extends AppCompatActivity {
    private Handler popupHandler = new Handler();
    private String[] buildingMessages = {
            "SLR Building: Clinic at 1st Floor near Canteen",
            "St. Nicholas: Science Lab at 1st Floor",
            "OLP Building: Resurrection Chapel at 1st Floor",
            "Holy Rosary: Dean's Office at 1st Floor",
            "St. Dominic: Offices at 1st Floor",
            "St. Catherine: MIS at 1st Floor",
            "Our Lady of Fatima: Registrar and Accounting Office at 1st Floor"
    };
    private int currentMessageIndex = 0;
    private AlertDialog currentPopup;

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

        // Start showing automatic building popups when activity starts
        startAutoPopups();
    }

    private void startAutoPopups() {
        // Show first popup after 3 second delay
        popupHandler.postDelayed(() -> {
            showBottomPopup(buildingMessages[currentMessageIndex]);
        }, 3000);
    }

    private void showBottomPopup(String message) {
        // Dismiss any existing popup first
        if (currentPopup != null && currentPopup.isShowing()) {
            currentPopup.dismiss();
        }

        View popupView = LayoutInflater.from(this).inflate(R.layout.bottom_popup, null);

        TextView popupText = popupView.findViewById(R.id.popupText);
        popupText.setText(message);

        currentPopup = new AlertDialog.Builder(this)
                .setView(popupView)
                .create();

        Window window = currentPopup.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

            // Set window properties to not affect main UI
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.y = 100; // 100px from bottom

            // REMOVE these problematic flags:
            // params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            //               WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
            //               WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

            // Use these flags instead:
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

            window.setAttributes(params);

            // Add this to ensure the dialog doesn't dim the background
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        currentPopup.show();

        // Auto dismiss after 4 seconds and show next message
        popupHandler.postDelayed(() -> {
            if (currentPopup != null && currentPopup.isShowing()) {
                currentPopup.dismiss();
            }

            // Show next message after a delay
            currentMessageIndex = (currentMessageIndex + 1) % buildingMessages.length;
            popupHandler.postDelayed(() -> {
                showBottomPopup(buildingMessages[currentMessageIndex]);
            }, 2000); // 2 second delay between messages
        }, 4000);
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
        popupHandler.removeCallbacksAndMessages(null);
        if (currentPopup != null && currentPopup.isShowing()) {
            currentPopup.dismiss();
        }
    }
}