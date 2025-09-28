package com.orient1caps2.orient1capstone2;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
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

    // For animated dropdowns
    private View[] allContents;
    private ImageView[] allArrows;

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

        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.post(() -> recyclerView.scrollToPosition(0));
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
            myVideo.setOnPreparedListener(mp -> myVideo.start());
        });

        myVideo.setOnClickListener(v -> {
            if (myVideo.isPlaying()) myVideo.pause();
            else myVideo.start();
        });

        // Setup dropdowns with animation
        setupDropdownSections();

        // Text content setup
        descriptionText = findViewById(R.id.descriptionText);
        descriptionText1 = findViewById(R.id.descriptionText1);
        myTextView = findViewById(R.id.myTextView);
        bishopSisonDescription = findViewById(R.id.bishopSisonDescription);

        // Sample bullet list
        String[] fruits = {"Order of the Preachers\nFounder\nSt. Dominic De Guzman\nBorn in Caleuega, Spain in 1170\n..."}; // shortened for brevity
        StringBuilder builder = new StringBuilder();
        for (String fruit : fruits) builder.append("• ").append(fruit).append("\n");
        myTextView.setText(builder.toString());

        String html1 = "<b>Saint Dominic, OP</b> (Spanish: Santo Domingo; 8 August 1170 – 6 August 1221), also known as <b>Dominic de Guzmán</b>, was a Castilian Catholic priest and the founder of the Dominican Order.";
        descriptionText.setText(Html.fromHtml(html1, Html.FROM_HTML_MODE_LEGACY));
        String html2 = "Last commencement exercises, <b>March 1973</b>, Religious Missionaries St. Dominic.";
        descriptionText1.setText(Html.fromHtml(html2, Html.FROM_HTML_MODE_LEGACY));
        String html5 = "<b>Bishop Jesus J. Sison</b> became the pastor of Bonuan, Pangasinan in 1943 and was named bishop of the newly erected diocese of Tarlac in 1963...";
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

    private void setupDropdownSections() {
        allContents = new View[]{
                findViewById(R.id.heroContainer),
                findViewById(R.id.scholasticContainer),
                findViewById(R.id.blessingContainer),
                findViewById(R.id.hymnContainer)
        };

        allArrows = new ImageView[]{
                findViewById(R.id.btnHeroToggle),
                findViewById(R.id.btnScholasticToggle),
                findViewById(R.id.btnBlessingToggle),
                findViewById(R.id.btnHymnToggle)
        };

        setupDropdown(R.id.heroHeader, R.id.heroContainer, R.id.btnHeroToggle);
        setupDropdown(R.id.scholasticHeader, R.id.scholasticContainer, R.id.btnScholasticToggle);
        setupDropdown(R.id.blessingHeader, R.id.blessingContainer, R.id.btnBlessingToggle);
        setupDropdown(R.id.hymnHeader, R.id.hymnContainer, R.id.btnHymnToggle);
    }

    private void setupDropdown(int headerId, int contentId, int arrowId) {
        View header = findViewById(headerId);
        final View content = findViewById(contentId);
        final ImageView arrow = findViewById(arrowId);

        header.setOnClickListener(v -> {
            if (content.getVisibility() == View.GONE) {
                // Close all other sections
                for (int i = 0; i < allContents.length; i++) {
                    if (allContents[i] != content && allContents[i].getVisibility() == View.VISIBLE) {
                        collapse(allContents[i]);
                        rotateArrow(allArrows[i], 180f, 0f);
                    }
                }
                expand(content);
                rotateArrow(arrow, 0f, 180f);
            } else {
                collapse(content);
                rotateArrow(arrow, 180f, 0f);
                if (content.getId() == R.id.hymnContainer && myVideo != null && myVideo.isPlaying()) {
                    myVideo.stopPlayback();
                    myVideo.setVisibility(View.GONE);
                    myImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) v.setVisibility(View.GONE);
                else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void rotateArrow(ImageView arrow, float from, float to) {
        RotateAnimation rotate = new RotateAnimation(
                from, to,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }

    private void flipCard() {
        View rootLayout = findViewById(R.id.flip_container);
        FlipAnimation flipAnimation = isFront ? new FlipAnimation(cardFront, cardBack)
                : new FlipAnimation(cardBack, cardFront);
        rootLayout.startAnimation(flipAnimation);
        isFront = !isFront;
    }

    private void startAutoScroll() {
        final int SCROLL_SPEED = 30;
        final int SCROLL_DELAY = 40;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoScrollHandler.removeCallbacksAndMessages(null);
        if (myVideo != null) myVideo.stopPlayback();
    }
}
