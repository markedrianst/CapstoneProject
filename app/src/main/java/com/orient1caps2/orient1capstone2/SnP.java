package com.orient1caps2.orient1capstone2;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SnP extends AppCompatActivity {
    private final Handler autoScrollHandler = new Handler();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CardView cardFront, cardBack;
    private boolean isFront = true;

    private VideoView myVideo;
    private ImageView myImage;
    private TextView descriptionText, descriptionText1, myTextView, bishopSisonDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sn_p);

        // Disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Setup RecyclerView (program cards)
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<CardAdapter.CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardAdapter.CardItem(R.drawable.practical_nursing, "Practical Nursing Program"));
        cardItems.add(new CardAdapter.CardItem(R.drawable.contact, "Contact Center Training"));
        cardItems.add(new CardAdapter.CardItem(R.drawable.restaurant, "Restaurant Management"));
        recyclerView.setAdapter(new CardAdapter(cardItems));

        // If CardAdapter intends infinite scroll, the adapter must handle that. Keep this safe fallback:
        recyclerView.post(() -> recyclerView.scrollToPosition(0));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

        startAutoScroll();

        // Flip card setup
        cardFront = findViewById(R.id.card_front);
        cardBack = findViewById(R.id.card_back);

        cardFront.setOnClickListener(v -> flipCard());
        cardBack.setOnClickListener(v -> flipCard());

        // Video / Image for hymn
        myImage = findViewById(R.id.myImage);
        myVideo = findViewById(R.id.myVideo);

        myImage.setOnClickListener(v -> {
            myImage.setVisibility(View.GONE);
            myVideo.setVisibility(View.VISIBLE);

            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dcthymn);
            myVideo.setVideoURI(videoUri);

            // Start video automatically when ready
            myVideo.setOnPreparedListener(mp -> myVideo.start());
        });

// Tap video to play/pause
        myVideo.setOnClickListener(v -> {
            if (myVideo.isPlaying()) {
                myVideo.pause();
            } else {
                myVideo.start();
            }
        });


        // Dropdowns for Blessing & Hymn
        View blessingHeader = findViewById(R.id.blessingHeader);
        ImageView btnBlessing = findViewById(R.id.btnBlessingToggle);
        View blessingContainer = findViewById(R.id.blessingContainer);

        View hymnHeader = findViewById(R.id.hymnHeader);
        ImageView btnHymn = findViewById(R.id.btnHymnToggle);
        View hymnContainer = findViewById(R.id.hymnContainer);

        blessingHeader.setOnClickListener(v -> {
            if (blessingContainer.getVisibility() == View.GONE) {
                expand(blessingContainer);
                btnBlessing.setImageResource(android.R.drawable.arrow_up_float);
            } else {
                collapse(blessingContainer);
                btnBlessing.setImageResource(android.R.drawable.arrow_down_float);
            }
        });

        hymnHeader.setOnClickListener(v -> {
            if (hymnContainer.getVisibility() == View.GONE) {
                expand(hymnContainer);
                btnHymn.setImageResource(android.R.drawable.arrow_up_float);
            } else {
                collapse(hymnContainer);
                btnHymn.setImageResource(android.R.drawable.arrow_down_float);

                // STOP video when collapsing hymn
                if (myVideo.isPlaying()) {
                    myVideo.stopPlayback();
                    myVideo.setVisibility(View.GONE);
                    myImage.setVisibility(View.VISIBLE);
                }
            }
        });

        // Text content setup - only for views present in XML
        descriptionText = findViewById(R.id.descriptionText);
        descriptionText1 = findViewById(R.id.descriptionText1);
        myTextView = findViewById(R.id.myTextView);
        bishopSisonDescription = findViewById(R.id.bishopSisonDescription);

        // simple bullet list
        String[] fruits = {"union with God", "community with others", "harmony with creation"};
        StringBuilder builder = new StringBuilder();
        for (String fruit : fruits) {
            builder.append("• ").append(fruit).append("\n");
        }
        myTextView.setText(builder.toString());

        // HTML-set texts used in original app (trimmed to those we kept)
        String html1 = "<b>Saint Dominic, OP</b> (Spanish: Santo Domingo; 8 August 1170 – 6 August 1221), also known as <b>Dominic de Guzmán</b>, was a Castilian Catholic priest and the founder of the Dominican Order.";
        descriptionText.setText(Html.fromHtml(html1, Html.FROM_HTML_MODE_LEGACY));

        String html2 = "Last commencement exercises, <b>March 1973</b>, Religious Missionaries St. Dominic.";
        descriptionText1.setText(Html.fromHtml(html2, Html.FROM_HTML_MODE_LEGACY));

        String html5 = "<b>Bishop Jesus J. Sison</b> became the pastor of Bonuan, Pangasinan in 1943 and was named bishop of the newly erected diocese of Tarlac in 1963. During his tenure as bishop, he worked tirelessly to improve the Catholic education of his flock. After his retirement in 1988, he moved to America";
        bishopSisonDescription.setText(Html.fromHtml(html5, Html.FROM_HTML_MODE_LEGACY));

        // Home button
        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SnP.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void flipCard() {
        View rootLayout = findViewById(R.id.flip_container);

        if (isFront) {
            FlipAnimation flipAnimation = new FlipAnimation(cardFront, cardBack);
            rootLayout.startAnimation(flipAnimation);
        } else {
            FlipAnimation flipAnimation = new FlipAnimation(cardBack, cardFront);
            rootLayout.startAnimation(flipAnimation);
        }
        isFront = !isFront;
    }

    private void startAutoScroll() {
        final int SCROLL_SPEED = 30;
        final int SCROLL_DELAY = 40; // milliseconds - tweak as needed

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (recyclerView != null) {
                    recyclerView.smoothScrollBy(SCROLL_SPEED, 0);
                    autoScrollHandler.postDelayed(this, SCROLL_DELAY);
                }
            }
        };
        autoScrollHandler.postDelayed(runnable, SCROLL_DELAY);
    }

    // Expand with slow animation
    private void expand(View view) {
        view.setVisibility(View.VISIBLE);
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.requestLayout();

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.setDuration(300); // reduce duration to 300ms for snappier UX (adjust as desired)
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });
        animator.start();
    }

    // Collapse with slow animation
    private void collapse(View view) {
        final int initialHeight = view.getMeasuredHeight();
        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.setDuration(300); // match expand duration
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
            if ((int) animation.getAnimatedValue() == 0) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // stop any pending handlers
        autoScrollHandler.removeCallbacksAndMessages(null);
        if (myVideo != null) {
            myVideo.stopPlayback();
        }
    }
}
