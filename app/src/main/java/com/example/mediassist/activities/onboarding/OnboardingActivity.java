package com.example.mediassist.activities.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mediassist.R;
import com.example.mediassist.activities.WelcomeActivity;
import com.example.mediassist.adapters.OnboardingAdapter;

public class OnboardingActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Button btnNext, btnPrevious;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        // Initialisation des points
        LinearLayout dotsLayout = findViewById(R.id.dotsLayout);
        dots = new ImageView[3];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = (ImageView) dotsLayout.getChildAt(i);
        }

        viewPager.setAdapter(new OnboardingAdapter(this));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
                btnNext.setText(position == 2 ? "suivant" : "Suivant");

                // Afficher/masquer le bouton Précédent
                btnPrevious.setVisibility(position > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < 2) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                startActivity(new Intent(this, WelcomeActivity.class));
                finish();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        });
    }

    private void updateDots(int position) {
        for (int i = 0; i < dots.length; i++) {
            dots[i].setImageResource(
                    i == position ? R.drawable.dot_selected : R.drawable.dot_unselected
            );
        }
    }
}
