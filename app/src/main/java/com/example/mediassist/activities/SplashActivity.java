package com.example.mediassist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // Import manquant
import androidx.appcompat.app.AppCompatActivity;
import com.example.mediassist.R;
import com.example.mediassist.activities.onboarding.OnboardingActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // lien avec le fichier XML

        // Utilisation correcte de Handler
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
            finish();
        }, 3000); // 3 secondes de délai
    }
}