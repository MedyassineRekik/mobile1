package com.example.mediassist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.adapters.MedicationAdapter;
import com.example.mediassist.models.Medication;

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
      //  addMedicationButton.setOnClickListener(v -> {
        //    Intent intent = new Intent(MedicationActivity.this, AddMedicationActivity.class);
          //  startActivity(intent);
        //});
    }

    private void initializeMedications() {
        // Exemples de données (à remplacer par vos données réelles)
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}