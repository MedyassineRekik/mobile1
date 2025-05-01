package com.example.mediassist.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mediassist.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Configuration de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profil Utilisateur");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Remplissage des informations du profil (à remplacer par les données réelles de l'utilisateur)
        TextView nameText = findViewById(R.id.nameText);
        TextView ageText = findViewById(R.id.ageText);
        TextView genderText = findViewById(R.id.genderText);
        TextView bloodTypeText = findViewById(R.id.bloodTypeText);
        TextView weightText = findViewById(R.id.weightText);
        TextView heightText = findViewById(R.id.heightText);
        TextView allergiesText = findViewById(R.id.allergiesText);
        TextView phoneText = findViewById(R.id.phoneText);
        TextView addressText = findViewById(R.id.addressText);

        // Exemple de données - à remplacer par les données réelles de l'utilisateur
        nameText.setText("Cisco Nano");
        ageText.setText("70 ans");
        genderText.setText("Femme");
        bloodTypeText.setText("A+");
        weightText.setText("50 kg");
        heightText.setText("160 cm");
        allergiesText.setText("Aucune allergie connue");
        phoneText.setText("+21968975340");
        addressText.setText("Adresse non renseignée");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}