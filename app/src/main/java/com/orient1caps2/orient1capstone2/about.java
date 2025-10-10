package com.orient1caps2.orient1capstone2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
// Show Disclaimer dialog on load
        showDisclaimerDialog();

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

        // 🔹 Setup FB links
        setupFacebookLinks();
    }

    private void setupFacebookLinks() {
        // Project Manager
        findViewById(R.id.projectManagerImage).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/JustcallmeDY"));

        // Programmer
        findViewById(R.id.programmerImage).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/markedrian70"));

        // Co-Programmer
        findViewById(R.id.coProgrammerImage).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/lj.mallari.397"));

        // Members
        findViewById(R.id.member1Image).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/johnvincent.pantig"));

        findViewById(R.id.member2Image).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/john.rhendell.ong"));

        findViewById(R.id.member3Image).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/edmar.perez.1690"));

        findViewById(R.id.member4Image).setOnClickListener(v ->
                openFacebook("https://www.facebook.com/capiliryan25"));
    }

    private void openFacebook(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void setupJustifiedTextViews() {
        WebView courseOverviewWebView = findViewById(R.id.courseOverviewWebView);
        String courseOverviewText = getString(R.string.course_overview_text);
        String courseOverviewHtml = createJustifiedHtml(courseOverviewText);
        courseOverviewWebView.loadDataWithBaseURL(null, courseOverviewHtml, "text/html", "UTF-8", null);
        configureWebView(courseOverviewWebView);

        WebView developerCreditsWebView = findViewById(R.id.developerCreditsWebView);
        String developerCreditsText = getString(R.string.developer_credits_text);
        String developerCreditsHtml = createJustifiedHtml(developerCreditsText);
        developerCreditsWebView.loadDataWithBaseURL(null, developerCreditsHtml, "text/html", "UTF-8", null);
        configureWebView(developerCreditsWebView);
        WebView referencesWebView = findViewById(R.id.referencesWebView);

        String referencesHtml = "<html><body style='color: white; font-family: Arial; text-align: justify; font-size: 14px; line-height: 1.6;'>" +
                "<p>Atienza, A. D., & Talavera, P. P. Q. (2025). Orient 1 course materials [Unpublished instructional materials]. Dominican College of Tarlac.</p>" +
                "<p>Sir Dan's Teaching Resources. (2020, August 23). " +
                "<p>Dominican College of Tarlac (DCT) Hymn [Video]. YouTube. <a href='https://youtu.be/bN06vUuSsag'>https://youtu.be/bN06vUuSsag</a></p></p>"+
                "<p style='margin-top: 16px; font-style: italic;font-size: 11px;'>&nbsp;&nbsp;&nbsp;&nbsp;Note. Images used within the app are derived from the instructional materials provided by the Orient 1 instructors of Dominican College of Tarlac Inc.</p>" +
                "</body></html>";

        referencesWebView.loadData(referencesHtml, "text/html", "UTF-8");
        referencesWebView.setBackgroundColor(Color.TRANSPARENT);
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
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void showDisclaimerDialog() {
        String disclaimerMessage =
                "<b>Disclaimer</b> The Orient 1 app is provided for educational use within Dominican College of Tarlac Inc.<br><br>" +
                        "• Most modules work offline, but 360 Campus View requires internet access.<br>" +
                        "• DCT Campus View requires internet access.<br><br>" +
                        "By tapping <b>“I Understand”</b>, you acknowledge that this app is intended for educational purposes only."
                ;

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ModernDialogTheme);

        builder.setTitle("Disclaimer")
                .setMessage(Html.fromHtml(disclaimerMessage, Html.FROM_HTML_MODE_LEGACY))
                .setCancelable(false)
                .setPositiveButton("I Understand", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        // Customize background (rounded corners + subtle color)
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000"))); // Light gray
        }

        dialog.show();

        // Make buttons modern
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(Color.parseColor("#3C7D8D")); // Teal accent
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setAllCaps(false);
    }

}
