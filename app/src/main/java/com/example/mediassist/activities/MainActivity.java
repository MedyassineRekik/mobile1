package com.example.mediassist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mediassist.R;
import com.example.mediassist.activities.Auth.LoginActivity;
import com.example.mediassist.activities.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Définition du titre
        TextView title = findViewById(R.id.title);
        title.setText("MediAssist Reminder");

        // Initialisation des CardViews pour chaque fonctionnalité
        CardView appointmentsCard = findViewById(R.id.appointmentsCard);
        CardView emergencyCard = findViewById(R.id.emergencyCard);
        CardView medicationCard = findViewById(R.id.medicationCard);
        CardView prescriptionsCard = findViewById(R.id.prescriptionsCard);
        CardView profileCard = findViewById(R.id.profileCard);
        CardView scheduleCard = findViewById(R.id.scheduleCard);

        // Navigation vers les différentes activités
        appointmentsCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AppointmentsActivity.class);
            startActivity(intent);
        });

        emergencyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmergencyActivity.class);
            startActivity(intent);
        });

        medicationCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MedicationActivity.class);
            startActivity(intent);
        });

        prescriptionsCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PrescriptionsActivity.class);
            startActivity(intent);
        });

        profileCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        scheduleCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        });

        // Gestion du menu (dans l'image c'est un swipe vers la droite)
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> {
            // Ici vous pouvez implémenter un drawer navigation ou une autre activité
            // Pour l'exemple, nous allons vers une page "About Us"
            Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
            startActivity(intent);
        });

        // Option de déconnexion (peut être dans le menu)
        TextView logoutText = findViewById(R.id.logoutText);
        logoutText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}