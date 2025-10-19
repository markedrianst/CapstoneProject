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

        // 🔹 Setup Gmail links
        setupGmailLinks();
    }

    private void setupGmailLinks() {
        // Project Manager
        findViewById(R.id.projectManagerGmail).setOnClickListener(v ->
                openGmail("dandyjohndeguzman@gmail.com"));

        // Programmer
        findViewById(R.id.programmerGmail).setOnClickListener(v ->
                openGmail("markedriantalavera070@gmail.com"));

        // Co-Programmer
        findViewById(R.id.coProgrammerGmail).setOnClickListener(v ->
                openGmail("lanzmallari.edu@gmail.com"));

        // Members
        findViewById(R.id.member1Gmail).setOnClickListener(v ->
                openGmail("johnvincentpantig40@gmail.com"));

        findViewById(R.id.member2Gmail).setOnClickListener(v ->
                openGmail("john2004ong@gmail.com"));

        findViewById(R.id.member3Gmail).setOnClickListener(v ->
                openGmail("aidan09perez@gmail.com"));

        findViewById(R.id.member4Gmail).setOnClickListener(v ->
                openGmail("capiliryan258@gmail.com"));
    }

    private void openGmail(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Orient1 App");

        try {
            startActivity(Intent.createChooser(intent, "Send email using:"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle case where no email app is installed
        }
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
        String referencesHtml = "<html>" +
                "<head>" +
                "<style>" +
                "body {" +
                "  color: white;" +
                "  font-family: Arial, sans-serif;" +
                "  text-align: justify;" +
                "  font-size: 25px;" +
                "  line-height: 1.6;" +
                "  margin: 0;" +
                "  padding: 8px;" +
                "}" +
                "ul {" +
                "  padding-left: 20px;" +
                "  margin: 0;" +
                "}" +
                "li {" +
                "  margin-bottom: 12px;" +
                "  padding-left: 8px;" +
                "  list-style-type: disc;" +
                "}" +
                "a {" +
                "  color: #8ab4f8;" +
                "  text-decoration: none;" +
                "  word-break: break-all;" +
                "}" +
                "a:hover {" +
                "  text-decoration: underline;" +
                "}" +
                ".note {" +
                "  margin-top: 20px;" +
                "  font-style: italic;" +
                "  font-size: 20px;" +
                "  color: #CCCCCC;" +
                "  padding: 10px;" +
                "  border-top: 1px solid #555;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<ul>" +

                "<li>9 Types of Intelligence Infographic. Adioma. <a href='https://blog.adioma.com/wp-content/uploads/2014/03/9-types-of-intelligence-infographic.png'>https://blog.adioma.com/wp-content/uploads/2014/03/9-types-of-intelligence-infographic.png</a></li>" +

                "<li>Alone Icon. UXWing. <a href='https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/alone-icon.png'>https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/alone-icon.png</a></li>" +

                "<li>Atienza, A. D., & Talavera, P. P. Q. (2025). <i>Orient 1 course materials</i> [Unpublished instructional materials]. Dominican College of Tarlac.</li>" +

                "<li>Brain Illustration. Daily Evergreen. <a href='https://dailyevergreen.com/wp-content/uploads/2022/03/brainillustration-900x692.jpg'>https://dailyevergreen.com/wp-content/uploads/2022/03/brainillustration-900x692.jpg</a></li>" +

                "<li>Bright Vector Illustration Lesson Arts. Shutterstock. <a href='https://www.shutterstock.com/image-vector/bright-vector-illustration-lesson-arts-260nw-1092498968.jpg'>https://www.shutterstock.com/image-vector/bright-vector-illustration-lesson-arts-260nw-1092498968.jpg</a></li>" +

                "<li>Catholic.com Image Repository. <a href='https://cdn.catholic.com/wp-content/uploads/AdobeStock_97102570-1200x400.jpeg'>https://cdn.catholic.com/wp-content/uploads/AdobeStock_97102570-1200x400.jpeg</a></li>" +

                "<li>Celebrating Dominic de Guzman: Veritas - Putting on the Truth. OpHope. <a href='https://ophope.org/celebrating-dominic-de-guzman/veritas-putting-on-the-truth/'>https://ophope.org/celebrating-dominic-de-guzman/veritas-putting-on-the-truth/</a></li>" +

                "<li>Dominican College Department [Facebook page]. <a href='https://www.facebook.com/dctcollegedepartment/'>https://www.facebook.com/dctcollegedepartment/</a></li>" +

                "<li>Dominican College of Tarlac Community [Facebook group]. <a href='https://www.facebook.com/groups/3318968261662690/'>https://www.facebook.com/groups/3318968261662690/</a></li>" +

                "<li>Educational Outcomes Image. PharmaSeal. <a href='https://res.cloudinary.com/pharmaseal/image/upload/v1589284159/Outcomes_LI_xzu2ev.jpg'>https://res.cloudinary.com/pharmaseal/image/upload/v1589284159/Outcomes_LI_xzu2ev.jpg</a></li>" +

                "<li>Educational Resources. Pinterest. <a href='https://kr.pinterest.com/pin/431923420496834842/'>https://kr.pinterest.com/pin/431923420496834842/</a></li>" +

                "<li>Educational Resources. Pinterest. <a href='https://i.pinimg.com/1200x/40/5c/09/405c093cd19c8f0820329972b5deff0f.jpg'>https://i.pinimg.com/1200x/40/5c/09/405c093cd19c8f0820329972b5deff0f.jpg</a></li>" +

                "<li>Educational  Video Reference [Dominican Hymn Youtube]. <a href='https://youtu.be/KD4940E4Nf8'>https://youtu.be/KD4940E4Nf8</a></li>" +

                "<li>Environmental Awareness Image. <a href='https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSbq_R_cPKQuLp04nmD9eLnYrui6WEQYPI7RUqew_wMzHzA4Lwp'>Environmental Image Reference</a></li>" +

                "<li>Filipino Student Customs. Rappler. <a href='https://www.rappler.com/life-and-style/arts-culture/188538-filipino-student-customs/'>https://www.rappler.com/life-and-style/arts-culture/188538-filipino-student-customs/</a></li>" +

                "<li>Friends Background Vector Images. Vecteezy. <a href='https://www.vecteezy.com/free-vector/friends-back'>https://www.vecteezy.com/free-vector/friends-back</a></li>" +

                "<li>Friendship Day 2023: Picturesque Locations in India for a Perfect Friend Trip. Hindustan Times. <a href='https://www.hindustantimes.com/photos/lifestyle/arts-culture/friendship-day-2023-7-picturesque-locations-in-india-for-a-perfect-friend-trip-101691050649777.html'>https://www.hindustantimes.com/photos/lifestyle/arts-culture/friendship-day-2023-7-picturesque-locations-in-india-for-a-perfect-friend-trip-101691050649777.html</a></li>" +

                "<li>Group of Friends Hugging Vector. VectorStock. <a href='https://cdn.vectorstock.com/i/1000v/65/23/back-view-of-group-friends-hugging-vector-41636523.jpg'>https://cdn.vectorstock.com/i/1000v/65/23/back-view-of-group-friends-hugging-vector-41636523.jpg</a></li>" +

                "<li>Kitchen Brigade System. Wikipedia. <a href='https://en.wikipedia.org/wiki/Kitchen_brigade'>https://en.wikipedia.org/wiki/Kitchen_brigade</a></li>" +

                "<li>Learning Assistant App Reference. AppRecs. <a href='https://apprecs.com/android/com.xx.zs/%E5%AD%A6%E4%B9%A0%E5%8A%A9%E6%89%8B'>https://apprecs.com/android/com.xx.zs/%E5%AD%A6%E4%B9%A0%E5%8A%A9%E6%89%8B</a></li>" +

                "<li>Manila International Skills Academy (MISA) Qualifications. <a href='https://misaskills.wordpress.com/2018/07/11/manila-international-skills-academy-misa-qualifications/'>https://misaskills.wordpress.com/2018/07/11/manila-international-skills-academy-misa-qualifications/</a></li>" +

                "<li>Medical/Dental PG Entrance Exam Classes. <a href='https://5.imimg.com/data5/MC/RV/MY-60270058/medical-2f-dental-pg-entrance-exam-classes-250x250.jpg'>https://5.imimg.com/data5/MC/RV/MY-60270058/medical-2f-dental-pg-entrance-exam-classes-250x250.jpg</a></li>" +

                "<li>No Artist Known. West facade of the Abbey of Silvacane, Cistercian abbey of Provence founded around 1144 [Photograph]. Bridgeman Images. <a href='https://www.bridgemanimages.com/en/noartistknown/west-facade-of-the-abbey-of-silvacane-cistercian-abbey-of-provence-founded-around-1144-which/photograph/asset/4841831'>https://www.bridgemanimages.com/en/noartistknown/west-facade-of-the-abbey-of-silvacane-cistercian-abbey-of-provence-founded-around-1144-which/photograph/asset/4841831</a></li>" +

                "<li>Rappler Media Assets. <a href='https://www.rappler.com/tachyon/r3-assets/612F469A6EA84F6BAE882D2B94A4B421/img/BAE9ED41474E4D8A95760595CA636DEF/titlecard.jpg?resize=640%2C360&zoom=1'>https://www.rappler.com/tachyon/r3-assets/612F469A6EA84F6BAE882D2B94A4B421/img/BAE9ED41474E4D8A95760595CA636DEF/titlecard.jpg</a></li>" +

                "<li>Rinad Blog Image Repository. <a href='https://rinad327713435.wordpress.com/2018/10/'>https://rinad327713435.wordpress.com/2018/10/</a></li>" +

                "<li>Sir Dan's Teaching Resources. (2020, August 23). Dominican College of Tarlac (DCT) Hymn [Video]. YouTube. <a href='https://youtu.be/bN06vUuSsag'>https://youtu.be/bN06vUuSsag</a></li>" +

                "<li>Smiling Teacher in Front of Class. Shutterstock. <a href='https://www.shutterstock.com/search/smiling-teacher-front-class'>https://www.shutterstock.com/search/smiling-teacher-front-class</a></li>" +

                "<li>Structural Learning. Lesson Study. <a href='https://www.structural-learning.com/post/lesson-study'>https://www.structural-learning.com/post/lesson-study</a></li>" +

                "<li>Student Stress Concept Illustration. PNGTree. <a href='https://png.pngtree.com/png-vector/20240115/original/pngtree-student-stress-concept-illustration-png-image_14117068.png'>https://png.pngtree.com/png-vector/20240115/original/pngtree-student-stress-concept-illustration-png-image_14117068.png</a></li>" +

                "<li>Target and Arrow Icon Vector Illustration. iStock. <a href='https://media.istockphoto.com/id/2225227225/vector/target-and-arrow-icon-vector-illustration.jpg'>https://media.istockphoto.com/id/2225227225/vector/target-and-arrow-icon-vector-illustration.jpg</a></li>" +

                "<li>Urban Decay Pollution Environmental Impact. Dreamstime. <a href='https://thumbs.dreamstime.com/b/urban-decay-pollution-s-grip-tomorrow-witness-stark-reality-future-landscape-image-captures-caused-environmental-301090552.jpg'>https://thumbs.dreamstime.com/b/urban-decay-pollution-s-grip-tomorrow-witness-stark-reality-future-landscape-image-captures-caused-environmental-301090552.jpg</a></li>" +

                "<li>Vegetables Harvest Banner. Dreamstime. <a href='https://thumbs.dreamstime.com/b/vegetables-harvest-banner-collage-popular-agricultural-plant-set-vector-farm-plants-bunch-healthy-food-site-header-horizontal-143720669.jpg'>https://thumbs.dreamstime.com/b/vegetables-harvest-banner-collage-popular-agricultural-plant-set-vector-farm-plants-bunch-healthy-food-site-header-horizontal-143720669.jpg</a></li>" +

                "<li>Why Practical Nursing Should Be Practiced in the Philippines. NurseSEO. <a href='https://nurseseo.wordpress.com/2012/07/26/why-practical-nursing-should-be-practiced-in-the-philippines/'>https://nurseseo.wordpress.com/2012/07/26/why-practical-nursing-should-be-practiced-in-the-philippines/</a></li>" +

                "<li>7 Types of Teachers. Benigno Bam Aquino [Facebook]. <a href='https://www.facebook.com/BenignoBamAquino/posts/834880759982298/'>https://www.facebook.com/BenignoBamAquino/posts/834880759982298/</a></li>" +

                "</ul>" +

                "<div class='note'>" +
                "Note. Images used within the app are derived from the instructional materials provided by the Orient 1 instructors of Dominican College of Tarlac Inc." +
                "</div>" +

                "</body>" +
                "</html>";

        // Configure the references WebView
        referencesWebView.setBackgroundColor(Color.TRANSPARENT);
        referencesWebView.setVerticalScrollBarEnabled(false);
        referencesWebView.setHorizontalScrollBarEnabled(false);

        // Enable these settings for better HTML rendering
        referencesWebView.getSettings().setJavaScriptEnabled(true);
        referencesWebView.getSettings().setLoadWithOverviewMode(true);
        referencesWebView.getSettings().setUseWideViewPort(true);
        referencesWebView.getSettings().setBuiltInZoomControls(false);
        referencesWebView.getSettings().setDisplayZoomControls(false);

        // Use loadDataWithBaseURL instead of loadData
        referencesWebView.loadDataWithBaseURL(null, referencesHtml, "text/html", "UTF-8", null);
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


}