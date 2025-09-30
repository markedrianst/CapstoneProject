package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class IOEAL5 extends AppCompatActivity {

    private static final int CARD_COUNT = 11;
    private final CardView[] cardFronts = new CardView[CARD_COUNT];
    private final CardView[] cardBacks = new CardView[CARD_COUNT];
    private final boolean[] isFrontVisible = new boolean[CARD_COUNT];

    // Dropdown support
    private LinearLayout[] dropdownContents;
    private ImageView[] dropdownIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ioeal5);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialize dropdowns (Make sure both are declared in XML with correct IDs)
        dropdownContents = new LinearLayout[] {
                findViewById(R.id.dropdownContent0),
                findViewById(R.id.dropdownContent1)
        };

        dropdownIcons = new ImageView[] {
                findViewById(R.id.dropdownIcon1),
                findViewById(R.id.dropdownIcon2)
        };

        // Set up toggle functionality
        setupDropdownToggles();

        // Setup cards
        initializeCards();

        // Setup navigation
        setupNavigationButtons();

        // Load example texts
        setupExampleText();

        // Window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupDropdownToggles() {
        for (int i = 0; i < dropdownContents.length; i++) {
            final int index = i;
            View parentCard = (View) dropdownContents[i].getParent().getParent();

            parentCard.setOnClickListener(v -> toggleDropdown(index));
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

    private void initializeCards() {
        int[] frontIds = {R.id.card_front, R.id.card_front1, R.id.card_front2,
                R.id.card_front3, R.id.card_front4, R.id.card_front5,
                R.id.card_front6, R.id.card_front7, R.id.card_front8, R.id.card_front9, R.id.card_front10};

        int[] backIds = {R.id.card_back, R.id.card_back1, R.id.card_back2,
                R.id.card_back3, R.id.card_back4, R.id.card_back5,
                R.id.card_back6, R.id.card_back7, R.id.card_back8, R.id.card_back9, R.id.card_back10};

        int[] containerIds = {R.id.flip_container, R.id.flip_container1, R.id.flip_container2,
                R.id.flip_container3, R.id.flip_container4, R.id.flip_container5,
                R.id.flip_container6, R.id.flip_container7, R.id.flip_container8, R.id.flip_container9, R.id.flip_container10};

        for (int i = 0; i < CARD_COUNT; i++) {
            cardFronts[i] = findViewById(frontIds[i]);
            cardBacks[i] = findViewById(backIds[i]);
            isFrontVisible[i] = true;

            final int index = i;
            cardFronts[i].setOnClickListener(v -> flipCard(index, containerIds[index]));
            cardBacks[i].setOnClickListener(v -> flipCard(index, containerIds[index]));
        }
    }

    private void flipCard(int cardIndex, int containerId) {
        View rootLayout = findViewById(containerId);
        FlipAnimation flipAnimation = isFrontVisible[cardIndex]
                ? new FlipAnimation(cardFronts[cardIndex], cardBacks[cardIndex])
                : new FlipAnimation(cardBacks[cardIndex], cardFronts[cardIndex]);

        rootLayout.startAnimation(flipAnimation);
        isFrontVisible[cardIndex] = !isFrontVisible[cardIndex];
    }

    private void setupNavigationButtons() {
        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(IOEAL5.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void setupExampleText() {
        TextView description6 = findViewById(R.id.descriptionText6);
        String htmlText6 = "&emsp;&emsp;A God-loving educational community with passion for truth and compassion for humanity.";
        description6.setText(Html.fromHtml(htmlText6, Html.FROM_HTML_MODE_LEGACY));

        TextView description7 = findViewById(R.id.descriptionText7);
        String htmlText7 = "&emsp;&emsp;We commit ourselves to promote the truth and holistically transform persons for the service of humanity.";
        description7.setText(Html.fromHtml(htmlText7, Html.FROM_HTML_MODE_LEGACY));

        TextView examplesTextView = findViewById(R.id.examplesTextView);
        TextView examplesTextView1 = findViewById(R.id.examplesTextView1);
        TextView examplesTextView2 = findViewById(R.id.examplesTextView2);
        TextView examplesTextView3 = findViewById(R.id.examplesTextView3);
        TextView examplesTextView4 = findViewById(R.id.examplesTextView4);

        List<String> examples = Arrays.asList(
                "Headaches: Stress can trigger tension headaches or migraines.",
                "Fatigue: Feeling tired or drained of energy, even after rest.",
                "Muscle tension or pain: Stress causes muscles to tighten.",
                "Stomach problems: Stress can lead to indigestion or nausea."
        );

        List<String> examples1 = Arrays.asList(
                "Irritability or anger: People may be more easily frustrated.",
                "Anxiety or nervousness: Constant worry or dread.",
                "Sadness or depression: Feeling down or hopeless.",
                "Feeling overwhelmed: Everything feels too much to handle."
        );

        List<String> stressSymptoms = Arrays.asList(
                "Difficulty concentrating: Mind is preoccupied.",
                "Memory problems: Forgetfulness due to stress.",
                "Racing thoughts: Uncontrollable overthinking.",
                "Indecisiveness: Trouble making decisions.",
                "Negative thinking: Focus on worst-case scenarios."
        );

        List<String> stressSymptoms1 = Arrays.asList(
                "Difficulty concentrating",
                "Memory problems",
                "Racing thoughts",
                "Indecisiveness",
                "Negative thinking",
                "Changes in eating habits",
                "Sleep disturbances",
                "Social withdrawal",
                "Increased substance use"
        );

        List<String> stressSymptoms2 = Arrays.asList(
                "Stress is natural but must be managed.",
                "Use healthy coping strategies to maintain well-being."
        );

        examplesTextView.setText(buildBulletList(examples));
        examplesTextView1.setText(buildBulletList(examples1));
        examplesTextView2.setText(buildBulletList(stressSymptoms));
        examplesTextView3.setText(buildBulletList(stressSymptoms1));
        examplesTextView4.setText(buildBulletList(stressSymptoms2));
    }

    private String buildBulletList(List<String> items) {
        StringBuilder builder = new StringBuilder();
        for (String item : items) {
            builder.append("• ").append(item).append("\n");
        }
        return builder.toString();
    }
}
