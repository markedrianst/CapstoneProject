package com.orient1caps2.orient1capstone2;

import android.os.Bundle;
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

        // initialize WebView
        webview = findViewById(R.id.webview);

        // Enable JavaScript if needed
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load your web app URL
        webview.loadUrl("https://domincan-college-of-tarlac-corridor.vercel.app/");

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
