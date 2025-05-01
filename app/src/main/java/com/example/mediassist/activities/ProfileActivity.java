package com.example.mediassist.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mediassist.R;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(v -> showEditProfileDialog());

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
    private void showEditProfileDialog() {
        // Inflater le layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null);

        // Configurer le dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Initialiser les champs avec les valeurs actuelles
        initDialogFields(dialogView);

        // Gérer les boutons
        Button btnCancel = dialogView.findViewById(R.id.dialogBtnCancel);
        Button btnSave = dialogView.findViewById(R.id.dialogBtnSave);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            saveProfileChanges(dialogView);
            dialog.dismiss();
        });
    }

    private void initDialogFields(View dialogView) {
        // Récupérer les TextViews actuels
        TextView nameText = findViewById(R.id.nameText);
        TextView ageText = findViewById(R.id.ageText);
        TextView genderText = findViewById(R.id.genderText);
        TextView bloodTypeText = findViewById(R.id.bloodTypeText);
        TextView weightText = findViewById(R.id.weightText);
        TextView heightText = findViewById(R.id.heightText);
        TextView allergiesText = findViewById(R.id.allergiesText);
        TextView phoneText = findViewById(R.id.phoneText);
        TextView addressText = findViewById(R.id.addressText);

        // Remplir les champs du dialog
        TextInputEditText dialogName = dialogView.findViewById(R.id.dialogNameText);
        TextInputEditText dialogAge = dialogView.findViewById(R.id.dialogAgeText);
        RadioGroup dialogGender = dialogView.findViewById(R.id.dialogGenderRadioGroup);
        Spinner dialogBloodType = dialogView.findViewById(R.id.dialogBloodTypeSpinner);
        TextInputEditText dialogWeight = dialogView.findViewById(R.id.dialogWeightText);
        TextInputEditText dialogHeight = dialogView.findViewById(R.id.dialogHeightText);
        TextInputEditText dialogAllergies = dialogView.findViewById(R.id.dialogAllergiesText);
        TextInputEditText dialogPhone = dialogView.findViewById(R.id.dialogPhoneText);
        TextInputEditText dialogAddress = dialogView.findViewById(R.id.dialogAddressText);

        // Remplir les valeurs
        dialogName.setText(nameText.getText());

        // Extraire l'âge numérique (supprimer " ans")
        String ageStr = ageText.getText().toString().replace(" ans", "");
        dialogAge.setText(ageStr);

        // Configurer le genre
        if (genderText.getText().toString().equals("Homme")) {
            dialogGender.check(R.id.dialogGenderMale);
        } else {
            dialogGender.check(R.id.dialogGenderFemale);
        }

        // Configurer le groupe sanguin
        ArrayAdapter<CharSequence> bloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogBloodType.setAdapter(bloodAdapter);

        // Sélectionner la valeur actuelle
        if (bloodTypeText.getText() != null) {
            int spinnerPosition = bloodAdapter.getPosition(bloodTypeText.getText());
            dialogBloodType.setSelection(spinnerPosition);
        }

        // Extraire le poids numérique (supprimer " kg")
        String weightStr = weightText.getText().toString().replace(" kg", "");
        dialogWeight.setText(weightStr);

        // Extraire la taille numérique (supprimer " cm")
        String heightStr = heightText.getText().toString().replace(" cm", "");
        dialogHeight.setText(heightStr);

        dialogAllergies.setText(allergiesText.getText());
        dialogPhone.setText(phoneText.getText());
        dialogAddress.setText(addressText.getText());
    }

    private void saveProfileChanges(View dialogView) {
        // Récupérer les nouvelles valeurs
        TextInputEditText dialogName = dialogView.findViewById(R.id.dialogNameText);
        TextInputEditText dialogAge = dialogView.findViewById(R.id.dialogAgeText);
        RadioGroup dialogGender = dialogView.findViewById(R.id.dialogGenderRadioGroup);
        Spinner dialogBloodType = dialogView.findViewById(R.id.dialogBloodTypeSpinner);
        TextInputEditText dialogWeight = dialogView.findViewById(R.id.dialogWeightText);
        TextInputEditText dialogHeight = dialogView.findViewById(R.id.dialogHeightText);
        TextInputEditText dialogAllergies = dialogView.findViewById(R.id.dialogAllergiesText);
        TextInputEditText dialogPhone = dialogView.findViewById(R.id.dialogPhoneText);
        TextInputEditText dialogAddress = dialogView.findViewById(R.id.dialogAddressText);

        // Mettre à jour les TextViews
        TextView nameText = findViewById(R.id.nameText);
        TextView ageText = findViewById(R.id.ageText);
        TextView genderText = findViewById(R.id.genderText);
        TextView bloodTypeText = findViewById(R.id.bloodTypeText);
        TextView weightText = findViewById(R.id.weightText);
        TextView heightText = findViewById(R.id.heightText);
        TextView allergiesText = findViewById(R.id.allergiesText);
        TextView phoneText = findViewById(R.id.phoneText);
        TextView addressText = findViewById(R.id.addressText);

        nameText.setText(dialogName.getText().toString());
        ageText.setText(dialogAge.getText().toString() + " ans");

        int selectedGenderId = dialogGender.getCheckedRadioButtonId();
        if (selectedGenderId == R.id.dialogGenderMale) {
            genderText.setText("Homme");
        } else {
            genderText.setText("Femme");
        }

        bloodTypeText.setText(dialogBloodType.getSelectedItem().toString());
        weightText.setText(dialogWeight.getText().toString() + " kg");
        heightText.setText(dialogHeight.getText().toString() + " cm");
        allergiesText.setText(dialogAllergies.getText().toString());
        phoneText.setText(dialogPhone.getText().toString());
        addressText.setText(dialogAddress.getText().toString());

        // Ici vous devriez aussi sauvegarder en base de données
        Toast.makeText(this, "Profil mis à jour", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}