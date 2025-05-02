package com.example.mediassist.activities.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;
import com.example.mediassist.database.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private EditText firstnameEditText, emailEditText, usernameEditText, passwordEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Log.d(TAG, "onCreate called");

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Initialisation des vues avec vérification de null
        initializeViews();

        // Configuration des écouteurs de clic
        setupClickListeners();
    }

    private void initializeViews() {
        try {
            firstnameEditText = findViewById(R.id.firstnameEditText);
            emailEditText = findViewById(R.id.emailEditText);
            usernameEditText = findViewById(R.id.usernameEditText);
            passwordEditText = findViewById(R.id.passwordEditText);

            Button signUpButton = findViewById(R.id.signUpButton);
            TextView loginText = findViewById(R.id.loginText);

            if (firstnameEditText == null || emailEditText == null ||
                    usernameEditText == null || passwordEditText == null ||
                    signUpButton == null || loginText == null) {
                Toast.makeText(this, "Erreur: Éléments d'interface manquants", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Un ou plusieurs éléments d'interface sont null");
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur d'initialisation: " + e.getMessage());
            Toast.makeText(this, "Erreur d'initialisation", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupClickListeners() {
        // Bouton d'inscription
        findViewById(R.id.signUpButton).setOnClickListener(v -> handleSignUp());

        // Texte "Déjà inscrit ? Connexion"
        findViewById(R.id.loginText).setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void handleSignUp() {
        try {
            // Récupération des valeurs
            String firstname = firstnameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validation des champs
            if (!validateInputs(firstname, email, username, password)) {
                return;
            }

            // Tentative d'inscription
            if (dbHelper.addUser(firstname, email, username, password)) {
                Toast.makeText(this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Nouvel utilisateur inscrit: " + username);

                // Redirection vers LoginActivity
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Échec de l'inscription pour: " + username);
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'inscription: " + e.getMessage());
            Toast.makeText(this, "Erreur inattendue", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs(String firstname, String email, String username, String password) {
        if (firstname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dbHelper.usernameExists(username)) {
            Toast.makeText(this, "Ce nom d'utilisateur est déjà pris", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dbHelper.emailExists(email)) {
            Toast.makeText(this, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}