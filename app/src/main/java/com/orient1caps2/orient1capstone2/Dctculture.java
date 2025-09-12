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
                "FIDES – “Faith in God”",
                "PATRIA – “Love for country and fellowmen”",
                "SAPIENTIA – “Wisdom”"
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
        String htmlText3 = "1.Emphasizes the total integral formation of the human person.<br><br>" +
                "2.Aims for union with God, community with others, and harmony with creation.";
        description3.setText(Html.fromHtml(htmlText3, Html.FROM_HTML_MODE_LEGACY));

        TextView description4 = findViewById(R.id.descriptionText4);
        String htmlText4 = "&emsp;&emsp;<b>Philosophy of Education</b>: Dominican College of Tarlac believes in the ultimate goal of education, that is the total integral formation of the human person that would lead him to attain the purpose for which he was created, namely";
        description4.setText(Html.fromHtml(htmlText4, Html.FROM_HTML_MODE_LEGACY));

        TextView description5 = findViewById(R.id.descriptionText5);
        String htmlText5 = "";
        description5.setText(Html.fromHtml(htmlText5, Html.FROM_HTML_MODE_LEGACY));

        TextView description6 = findViewById(R.id.descriptionText6);
        String htmlText6 = "&emsp;&emsp;A God-loving educational community of servant leaders with passion for truth and compassion for humanity";
        description6.setText(Html.fromHtml(htmlText6, Html.FROM_HTML_MODE_LEGACY));

        TextView description7 = findViewById(R.id.descriptionText7);
        String htmlText7 = "&emsp;&emsp;We commit ourselves to the total formation of the person, promotion of truth and transformation of values for the service of humanity.";
        description7.setText(Html.fromHtml(htmlText7, Html.FROM_HTML_MODE_LEGACY));

        // Philosophy bullets
        TextView philoTextView = findViewById(R.id.philoed);
        StringBuilder philoBuilder = new StringBuilder();
        for (String item : philosophy) {
            philoBuilder.append("• ").append(item).append("\n");
        }
        philoTextView.setText(philoBuilder.toString());

        // Values list
        TextView valuesTextView = findViewById(R.id.valuesTextView);
        StringBuilder valuesBuilder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            valuesBuilder.append((i + 1)).append(". ").append(values[i]).append("\n");
        }
        valuesTextView.setText(valuesBuilder.toString());

        // Dropdown toggle (sisters section)
        LinearLayout dropdownContent = findViewById(R.id.dropdownContent);
        LinearLayout dropdownHeader = findViewById(R.id.dropdownHeader);
        ImageView dropdownArrow = findViewById(R.id.dropdownArrow);

        dropdownHeader.setOnClickListener(v -> {
            if (dropdownContent.getVisibility() == View.VISIBLE) {
                collapse(dropdownContent);
                dropdownArrow.animate().rotation(0).start(); // arrow down
            } else {
                expand(dropdownContent);
                dropdownArrow.animate().rotation(180).start(); // arrow up
            }
        });

        // Home button
        ImageButton homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Dctculture.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    // Smooth expand animation
    private void expand(View v) {
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

    // Smooth collapse animation
    private void collapse(View v) {
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
}
