package com.orient1caps2.orient1capstone2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class arselection extends AppCompatActivity {
    int LAUNCH_SECOND_ACTIVITY = 1;
    private AlertDialog noInternetDialog; // Keep reference to dialog

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

        // Handle AR button click with fast internet check
        Button btnLaunchUnity = findViewById(R.id.ARbutton);
        btnLaunchUnity.setOnClickListener(v -> {
            // Prevent multiple dialogs from showing
            if (noInternetDialog != null && noInternetDialog.isShowing()) return;

            if (!hasInternet()) {
                showNoInternetDialog(getNetworkType());
            } else {
                Intent intent = new Intent(arselection.this, dcthallview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    // Instant internet check using NetworkCapabilities
    private boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities nc = cm.getNetworkCapabilities(network);
            return nc != null && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            // Fallback for older devices
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
    }

    // Get network type (Wi-Fi or Mobile)
    private String getNetworkType() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return "Wi-Fi";
                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return "Mobile Data";
            }
        }
        return "No Connection";
    }

    // Custom dialog for no internet
    private void showNoInternetDialog(String networkType) {
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);

        noInternetDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);
        android.widget.TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        android.widget.TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);

        dialogTitle.setText("No Internet Connection");
        if (networkType.equals("Mobile Data")) {
            dialogMessage.setText("Mobile data is on, but there is no internet. Please check your connection.");
        } else if (networkType.equals("Wi-Fi")) {
            dialogMessage.setText("Connected to Wi-Fi, but no internet. Please check your network.");
        } else {
            dialogMessage.setText("No internet connection detected. Please check your Wi-Fi or mobile data.");
        }

        btnYes.setText("Open Settings");
        btnYes.setOnClickListener(v -> {
            noInternetDialog.dismiss();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            Toast.makeText(arselection.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        });

        btnNo.setText("Cancel");
        btnNo.setOnClickListener(v -> noInternetDialog.dismiss());

        noInternetDialog.show();
    }
}
