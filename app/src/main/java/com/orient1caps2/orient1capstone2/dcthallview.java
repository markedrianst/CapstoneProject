package com.orient1caps2.orient1capstone2;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

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
        builder.setTitle("Info...")
                .setMessage("Use search to navigate the specific location.");

        AlertDialog dialog = builder.create();

        // Set slightly transparent background: #CC = 80% opacity
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xCCD9D9D9));
        }

        // Make dialog cancelable on touch outside
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        // Dismiss on any touch inside the dialog
        dialog.setOnShowListener(d -> {
            dialog.getWindow().getDecorView().setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            });
        });
    }

    // To handle device back button for WebView navigation
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
