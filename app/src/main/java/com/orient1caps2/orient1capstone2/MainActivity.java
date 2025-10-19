package com.orient1caps2.orient1capstone2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.widget.TextViewCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private boolean isNavigating = false;

    @SuppressLint("MissingSuperCall") // we handle back ourselves
    @Override
    public void onBackPressed() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.exit_dialog1, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            finishAffinity();
        });

        btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // ✅ Show disclaimer only once
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean disclaimerShown = prefs.getBoolean("disclaimerShown", false);

        if (!disclaimerShown) {
            showDisclaimerDialog();
            prefs.edit().putBoolean("disclaimerShown", true).apply();
        }

        MaterialButton btnOne = findViewById(R.id.btnOne);
        MaterialButton btnTwo = findViewById(R.id.btnTwo);
        MaterialButton btnThree = findViewById(R.id.btnThree);
        ImageButton btnAbout = findViewById(R.id.aboutButton);
        TextView title = findViewById(R.id.textOrient);
        TextView footer = findViewById(R.id.footerText);

        // Load fade-in animations
        Animation fadeIn1 = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_bottom);
        Animation fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_bottom);
        Animation fadeIn3 = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_bottom);
        Animation fadeIn4 = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_bottom);

        // Staggered animation sequence
        new Handler().postDelayed(() -> {
            btnAbout.setVisibility(View.VISIBLE);
            btnAbout.startAnimation(fadeIn4);
        }, 300);

        new Handler().postDelayed(() -> {
            btnOne.setVisibility(View.VISIBLE);
            btnOne.startAnimation(fadeIn1);
        }, 500);

        new Handler().postDelayed(() -> {
            btnTwo.setVisibility(View.VISIBLE);
            btnTwo.startAnimation(fadeIn2);
        }, 800);

        new Handler().postDelayed(() -> {
            btnThree.setVisibility(View.VISIBLE);
            btnThree.startAnimation(fadeIn3);
        }, 1100);

        applyResponsiveAdjustments();

        // Navigation buttons
        btnOne.setOnClickListener(v -> navigateOnce(new Intent(MainActivity.this, lessons_module.class)));
        btnTwo.setOnClickListener(v -> navigateOnce(new Intent(MainActivity.this, quiz_module.class)));
        btnThree.setOnClickListener(v -> navigateOnce(new Intent(MainActivity.this, arselection.class)));
        btnAbout.setOnClickListener(v -> navigateOnce(new Intent(MainActivity.this, about.class)));
    }

    private synchronized void navigateOnce(Intent intent) {
        if (isNavigating) return;
        isNavigating = true;

        disableNavigationViews();

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.fade_out_to_bottom);
    }

    private void disableNavigationViews() {
        View v1 = findViewById(R.id.btnOne);
        View v2 = findViewById(R.id.btnTwo);
        View v3 = findViewById(R.id.btnThree);
        View v4 = findViewById(R.id.aboutButton);
        if (v1 != null) v1.setEnabled(false);
        if (v2 != null) v2.setEnabled(false);
        if (v3 != null) v3.setEnabled(false);
        if (v4 != null) v4.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNavigating = false;
        View v1 = findViewById(R.id.btnOne);
        View v2 = findViewById(R.id.btnTwo);
        View v3 = findViewById(R.id.btnThree);
        View v4 = findViewById(R.id.aboutButton);
        if (v1 != null) v1.setEnabled(true);
        if (v2 != null) v2.setEnabled(true);
        if (v3 != null) v3.setEnabled(true);
        if (v4 != null) v4.setEnabled(true);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        applyResponsiveAdjustments();
    }

    @SuppressLint("RestrictedApi")
    private void applyResponsiveAdjustments() {
        MaterialButton btnOne = findViewById(R.id.btnOne);
        MaterialButton btnTwo = findViewById(R.id.btnTwo);
        MaterialButton btnThree = findViewById(R.id.btnThree);
        TextView title = findViewById(R.id.textOrient);
        TextView footer = findViewById(R.id.footerText);

        // Adaptive autosize for title
        if (title != null) {
            title.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            title.setAutoSizeTextTypeUniformWithConfiguration(18, 32, 2, TypedValue.COMPLEX_UNIT_SP);
        }

        // Adaptive autosize for buttons
        MaterialButton[] buttons = {btnOne, btnTwo, btnThree};
        for (MaterialButton b : buttons) {
            if (b == null) continue;
            b.setAutoSizeTextTypeWithDefaults(TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            b.setAutoSizeTextTypeUniformWithConfiguration(14, 28, 2, TypedValue.COMPLEX_UNIT_SP);
        }

        // Responsive button width
        int screenWidthPx = getResources().getDisplayMetrics().widthPixels;
        int paddingSides = dpToPx(32);
        int desiredMaxWidth = screenWidthPx - paddingSides;
        int originalBtnWidthPx = dpToPx(280);

        if (screenWidthPx < originalBtnWidthPx + paddingSides) {
            for (MaterialButton b : buttons) {
                if (b == null) continue;
                ViewGroup.LayoutParams lp = b.getLayoutParams();
                lp.width = desiredMaxWidth;
                b.setLayoutParams(lp);
            }
        }

        // Footer autosize
        if (footer != null) {
            footer.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    // ✅ Disclaimer dialog (only once)
    private void showDisclaimerDialog() {
        String disclaimerMessage =
                " The Orient 1 app is provided for educational use within Dominican College of Tarlac Inc.<br><br>" +
                        "Most modules work offline, but 360 Campus View requires internet access.<br>" +
                        "<br>By tapping <b>“I Understand”</b>, you acknowledge that this app is intended for educational purposes only.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ModernDialogTheme);

        builder.setTitle("Disclaimer")
                .setMessage(Html.fromHtml(disclaimerMessage, Html.FROM_HTML_MODE_LEGACY))
                .setCancelable(false)
                .setPositiveButton("I Understand", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        // Force light theme
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF"))); // White background
            // Force light mode
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                dialog.getWindow().getDecorView().setForceDarkAllowed(false);
            }
        }

        dialog.show();

        // Set button text color
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(Color.parseColor("#3C7D8D"));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setAllCaps(false);
    }}
