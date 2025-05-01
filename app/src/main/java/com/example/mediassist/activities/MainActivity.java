package com.example.mediassist.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
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

        // Gestion du menu
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
            startActivity(intent);
        });

        // Option de déconnexion avec popup de confirmation
        TextView logoutText = findViewById(R.id.logoutText);
        logoutText.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void showLogoutConfirmationDialog() {
        // Inflater le layout personnalisé
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);

        // Créer le dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Personnaliser la fenêtre de dialogue
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        // Gérer les clics sur les boutons
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnLogout = dialogView.findViewById(R.id.btnLogout);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnLogout.setOnClickListener(v -> {
            // Action de déconnexion
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });

}}