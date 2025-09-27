package com.orient1caps2.orient1capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Dctculture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dctculture);

        String[] philosophy = {
                "Union with God",
                "Community with others",
                "Harmony with creation"
        };

        String[] values = {
                "FIDES – \"Faith in God\"",
                "PATRIA – \"Love for country and fellowmen\"",
                "SAPIENTIA – \"Wisdom\""
        };

        // Descriptions
        TextView description = findViewById(R.id.descriptionText);
        String htmlText = "&emsp;&emsp;Founded on February 14, 1947 by Fr. Mariano M. Sablay. " +
                "It started with 35 students. <b>Fr. Mariano M. Sablay</b> was the Parish Priest of " +
                "San Nicolas de Tolentino of Capas, Tarlac in 1946.";
        description.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));

        TextView description1 = findViewById(R.id.descriptionText1);
        String htmlText1 = "&emsp;&emsp;In 1960, the Dominican Sisters assumed administration of the school with Sr. Rosalina Mirabueno, " +
                "O.P., as Principal. This transition, along with the completion of the main building," +
                " facilitated an increase in student enrollment and expansion of facilities. " +
                "In 1967, SNA was renamed Dominican School, in honor of St. Dominic, the founder of the Order of Preachers.";
        description1.setText(Html.fromHtml(htmlText1, Html.FROM_HTML_MODE_LEGACY));

        TextView description2 = findViewById(R.id.descriptionText2);
        String htmlText2 = "&emsp;&emsp;Became official on April 20, 1999. Recognized courses include Bachelor programs starting 1997 onward.";
        description2.setText(Html.fromHtml(htmlText2, Html.FROM_HTML_MODE_LEGACY));

        TextView description3 = findViewById(R.id.descriptionText3);
        String htmlText3 = "1. Emphasizes the total integral formation of the human person.<br><br>" +
                "2. Aims for union with God, community with others, and harmony with creation.";
        description3.setText(Html.fromHtml(htmlText3, Html.FROM_HTML_MODE_LEGACY));

        TextView description4 = findViewById(R.id.descriptionText4);
        String htmlText4 = "&emsp;&emsp;The Dominican College of Tarlac is a Catholic educational institution committed to the total integral formation of the human person with a deep sense of nationalism and global awareness. It aims to develop individuals who are:";
        description4.setText(Html.fromHtml(htmlText4, Html.FROM_HTML_MODE_LEGACY));

        TextView philoed = findViewById(R.id.philoed);
        StringBuilder philosophyText = new StringBuilder();
        for (String item : philosophy) {
            philosophyText.append("• ").append(item).append("\n");
        }
        philoed.setText(philosophyText.toString());

        TextView valuesTextView = findViewById(R.id.valuesTextView);
        StringBuilder valuesText = new StringBuilder();
        for (String value : values) {
            valuesText.append("• ").append(value).append("\n");
        }
        valuesTextView.setText(valuesText.toString());

        TextView description5 = findViewById(R.id.descriptionText5);
        String htmlText5 = "These virtues are represented by the three stars in the logo, symbolizing the institution's commitment to faith, love for country, and wisdom.";
        description5.setText(Html.fromHtml(htmlText5, Html.FROM_HTML_MODE_LEGACY));


        // Back button functionality
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Home button functionality
        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dctculture.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Setup dropdown functionality for all sections
        setupDropdown(R.id.historyHeader, R.id.historyContent, R.id.historyArrow);
        setupDropdown(R.id.gravissimumHeader, R.id.gravissimumContent, R.id.gravissimumArrow);
        setupDropdown(R.id.dropdownHeader, R.id.dropdownContent, R.id.dropdownArrow);
        setupDropdown(R.id.philosophyHeader, R.id.philosophyContent, R.id.philosophyArrow);
        setupDropdown(R.id.objectivesHeader, R.id.objectivesContent, R.id.objectivesArrow);
        setupDropdown(R.id.dctProgramsHeader, R.id.timelineContainer, R.id.dctProgramsArrow);

    }

    private void setupDropdown(int headerId, int contentId, int arrowId) {
        LinearLayout header = findViewById(headerId);
        final LinearLayout content = findViewById(contentId);
        final ImageView arrow = findViewById(arrowId);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.getVisibility() == View.GONE) {
                    expand(content);
                    arrow.setRotation(180f);
                } else {
                    collapse(content);
                    arrow.setRotation(0f);
                }
            }
        });
    }

    private void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
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
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}