package com.example.mediassist.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mediassist.database.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.io.InputStream;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;








public class ProfileActivity extends AppCompatActivity {

    // Request codes
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    // Views
    private ImageView dialogProfileImage; // Changed from CircleImageView to regular ImageView
    private Bitmap selectedProfileImage;
    private static final int CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        String username = getIntent().getStringExtra("username");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor userData = dbHelper.getUserData(username);


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
        if (userData.moveToFirst()) {
            nameText.setText(userData.getString(1)); // firstname
            ageText.setText(userData.getString(4) != null ? userData.getString(4) + " ans" : "");
            genderText.setText(userData.getString(5) != null ? userData.getString(5) : "");
            bloodTypeText.setText(userData.getString(6) != null ? userData.getString(6) : "");
            weightText.setText(userData.getString(7) != null ? userData.getString(7) + " kg" : "");
            heightText.setText(userData.getString(8) != null ? userData.getString(8) + " cm" : "");
            allergiesText.setText(userData.getString(9) != null ? userData.getString(9) : "");
            phoneText.setText(userData.getString(10) != null ? userData.getString(10) : "");
            addressText.setText(userData.getString(11) != null ? userData.getString(11) : "");
        }
        userData.close();
    }
    private void showEditProfileDialog() {
        // Inflater le layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null);


        // Initialize profile image view (using regular ImageView)
        dialogProfileImage = dialogView.findViewById(R.id.dialogProfileImage);
        Button btnChangePhoto = dialogView.findViewById(R.id.btnChangePhoto);

        // Set click listener for photo change
        btnChangePhoto.setOnClickListener(v -> {
            if (checkStoragePermission()) {
                openImagePicker();
            }
        });

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
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE
            );
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        // Your logic to open the camera
        Toast.makeText(this, "Camera is ready to use", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean checkStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                selectedProfileImage = BitmapFactory.decodeStream(inputStream);
                dialogProfileImage.setImageBitmap(selectedProfileImage);
            } catch (IOException e) {
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
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
        String username = getIntent().getStringExtra("username");

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
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean updated = dbHelper.updateUserProfile(username,
                dialogAge.getText().toString(),
                (genderText.getText().toString()), // ou bien à partir de selectedGenderId si tu préfères
                dialogBloodType.getSelectedItem().toString(),
                dialogWeight.getText().toString(),
                dialogHeight.getText().toString(),
                dialogAllergies.getText().toString(),
                dialogPhone.getText().toString(),
                dialogAddress.getText().toString());
        Log.d("PROFILE_SAVE", "Update result: " + updated);




        if (updated) {
            Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Échec de la mise à jour", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}