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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Définition du titre
        TextView title = findViewById(R.id.title);
        title.setText("MediAssist Rappels");

        // Initialisation des CardViews pour chaque fonctionnalité
        initialiserCartesFonctionnalites();

        // Gestion du menu
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> afficherMenuDeroulant());

        // Option de déconnexion
        TextView logoutText = findViewById(R.id.logoutText);
        logoutText.setOnClickListener(v -> afficherConfirmationDeconnexion());
    }

    private void initialiserCartesFonctionnalites() {
        // Rendez-vous médicaux
        CardView appointmentsCard = findViewById(R.id.appointmentsCard);
        appointmentsCard.setOnClickListener(v -> {
            startActivity(new Intent(this, AppointmentsActivity.class));
        });

        // Urgences
        CardView emergencyCard = findViewById(R.id.emergencyCard);
        emergencyCard.setOnClickListener(v -> {
            startActivity(new Intent(this, EmergencyActivity.class));
        });

        // Médicaments
        CardView medicationCard = findViewById(R.id.medicationCard);
        medicationCard.setOnClickListener(v -> {
            startActivity(new Intent(this, MedicationActivity.class));
        });

        // Ordonnances
        CardView prescriptionsCard = findViewById(R.id.prescriptionsCard);
        prescriptionsCard.setOnClickListener(v -> {
            startActivity(new Intent(this, PrescriptionsActivity.class));
        });

        // Profil
        CardView profileCard = findViewById(R.id.profileCard);
        profileCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(intent);
        });

        // Planning
        CardView scheduleCard = findViewById(R.id.scheduleCard);
        scheduleCard.setOnClickListener(v -> {
            startActivity(new Intent(this, ScheduleActivity.class));
        });
    }

    private void afficherMenuDeroulant() {
        View menuView = LayoutInflater.from(this).inflate(R.layout.menu_drawer, null);

        // Création et configuration du popup
        PopupWindow popupWindow = new PopupWindow(
                menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(16f);
        popupWindow.setOutsideTouchable(true);

        // Positionnement du menu
        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int[] location = new int[2];
        menuIcon.getLocationOnScreen(location);
        popupWindow.showAtLocation(menuIcon, Gravity.NO_GRAVITY,
                location[0] + menuIcon.getWidth(), location[1]);

        // Gestion des clics sur les éléments du menu
        configurerActionsMenu(menuView, popupWindow);
    }

    private void configurerActionsMenu(View menuView, PopupWindow popupWindow) {
        // Notifications
        LinearLayout menuNotifications = menuView.findViewById(R.id.menuNotifications);
        menuNotifications.setOnClickListener(v -> {
            popupWindow.dismiss();
            startActivity(new Intent(this, NotificationsActivity.class));
        });

        // Paramètres
        LinearLayout menuSettings = menuView.findViewById(R.id.menuSettings);
        menuSettings.setOnClickListener(v -> {
            popupWindow.dismiss();
            Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
        });

        // À propos
        LinearLayout menuAbout = menuView.findViewById(R.id.menuAbout);
        menuAbout.setOnClickListener(v -> {
            popupWindow.dismiss();
            startActivity(new Intent(this, AboutActivity.class));
        });
    }

    private void afficherConfirmationDeconnexion() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Style de la fenêtre
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        // Gestion des boutons
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnLogout = dialogView.findViewById(R.id.btnLogout);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnLogout.setOnClickListener(v -> {
            deconnecterUtilisateur();
            dialog.dismiss();
        });
    }

    private void deconnecterUtilisateur() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}