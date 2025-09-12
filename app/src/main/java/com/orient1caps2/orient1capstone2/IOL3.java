package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOL3 extends AppCompatActivity {

    private static final int AUTO_SCROLL_INTERVAL_MS = 3000;
    private static final float MAX_SCALE = 1.25f;
    private static final float MIN_SCALE = 0.85f;
    private static final float SCALE_RANGE = MAX_SCALE - MIN_SCALE;
    private static final float BASE_ALPHA = 0.6f;
    private static final float ALPHA_RANGE = 0.4f;

    // Handlers and runnables
    private final Handler autoScrollHandler = new Handler(Looper.getMainLooper());
    // Sample data arrays
    private final String[] titles = {
            "1. Keep studies as your first priority",
            "2. Set SMART Goals",
            "3. Time Management",
            "4. Take part in Classroom & School Activities",
            "5. Pay attention to What Teachers teach",
            "6. Study in a Group"
    };
    private final String[] descriptions = {
            "A good student never ignores his studies and keep their studies on the topmost priority list.",
            "Setting a smart goal is important for your future bright. This helps to achieve what you want to be in life.",
            "Time Management is a must in a students' life. The purpose of time management is to enable them to do more and better work in less time.",
            "Participating in various activities conducted in the school makes you an active member. Stay active in daily classroom activities.",
            "Paying attention in class is absolutely necessary to become successful in life as a student. Keep your eyes & ears open to what teacher is teaching.",
            "Studying with peers can help reinforce learning through discussion and explanation."
    };
    private final int[] imageRes = {
            R.drawable.image17,
            R.drawable.image18,
            R.drawable.image19,
            R.drawable.image20,
            R.drawable.image21,
            R.drawable.image23,
            R.drawable.image24,
            R.drawable.image25,
            R.drawable.image26,
            R.drawable.image27
    };
    private Runnable autoScrollRunnable1;
    private Runnable autoScrollRunnable2;
    // RecyclerViews for infinite carousels
    private RecyclerView infiniteCarousel1;
    private RecyclerView infiniteCarousel2;
    // Dropdown views
    private LinearLayout[] dropdownContents;
    private ImageView[] dropdownIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iol3);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialize dropdown arrays
        dropdownContents = new LinearLayout[]{
                findViewById(R.id.dropdownContent1),
                findViewById(R.id.dropdownContent2),
                findViewById(R.id.dropdownContent3),
                findViewById(R.id.dropdownContent4),
                findViewById(R.id.dropdownContent5),
                findViewById(R.id.dropdownContent6)
        };

        dropdownIcons = new ImageView[]{
                findViewById(R.id.dropdownIcon1),
                findViewById(R.id.dropdownIcon2),
                findViewById(R.id.dropdownIcon3),
                findViewById(R.id.dropdownIcon4),
                findViewById(R.id.dropdownIcon5),
                findViewById(R.id.dropdownIcon6)
        };

        // Setup dropdown toggles
        setupDropdownToggles();

        RecyclerView recycler1 = findViewById(R.id.recyclerView);
        recycler1.setLayoutManager(new LinearLayoutManager(this));

        List<InfoItem> items = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            items.add(new InfoItem(imageRes[i], titles[i], descriptions[i]));
        }

        InfoAdapter adapter1 = new InfoAdapter(items);
        recycler1.setAdapter(adapter1);

        TextView descriptionText7 = findViewById(R.id.description4);
        String html8 = "<b>Student life</b> in school helps them start learning about everything. They learn academic knowledge, manners, good behaviors, discipline, punctuality, and more";
        descriptionText7.setText(Html.fromHtml(html8, Html.FROM_HTML_MODE_LEGACY));

        List<Integer> myDataList = Arrays.asList(
                R.drawable.iiiimage1,
                R.drawable.iiiimage2,
                R.drawable.iiiimage3,
                R.drawable.iiiimage4,
                R.drawable.iiiimage5,
                R.drawable.iiiimage6
        );

        RecyclerView recycler = findViewById(R.id.carouselRecycler);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(lm);
        CarouselAdapter1 adapter = new CarouselAdapter1(myDataList);
        recycler.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler);
        recycler.setOverScrollMode(View.OVER_SCROLL_NEVER);

        setupWindowInsets();
        setupNavigationButtons();
        setupFirstCarousel();
        setupSecondCarousel();
        setupInfiniteCarousels();
        setupSecondCarousel1();
    }

    private void setupDropdownToggles() {
        for (int i = 0; i < dropdownContents.length; i++) {
            final int index = i;
            View parentCard = (View) dropdownContents[i].getParent().getParent();

            parentCard.setOnClickListener(v -> toggleDropdown(index));

            // Also make the icon clickable
            dropdownIcons[i].setOnClickListener(v -> toggleDropdown(index));
        }
    }

    private void toggleDropdown(int index) {
        boolean isVisible = dropdownContents[index].getVisibility() == View.VISIBLE;

        if (isVisible) {
            dropdownContents[index].setVisibility(View.GONE);
            dropdownIcons[index].setRotation(0);
        } else {
            dropdownContents[index].setVisibility(View.VISIBLE);
            dropdownIcons[index].setRotation(180);
        }
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupNavigationButtons() {
        // Home button
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            startActivity(new Intent(IOL3.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        // Back button
        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void setupFirstCarousel() {
        List<Integer> images = Arrays.asList(R.drawable.child, R.drawable.teen, R.drawable.old);
        List<String> texts = Arrays.asList("", "", "");

        setupCarousel(
                R.id.carouselRecyclerView,
                new CarouselAdapter(images, texts),
                images.size()
        );
    }

    private void setupSecondCarousel() {
        List<Integer> images = Arrays.asList(
                R.drawable.image12, R.drawable.image13, R.drawable.image14,
                R.drawable.image15, R.drawable.image16
        );
        List<String> texts = Arrays.asList("", "", "", "", "");

        setupCarousel(
                R.id.carouselRecyclerView1,
                new CarouselAdapter(images, texts),
                images.size()
        );
    }

    private void setupSecondCarousel1() {
        List<Integer> images = Arrays.asList(
                R.drawable.image28, R.drawable.image29, R.drawable.image30,
                R.drawable.image31, R.drawable.image32, R.drawable.image33, R.drawable.image34, R.drawable.image35,
                R.drawable.image36, R.drawable.image37
        );
        List<String> texts = Arrays.asList("Health Issues", "Academic Pressure", "Mental Health Issues", "Lack of Resources", "Financial Constraints", "Lack of Motivation", "Social Issues", "Time Management", "Technological Barriers", "Family Problems");

        setupCarousel(
                R.id.carouselRecyclerView2,
                new CarouselAdapter(images, texts),
                images.size()
        );
    }

    private void setupCarousel(int recyclerViewId, CarouselAdapter adapter, int itemCount) {
        RecyclerView carouselRecyclerView = findViewById(recyclerViewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        carouselRecyclerView.setLayoutManager(layoutManager);
        carouselRecyclerView.setAdapter(adapter);
        new LinearSnapHelper().attachToRecyclerView(carouselRecyclerView);

        // Center position for looping feel
        int initialPosition = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % itemCount);
        carouselRecyclerView.scrollToPosition(initialPosition);

        carouselRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                applyCarouselScalingEffects(recyclerView);
            }
        });
    }

    private void applyCarouselScalingEffects(RecyclerView recyclerView) {
        float centerX = recyclerView.getWidth() / 2f;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View child = recyclerView.getChildAt(i);
            float childCenterX = (child.getLeft() + child.getRight()) / 2f;
            float distance = Math.abs(centerX - childCenterX);
            float normalizedDistance = centerX > 0 ? Math.min(distance / centerX, 1f) : 0f;
            float scale = MAX_SCALE - (normalizedDistance * SCALE_RANGE);

            child.setScaleX(scale);
            child.setScaleY(scale);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                child.setTranslationZ(scale * 8f);
            }

            float alpha = BASE_ALPHA + (ALPHA_RANGE * (scale - MIN_SCALE) / SCALE_RANGE);
            child.setAlpha(alpha);
        }
    }

    private void setupInfiniteCarousels() {
        // First infinite carousel
        List<Integer> images1 = Arrays.asList(
                R.drawable.image1, R.drawable.image2, R.drawable.image3,
                R.drawable.image4, R.drawable.image5
        );

        infiniteCarousel1 = findViewById(R.id.imageCarousel);
        setupInfiniteCarousel(infiniteCarousel1, images1);
        autoScrollRunnable1 = createAutoScrollRunnable(infiniteCarousel1);
        autoScrollHandler.postDelayed(autoScrollRunnable1, AUTO_SCROLL_INTERVAL_MS);

        // Second infinite carousel
        List<Integer> images2 = Arrays.asList(
                R.drawable.image6, R.drawable.image7, R.drawable.image8,
                R.drawable.image9, R.drawable.image10, R.drawable.image11
        );

        infiniteCarousel2 = findViewById(R.id.imageCarousel1);
        setupInfiniteCarousel(infiniteCarousel2, images2);
        autoScrollRunnable2 = createAutoScrollRunnable(infiniteCarousel2);
        autoScrollHandler.postDelayed(autoScrollRunnable2, AUTO_SCROLL_INTERVAL_MS);
    }

    private void setupInfiniteCarousel(RecyclerView carousel, List<Integer> images) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        carousel.setLayoutManager(layoutManager);
        carousel.setAdapter(new InfiniteImageAdapter(this, images));

        new LinearSnapHelper().attachToRecyclerView(carousel);

        // Start near middle so both directions work
        int mid = Integer.MAX_VALUE / 2;
        int offset = mid % images.size();
        carousel.scrollToPosition(mid - offset);
    }

    private Runnable createAutoScrollRunnable(RecyclerView carousel) {
        return new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = (LinearLayoutManager) carousel.getLayoutManager();
                if (layoutManager != null) {
                    int firstVisible = layoutManager.findFirstVisibleItemPosition();
                    if (firstVisible != RecyclerView.NO_POSITION) {
                        carousel.smoothScrollToPosition(firstVisible + 1);
                    }
                }
                autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL_MS);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop auto-scroll to avoid leaks
        if (autoScrollRunnable1 != null) {
            autoScrollHandler.removeCallbacks(autoScrollRunnable1);
        }
        if (autoScrollRunnable2 != null) {
            autoScrollHandler.removeCallbacks(autoScrollRunnable2);
        }
    }
}