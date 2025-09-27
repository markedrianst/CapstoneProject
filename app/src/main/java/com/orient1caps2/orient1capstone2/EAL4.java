package com.orient1caps2.orient1capstone2;

import static android.graphics.drawable.GradientDrawable.OVAL;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EAL4 extends AppCompatActivity {

    // Carousel constants
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.85f;
    private static final float SCALE_RANGE = MAX_SCALE - MIN_SCALE;
    private static final float BASE_ALPHA = 0.7f;
    private static final float ALPHA_RANGE = 1.0f - BASE_ALPHA;

    private RecyclerView carouselRecyclerView;
    private CarouselAdapter2 adapter;
    private TabLayout dotsIndicator;
    private int itemCount;
    private float centerX;

    // Expandable section variables
    private boolean isFormalEducationExpanded = false;
    private boolean isInformalEducationExpanded = false;
    private boolean isNonFormalEducationExpanded = false;
    private boolean isAccessInclusionExpanded = false;
    private boolean isTeachingMethodsExpanded = false;
    private boolean isCurriculumContentExpanded = false;
    private boolean isTechnologyRoleExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eal4);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setupButtons();
        setupTextViews();
        setupCarousel();
        setupCarousel1();
        setupExpandableSections(); // Add this line
    }

    private void setupButtons() {
        // Home button
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            startActivity(new Intent(EAL4.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        // Back button
        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void setupExpandableSections() {
        // Formal Education
        View formalEducationArrow = findViewById(R.id.formalEducationArrow);
        View formalEducationHeader = (View) formalEducationArrow.getParent().getParent();
        formalEducationHeader.setOnClickListener(v -> {
            isFormalEducationExpanded = !isFormalEducationExpanded;
            toggleSection(
                    R.id.formalEducationContent,
                    R.id.formalEducationArrow,
                    isFormalEducationExpanded
            );
        });

        // Informal Education
        View informalEducationArrow = findViewById(R.id.informalEducationArrow);
        View informalEducationHeader = (View) informalEducationArrow.getParent().getParent();
        informalEducationHeader.setOnClickListener(v -> {
            isInformalEducationExpanded = !isInformalEducationExpanded;
            toggleSection(
                    R.id.informalEducationContent,
                    R.id.informalEducationArrow,
                    isInformalEducationExpanded
            );
        });

        // Non-formal Education
        View nonFormalEducationArrow = findViewById(R.id.nonFormalEducationArrow);
        View nonFormalEducationHeader = (View) nonFormalEducationArrow.getParent().getParent();
        nonFormalEducationHeader.setOnClickListener(v -> {
            isNonFormalEducationExpanded = !isNonFormalEducationExpanded;
            toggleSection(
                    R.id.nonFormalEducationContent,
                    R.id.nonFormalEducationArrow,
                    isNonFormalEducationExpanded
            );
        });

        // Evolution of Education sections
        setupEvolutionSections();
        setupEvolutionSections1();
    }

    private void setupEvolutionSections() {
        // Access and Inclusion
        View accessInclusionArrow = findViewById(R.id.accessInclusionArrow);
        View accessInclusionHeader = (View) accessInclusionArrow.getParent();
        accessInclusionHeader.setOnClickListener(v -> {
            isAccessInclusionExpanded = !isAccessInclusionExpanded;
            toggleSection(
                    R.id.accessInclusionContent,
                    R.id.accessInclusionArrow,
                    isAccessInclusionExpanded
            );
        });

        // Teaching Methods
        View teachingMethodsArrow = findViewById(R.id.teachingMethodsArrow);
        View teachingMethodsHeader = (View) teachingMethodsArrow.getParent();
        teachingMethodsHeader.setOnClickListener(v -> {
            isTeachingMethodsExpanded = !isTeachingMethodsExpanded;
            toggleSection(
                    R.id.teachingMethodsContent,
                    R.id.teachingMethodsArrow,
                    isTeachingMethodsExpanded
            );
        });

        // Curriculum Content
        View curriculumContentArrow = findViewById(R.id.curriculumContentArrow);
        View curriculumContentHeader = (View) curriculumContentArrow.getParent();
        curriculumContentHeader.setOnClickListener(v -> {
            isCurriculumContentExpanded = !isCurriculumContentExpanded;
            toggleSection(
                    R.id.curriculumContentContent,
                    R.id.curriculumContentArrow,
                    isCurriculumContentExpanded
            );
        });

        // Role of Technology
        View technologyRoleArrow = findViewById(R.id.technologyRoleArrow);
        View technologyRoleHeader = (View) technologyRoleArrow.getParent();
        technologyRoleHeader.setOnClickListener(v -> {
            isTechnologyRoleExpanded = !isTechnologyRoleExpanded;
            toggleSection(
                    R.id.technologyRoleContent,
                    R.id.technologyRoleArrow,
                    isTechnologyRoleExpanded
            );
        });
    }

    private boolean isVisualExpanded = false;
    private boolean isAuralExpanded = false;
    private boolean isVerbalExpanded = false;
    private boolean isPhysicalExpanded = false;
    private boolean isLogicalExpanded = false;
    private boolean isSocialExpanded = false;
    private boolean isSolitaryExpanded = false;
    private boolean isNaturalisticExpanded = false;

    private void setupEvolutionSections1() {
        // Visual (spatial)
        View visualArrow = findViewById(R.id.visualArrow);
        View visualHeader = (View) visualArrow.getParent();
        visualHeader.setOnClickListener(v -> {
            isVisualExpanded = !isVisualExpanded;
            toggleSection(R.id.visualContent, R.id.visualArrow, isVisualExpanded);
        });

        // Aural (auditory-musical)
        View auralArrow = findViewById(R.id.auralArrow);
        View auralHeader = (View) auralArrow.getParent();
        auralHeader.setOnClickListener(v -> {
            isAuralExpanded = !isAuralExpanded;
            toggleSection(R.id.auralContent, R.id.auralArrow, isAuralExpanded);
        });

        // Verbal (linguistic)
        View verbalArrow = findViewById(R.id.verbalArrow);
        View verbalHeader = (View) verbalArrow.getParent();
        verbalHeader.setOnClickListener(v -> {
            isVerbalExpanded = !isVerbalExpanded;
            toggleSection(R.id.verbalContent, R.id.verbalArrow, isVerbalExpanded);
        });

        // Physical (kinesthetic)
        View physicalArrow = findViewById(R.id.physicalArrow);
        View physicalHeader = (View) physicalArrow.getParent();
        physicalHeader.setOnClickListener(v -> {
            isPhysicalExpanded = !isPhysicalExpanded;
            toggleSection(R.id.physicalContent, R.id.physicalArrow, isPhysicalExpanded);
        });

        // Logical (mathematical)
        View logicalArrow = findViewById(R.id.logicalArrow);
        View logicalHeader = (View) logicalArrow.getParent();
        logicalHeader.setOnClickListener(v -> {
            isLogicalExpanded = !isLogicalExpanded;
            toggleSection(R.id.logicalContent, R.id.logicalArrow, isLogicalExpanded);
        });

        // Social (interpersonal)
        View socialArrow = findViewById(R.id.socialArrow);
        View socialHeader = (View) socialArrow.getParent();
        socialHeader.setOnClickListener(v -> {
            isSocialExpanded = !isSocialExpanded;
            toggleSection(R.id.socialContent, R.id.socialArrow, isSocialExpanded);
        });

        // Solitary (intrapersonal)
        View solitaryArrow = findViewById(R.id.solitaryArrow);
        View solitaryHeader = (View) solitaryArrow.getParent();
        solitaryHeader.setOnClickListener(v -> {
            isSolitaryExpanded = !isSolitaryExpanded;
            toggleSection(R.id.solitaryContent, R.id.solitaryArrow, isSolitaryExpanded);
        });

        // Naturalistic
        View naturalisticArrow = findViewById(R.id.naturalisticArrow);
        View naturalisticHeader = (View) naturalisticArrow.getParent();
        naturalisticHeader.setOnClickListener(v -> {
            isNaturalisticExpanded = !isNaturalisticExpanded;
            toggleSection(R.id.naturalisticContent, R.id.naturalisticArrow, isNaturalisticExpanded);
        });
    }

    private void toggleSection(int contentId, int arrowId, boolean isExpanded) {
        View content = findViewById(contentId);
        ImageView arrow = findViewById(arrowId);

        if (isExpanded) {
            content.setVisibility(View.VISIBLE);
            arrow.setRotation(180);
        } else {
            content.setVisibility(View.GONE);
            arrow.setRotation(0);
        }
    }

    private void setupTextViews() {
        // Set HTML formatted text for all TextViews
        setHtmlText(R.id.descriptionText6, "This is the structured education we typically<b> receive in schools and colleges</b>. It follows a specific curriculum with defined goals and assessments.");
        setHtmlText(R.id.descriptionText7, "This happens outside of traditional classrooms and often <b>occurs naturally </b>through life experiences, interactions with others, and  self-directed learning.");
        setHtmlText(R.id.descriptionText8, "Learning resulting from daily activities\nrelated to work, family or leisure. It is not organized or structured in terms of objectives, time or learning support.");
        setHtmlText(R.id.descriptionText9, "Why <b>Education</b> is important?");
        setHtmlText(R.id.descriptionText10, "<b>Education</b> is <b>one of the most powerful things</b> in life. It allows us to find the meaning behind everything and helps improve lives in a massive way. <b>Education</b> gives us an understanding of the world around us and offers us an opportunity to <b>use</b> that knowledge <b>wisely</b>.");
    }

    private void setHtmlText(int textViewId, String html) {
        TextView textView = findViewById(textViewId);
        textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
    }

    private void setupCarousel() {
        List<Integer> images = Arrays.asList(R.drawable.a1, R.drawable.a2, R.drawable.a2);
        List<String> texts = Arrays.asList("", "", "");
        itemCount = images.size();

        // Initialize views
        carouselRecyclerView = findViewById(R.id.carouselRecyclerView);
        dotsIndicator = findViewById(R.id.dotsIndicator);

        // Setup RecyclerView with infinite scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }
        };
        carouselRecyclerView.setLayoutManager(layoutManager);

        // Use a large number to simulate infinite scrolling
        adapter = new CarouselAdapter2(images, texts);
        carouselRecyclerView.setAdapter(adapter);

        // Setup snap helper for smooth snapping
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(carouselRecyclerView);

        // Setup dots indicator
        setupDotsIndicator();

        // Add scroll listener for carousel effects
        carouselRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    updateSelectedDot();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                applyCarouselEffects();
            }
        });

        // Initialize center position after layout
        carouselRecyclerView.post(() -> {
            centerX = carouselRecyclerView.getWidth() / 2f;
            // Start at a middle position for infinite scrolling
            int initialPosition = Integer.MAX_VALUE / 2;
            carouselRecyclerView.scrollToPosition(initialPosition);
            applyCarouselEffects();
        });
    }

    private void setupCarousel1() {
        List<Integer> images = Arrays.asList(
                R.drawable.musical,
                R.drawable.intrapersonal,
                R.drawable.naturalist,
                R.drawable.verbal,
                R.drawable.interpersonal,
                R.drawable.spatial,
                R.drawable.kinesthetic,
                R.drawable.logical,
                R.drawable.existential
        );

        List<String> titles = Arrays.asList(
                "Musical",
                "Intrapersonal",
                "Naturalist",
                "Verbal",
                "Interpersonal",
                "Spatial",
                "Kinesthetic",
                "Logical",
                "Existential"
        );

        List<String> descriptions = Arrays.asList(
                "People who have strong musical intelligence are good at thinking in patterns, rhythms, and sounds.",
                "Individuals who are strong in intrapersonal intelligence are good at being aware of their own emotional states, feelings, and motivations.",
                "Individuals who are high in this type of intelligence are more in tune with nature and are often interested in nurturing, exploring the environment, and learning about other species.",
                "People who are strong in linguistic-verbal intelligence are able to use words well, both when writing and speaking.",
                "Those who have strong interpersonal intelligence are good at understanding and interacting with other people.",
                "People who are strong in visual-spatial intelligence are good at visualizing things.",
                "Those who have high bodily kinesthetic intelligence are said to be good at body movement, performing actions, and physical control.",
                "People who are strong in logical mathematical intelligence are good at reasoning, recognizing patterns, and logically analyzing problems.",
                "Existential intelligence as an ability to explore into deeper questions about life and existence."
        );

        // Initialize the second RecyclerView
        RecyclerView carousel2RecyclerView = findViewById(R.id.carouselRecyclerView2);

        // Setup LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        carousel2RecyclerView.setLayoutManager(layoutManager);

        // Create and set adapter with proper CardItems
        List<CardAdapter2.CardItem> cardItems = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            cardItems.add(new CardAdapter2.CardItem(
                    images.get(i),
                    titles.get(i),
                    descriptions.get(i)
            ));
        }

        CardAdapter2 adapter = new CardAdapter2(cardItems);
        carousel2RecyclerView.setAdapter(adapter);

        // Setup snap helper
        new LinearSnapHelper().attachToRecyclerView(carousel2RecyclerView);

        // Initialize center position for infinite scrolling
        carousel2RecyclerView.post(() -> {
            int middlePosition = Integer.MAX_VALUE / 2;
            carousel2RecyclerView.scrollToPosition(middlePosition);
        });
    }

    private void setupDotsIndicator() {
        dotsIndicator.removeAllTabs();
        for (int i = 0; i < itemCount; i++) {
            TabLayout.Tab tab = dotsIndicator.newTab();
            tab.setCustomView(createDotView(i == 0));
            dotsIndicator.addTab(tab);
        }
    }

    private View createDotView(boolean selected) {
        int sizePx = dpToPx(selected ? 8 : 6);
        View dot = new View(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizePx, sizePx);
        lp.setMargins(dpToPx(2), 0, dpToPx(2), 0);
        dot.setLayoutParams(lp);

        GradientDrawable bg = new GradientDrawable();
        bg.setShape(OVAL);
        bg.setColor(selected ? 0xFF333333 : 0xFFBBBBBB);
        dot.setBackground(bg);

        return dot;
    }

    private void updateSelectedDot() {
        View centerView = findCenterView();
        if (centerView == null) return;

        int centerPos = carouselRecyclerView.getLayoutManager().getPosition(centerView);
        if (centerPos == RecyclerView.NO_POSITION) return;

        int logicalPos = centerPos % itemCount;
        for (int i = 0; i < dotsIndicator.getTabCount(); i++) {
            TabLayout.Tab tab = dotsIndicator.getTabAt(i);
            if (tab != null && tab.getCustomView() != null) {
                updateDotView(tab.getCustomView(), i == logicalPos);
            }
        }
    }

    private View findCenterView() {
        if (carouselRecyclerView.getLayoutManager() == null) return null;

        LinearLayoutManager layoutManager = (LinearLayoutManager) carouselRecyclerView.getLayoutManager();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        View closestToCenter = null;
        float closestDistance = Float.MAX_VALUE;

        for (int i = firstVisible; i <= lastVisible; i++) {
            View child = layoutManager.findViewByPosition(i);
            if (child != null) {
                float childCenterX = (child.getLeft() + child.getRight()) / 2f;
                float distance = Math.abs(centerX - childCenterX);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestToCenter = child;
                }
            }
        }
        return closestToCenter;
    }

    private void updateDotView(View dot, boolean isSelected) {
        int sizePx = dpToPx(isSelected ? 8 : 6);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dot.getLayoutParams();
        lp.width = sizePx;
        lp.height = sizePx;
        dot.setLayoutParams(lp);

        GradientDrawable bg = (GradientDrawable) dot.getBackground();
        if (bg != null) {
            bg.setColor(isSelected ? 0xFF333333 : 0xFFBBBBBB);
        }
    }

    private void applyCarouselEffects() {
        if (centerX == 0) return;

        LinearLayoutManager layoutManager = (LinearLayoutManager) carouselRecyclerView.getLayoutManager();
        if (layoutManager == null) return;

        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        for (int i = firstVisible; i <= lastVisible; i++) {
            View child = layoutManager.findViewByPosition(i);
            if (child != null) {
                float childCenterX = (child.getLeft() + child.getRight()) / 2f;
                float distance = Math.abs(centerX - childCenterX);
                float normalizedDistance = Math.min(distance / centerX, 1f);
                float scale = MAX_SCALE - (normalizedDistance * SCALE_RANGE);

                child.setScaleX(scale);
                child.setScaleY(scale);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    child.setTranslationZ(scale * 8f);
                }

                float alpha = BASE_ALPHA + (ALPHA_RANGE * (scale - MIN_SCALE) / SCALE_RANGE);
                child.setAlpha(alpha);
            }
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}