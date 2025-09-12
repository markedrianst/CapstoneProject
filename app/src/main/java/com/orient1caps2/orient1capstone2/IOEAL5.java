package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ioeal5);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Parent layout where items will be added
        LinearLayout container = findViewById(R.id.container);

        // Sample data (10 items)
        String[] titles = {"1. Identify Your Stressors", "2. Time Management", "3. Deep Breathing Exercises", "4. Physical Activity", "5. Healthy Lifestyle Choices", "6. Hobbies and Leisure Activities", "7. Journaling", "8. Gratitude Practice", "9. Social Support", "10. Professional Help"};
        String[] contents = {"Identify and limit exposure to stressors, whether they are people, environments, or activities that trigger stress.",
                "Prioritize tasks and create a schedule to manage your time effectively. Break large tasks into smaller, manageable steps to avoid feeling overwhelmed.",
                "Focus on your breath to calm your mind and reduce anxiety. Try inhaling deeply through your nose for four counts, holding for four counts, and exhaling through your mouth for four counts.",
                "Engage in regular exercise, whether it’s walking, jogging, yoga, or dancing. Physical activity releases endorphins, which improve mood and reduce stress.",
                "Maintain a balanced diet, get enough sleep, and limit alcohol and caffeine intake. These factors significantly affect your stress levels.",
                "Engage in activities you enjoy, such as reading, gardening, or crafting. Taking time for hobbies can help you unwind and recharge.",
                "Write down your thoughts and feelings to help process emotions and gain clarity on stressful situations.",
                "Keep a gratitude journal where you list things you’re thankful for. Focusing on positive aspects of your life can shift your mindset and reduce stress.",
                "Connect with friends, family, or support groups. Talking about your feelings can provide relief and different perspectives on your situation.",
                "If stress becomes overwhelming, consider talking to a mental health professional. Therapy can provide valuable tools and coping strategies."};
        int[] imageIds = {R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5,
                R.drawable.s6, R.drawable.s7, R.drawable.s8, R.drawable.s9, R.drawable.s10};

        // Loop through all items
        for (int i = 0; i < titles.length; i++) {
            // Inflate the single XML layout
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_tip, container, false);

            // Set data
            TextView tvTitle = itemView.findViewById(R.id.tvTitle);
            TextView tvContent = itemView.findViewById(R.id.tvContent);
            ImageView imgTip = itemView.findViewById(R.id.imgTip);

            tvTitle.setText(titles[i]);
            tvContent.setText(contents[i]);
            imgTip.setImageResource(imageIds[i]);

            // Add to container
            container.addView(itemView);
        }


        TextView examplesTextView = findViewById(R.id.examplesTextView);

        TextView examplesTextView1 = findViewById(R.id.examplesTextView1);

        TextView examplesTextView2 = findViewById(R.id.examplesTextView2);
        TextView examplesTextView3 = findViewById(R.id.examplesTextView3);
        TextView examplesTextView4 = findViewById(R.id.examplesTextView4);

        List<String> stressSymptoms2 = Arrays.asList(
                "Stress is a natural response, but it needs to be managed effectively.",
                "Use healthy coping mechanisms to maintain balance and well-being."
        );

        StringBuilder builder4 = new StringBuilder();
        for (String example : stressSymptoms2) {
            builder4.append("• ").append(example).append("\n");
        }
        examplesTextView4.setText(builder4.toString());


        List<String> stressSymptoms1 = Arrays.asList(
                "Difficulty concentrating: Struggling to focus or complete tasks because the mind is preoccupied.",
                "Memory problems: Forgetfulness or difficulty recalling important information, especially under high stress.",
                "Racing thoughts: Constant, uncontrollable thoughts or overthinking.",
                "Indecisiveness: Having trouble making decisions or feeling paralyzed by choices due to stress.",
                "Negative thinking: Increased pessimism or a tendency to focus on worst-case scenarios, which can reinforce the stress.",
                "Changes in eating habits: Eating too much or too little, often as a way to cope with stress.",
                "Sleep disturbances: Difficulty falling asleep, staying asleep, or sleeping too much due to stress.",
                "Social withdrawal: Avoiding social situations or isolating oneself as a response to stress.",
                "Increased use of alcohol, drugs, or smoking: Stress may lead some individuals to rely on these substances to cope."
        );

        StringBuilder builder3 = new StringBuilder();
        for (String example : stressSymptoms1) {
            builder3.append("• ").append(example).append("\n");
        }
        examplesTextView3.setText(builder3.toString());


        List<String> stressSymptoms = Arrays.asList(
                "Difficulty concentrating: Struggling to focus or complete tasks because the mind is preoccupied.",
                "Memory problems: Forgetfulness or difficulty recalling important information, especially under high stress.",
                "Racing thoughts: Constant, uncontrollable thoughts or overthinking.",
                "Indecisiveness: Having trouble making decisions or feeling paralyzed by choices due to stress.",
                "Negative thinking: Increased pessimism or a tendency to focus on worst-case scenarios, which can reinforce the stress."
        );

        StringBuilder builder2 = new StringBuilder();
        for (String example : stressSymptoms) {
            builder2.append("• ").append(example).append("\n");
        }
        examplesTextView2.setText(builder2.toString());

        List<String> examples1 = Arrays.asList(
                "Irritability or anger: Stress often makes people more easily frustrated or prone to anger.",
                "Anxiety or nervousness: A constant sense of worry, fear, or dread can emerge under stress.",
                "Sadness or depression: Stress may lead to feeling down or hopeless, contributing to a depressed mood.",
                "Feeling overwhelmed: When everything feels too much to handle, causing difficulty in focusing or making decisions."
        );

        StringBuilder builder1 = new StringBuilder();
        for (String example : examples1) {
            builder1.append("• ").append(example).append("\n");
        }

        examplesTextView1.setText(builder1.toString());


        List<String> examples = Arrays.asList(
                "Headaches: Stress can trigger tension headaches or migraines.",
                "Fatigue: Feeling tired or drained of energy, even after rest.",
                "Muscle tension or pain: Stress causes muscles to tighten, leading to soreness, especially in the neck, shoulders, and back.",
                "Stomach problems: Stress can lead to indigestion, nausea, or stomach cramps."
        );

        StringBuilder builder = new StringBuilder();
        for (String example : examples) {
            builder.append("• ").append(example).append("\n");
        }

        examplesTextView.setText(builder.toString());


        initializeCards();
        setupNavigationButtons();

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

    private void setupNavigationButtons() {
        // Home button
        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(IOEAL5.this, MainActivity.class));
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

    private void flipCard(int cardIndex, int containerId) {
        View rootLayout = findViewById(containerId);
        FlipAnimation flipAnimation = isFrontVisible[cardIndex]
                ? new FlipAnimation(cardFronts[cardIndex], cardBacks[cardIndex])
                : new FlipAnimation(cardBacks[cardIndex], cardFronts[cardIndex]);

        rootLayout.startAnimation(flipAnimation);
        isFrontVisible[cardIndex] = !isFrontVisible[cardIndex];
    }
}