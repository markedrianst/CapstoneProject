package com.orient1caps2.orient1capstone2;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // Apply window insets to root view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize WebViews for justified text
        setupJustifiedTextViews();

        // Back button functionality
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void setupJustifiedTextViews() {
        // Course Overview WebView
        WebView courseOverviewWebView = findViewById(R.id.courseOverviewWebView);
        String courseOverviewText = getString(R.string.course_overview_text);
        String courseOverviewHtml = createJustifiedHtml(courseOverviewText);
        courseOverviewWebView.loadDataWithBaseURL(null, courseOverviewHtml, "text/html", "UTF-8", null);
        configureWebView(courseOverviewWebView);

        // Developer Credits WebView
        WebView developerCreditsWebView = findViewById(R.id.developerCreditsWebView);
        String developerCreditsText = getString(R.string.developer_credits_text);
        String developerCreditsHtml = createJustifiedHtml(developerCreditsText);
        developerCreditsWebView.loadDataWithBaseURL(null, developerCreditsHtml, "text/html", "UTF-8", null);
        configureWebView(developerCreditsWebView);
    }

    private String createJustifiedHtml(String text) {
        return "<html><head>" +
                "<style type='text/css'>" +
                "body {" +
                "  text-align: justify;" +
                "  color: #FFFFFF;" +
                "  font-size: 20px;" +
                "  line-height: 1.5;" +
                "  font-family: 'Poppins', sans-serif;" +
                "  margin: 0;" +
                "  padding: 0;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" + text + "</body></html>";
    }

    private void configureWebView(WebView webView) {
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        // Enable hardware acceleration for better performance
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}