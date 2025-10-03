package com.orient1caps2.orient1capstone2;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class dcthallview extends AppCompatActivity {
    private WebView webview; // declare only

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcthallview);

        // Show slightly transparent dialog
        showNavigationDialog();

        // initialize WebView
        webview = findViewById(R.id.webview);

        // Enable JavaScript if needed
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load your web app URL
        webview.loadUrl("https://dominican-college-of-tarlac-corridor.vercel.app/");

        // Make links open inside WebView instead of external browser
        webview.setWebViewClient(new WebViewClient());

        // Handle back button click from layout
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            if (webview.canGoBack()) {
                webview.goBack(); // go back in webview history
            } else {
                finish(); // close activity if no history
            }
        });
    }

    private void showNavigationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_message, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            // Make background fully transparent so only your custom layout shows
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT)
            );
        }

        // Allow dismiss on outside touch
        dialog.setCanceledOnTouchOutside(true);

        // Allow dismiss on tapping the dialog itself
        dialogView.setOnClickListener(v -> dialog.dismiss());

        // Handle back button dismissal
        dialog.setOnKeyListener((d, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                dialog.dismiss();
                return true;
            }
            return false;
        });

        dialog.show();
    }
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
