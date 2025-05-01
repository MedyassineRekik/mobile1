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
import com.example.mediassist.adapters.PrescriptionAdapter;
import com.example.mediassist.models.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionsActivity extends AppCompatActivity {

    private RecyclerView prescriptionsRecyclerView;
    private PrescriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);



        // Initialisation des vues
        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.title);
        Button importPrescriptionButton = findViewById(R.id.importPrescriptionButton);
        prescriptionsRecyclerView = findViewById(R.id.prescriptionsRecyclerView);

        // Configuration UI
        title.setText("Ordonnances");
        backButton.setOnClickListener(v -> finish());

        // Configuration RecyclerView
        prescriptionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriptionAdapter(getSamplePrescriptions());
        prescriptionsRecyclerView.setAdapter(adapter);

        // Bouton Importer Ordonnance
       // importPrescriptionButton.setOnClickListener(v -> {
         //   Intent intent = new Intent(PrescriptionsActivity.this, ImportPrescriptionActivity.class);
           // startActivity(intent);
        //});
    }

    private List<Prescription> getSamplePrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();

        // Exemple d'ordonnance (comme dans l'image)
        Prescription biodReport = new Prescription();
        biodReport.setTitle("Biod report for December");
        biodReport.setLaboratory("Manouba Labs");
        prescriptions.add(biodReport);

        return prescriptions;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}