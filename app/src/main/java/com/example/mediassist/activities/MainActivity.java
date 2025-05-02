package com.example.mediassist.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
            intent.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(intent);
        });

        scheduleCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        });

        // Gestion du menu
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> showMenuDrawer());

        // Option de déconnexion avec popup de confirmation
        TextView logoutText = findViewById(R.id.logoutText);
        logoutText.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void showMenuDrawer() {
        // Inflater le layout du menu

        View menuView = LayoutInflater.from(this).inflate(R.layout.menu_drawer, null);

        // Créer le popup
        PopupWindow popupWindow = new PopupWindow(
                menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Style du popup
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(16f);
        popupWindow.setOutsideTouchable(true);

        // Afficher le popup ancré à l'icône menu
        menuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        ImageView menuIcon = findViewById(R.id.menuIcon);

        // Positionner le menu à droite de l'icône
        int[] location = new int[2];
        menuIcon.getLocationOnScreen(location);
        popupWindow.showAtLocation(menuIcon, Gravity.NO_GRAVITY,
                location[0] + menuIcon.getWidth(), location[1]);

        // Gérer les clics sur les éléments du menu
        LinearLayout menuNotifications = menuView.findViewById(R.id.menuNotifications);
        LinearLayout menuSettings = menuView.findViewById(R.id.menuSettings);
        LinearLayout menuAbout = menuView.findViewById(R.id.menuAbout);

        // Dans la méthode showMenuDrawer() :
        menuNotifications.setOnClickListener(v -> {
            popupWindow.dismiss();
            // Ouvrir les notifications
            Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        menuSettings.setOnClickListener(v -> {
            popupWindow.dismiss();
            // Ouvrir les paramètres
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        });

        menuAbout.setOnClickListener(v -> {
            popupWindow.dismiss();
            // Ouvrir About Us
            Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
            startActivity(intent);
        });
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
        }
        );
    }
}