package com.example.mediassist.activities.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.mediassist.R;

public class OnboardingFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // On "gonfle" le layout
        View rootView = inflater.inflate(R.layout.fragment_onboarding1, container, false);

        // Récupération de l'image
        ImageView image = rootView.findViewById(R.id.onboarding_image);

        // Animation (zoom + fade)
        image.setScaleX(0.8f);
        image.setScaleY(0.8f);
        image.setAlpha(0f);

        image.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(1000)
                .setStartDelay(200)
                .start();

        return rootView;
    }
}
