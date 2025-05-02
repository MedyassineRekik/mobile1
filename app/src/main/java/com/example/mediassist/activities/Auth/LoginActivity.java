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
import com.example.mediassist.activities.MainActivity;
import com.example.mediassist.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText usernameEditText, passwordEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate called");

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Initialisation des vues avec vérification
        initializeViews();

        // Configuration des écouteurs de clic
        setupClickListeners();
    }

    private void initializeViews() {
        try {
            usernameEditText = findViewById(R.id.usernameEditText);
            passwordEditText = findViewById(R.id.passwordEditText);

            if (usernameEditText == null || passwordEditText == null) {
                Toast.makeText(this, "Erreur: Éléments d'interface manquants", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Éléments d'interface non trouvés");
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur d'initialisation: " + e.getMessage());
            Toast.makeText(this, "Erreur d'initialisation", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupClickListeners() {
        // Bouton de connexion
        findViewById(R.id.loginButton).setOnClickListener(v -> handleLogin());

        // Texte d'inscription
        findViewById(R.id.signUpText).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
    }

    private void handleLogin() {
        try {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validation basique
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification des identifiants
            if (dbHelper.checkUser(username, password)) {
                Log.d(TAG, "Connexion réussie pour: " + username);
                Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                // Redirection vers MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", username); // Ajoute ça
                startActivity(intent);
                finish();
            } else {
                Log.w(TAG, "Échec de connexion pour: " + username);
                Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur de connexion: " + e.getMessage());
            Toast.makeText(this, "Erreur lors de la connexion", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}