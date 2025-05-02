package com.example.mediassist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.adapters.MedicationAdapter;
import com.example.mediassist.models.Medication;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MedicationActivity extends AppCompatActivity {

    private RecyclerView medicationRecyclerView;
    private MedicationAdapter adapter;
    private List<Medication> medicationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        // Initialisation des vues
        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.title);
        Button addMedicationButton = findViewById(R.id.addMedicationButton);
        medicationRecyclerView = findViewById(R.id.medicationRecyclerView);

        // Configuration UI
        title.setText("Gestion des Médicaments");
        backButton.setOnClickListener(v -> finish());

        // Configuration RecyclerView
        medicationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeMedications();
        adapter = new MedicationAdapter(medicationList);
        medicationRecyclerView.setAdapter(adapter);

        // Bouton Ajouter Médicament
        addMedicationButton.setOnClickListener(v -> showAddMedicationDialog());
    }

    private void showAddMedicationDialog() {
        // Inflater le layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_medication, null);

        // Créer le dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Gérer les boutons
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnAdd.setOnClickListener(v -> {
            // Récupérer les valeurs
            TextInputEditText etName = dialogView.findViewById(R.id.etMedicationName);
            TextInputEditText etDosage = dialogView.findViewById(R.id.etDosage);
            TextInputEditText etFrequency = dialogView.findViewById(R.id.etFrequency);
            TextInputEditText etSchedule = dialogView.findViewById(R.id.etSchedule);

            String name = etName.getText().toString().trim();
            String dosage = etDosage.getText().toString().trim();
            String frequency = etFrequency.getText().toString().trim();
            String schedule = etSchedule.getText().toString().trim();

            // Validation simple
            if (name.isEmpty() || dosage.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ajouter le nouveau médicament
            Medication newMedication = new Medication(
                    name,
                    dosage,
                    frequency.isEmpty() ? "Non spécifié" : frequency,
                    schedule.isEmpty() ? "Non spécifié" : schedule,
                    R.drawable.ic_medication);

            medicationList.add(newMedication);
            adapter.notifyItemInserted(medicationList.size() - 1);

            Toast.makeText(this, "Médicament ajouté", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private void initializeMedications() {
        // Exemples de données
        medicationList.add(new Medication(
                "Paracétamol",
                "500mg",
                "3 fois/jour",
                "08:00, 12:00, 18:00",
                R.drawable.ic_medication));

        medicationList.add(new Medication(
                "Ibuprofène",
                "200mg",
                "2 fois/jour",
                "09:00, 21:00",
                R.drawable.ic_medication));
    }
}
