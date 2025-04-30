package com.example.mediassist.activities.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.mediassist.R;
public class OnboardingFragment3 extends Fragment {
    private ImageView image;
    private boolean animationPlayed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onboarding3, container, false);
        image = rootView.findViewById(R.id.onboarding_image);

        // Initial state
        resetAnimationState();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!animationPlayed) {
            startAnimation();
            animationPlayed = true;
        }
    }

    private void resetAnimationState() {
        image.setScaleX(0.8f);
        image.setScaleY(0.8f);
        image.setAlpha(0f);
    }

    private void startAnimation() {
        image.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(1000)
                .setStartDelay(200)
                .start();
    }
}