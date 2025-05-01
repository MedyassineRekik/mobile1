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
import com.example.mediassist.adapters.AppointmentAdapter;
import com.example.mediassist.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private RecyclerView appointmentsRecyclerView;
    private AppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);



        // Initialisation des vues
        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.title);
        Button addAppointmentButton = findViewById(R.id.addAppointmentButton);
        appointmentsRecyclerView = findViewById(R.id.appointmentsRecyclerView);

        // Configuration UI
        title.setText("Rendez-vous");
        backButton.setOnClickListener(v -> finish());

        // Configuration RecyclerView
        appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentAdapter(getSampleAppointments());
        appointmentsRecyclerView.setAdapter(adapter);

        // Bouton Ajouter Rendez-vous
       // addAppointmentButton.setOnClickListener(v -> {
         //   Intent intent = new Intent(AppointmentsActivity.this, AddAppointmentActivity.class);
           // startActivity(intent);
        //});
    }

    private List<Appointment> getSampleAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        // Premier rendez-vous (comme dans l'image)
        Appointment dockerAppointment = new Appointment();
        dockerAppointment.setTitle("Docker Appointment");
        dockerAppointment.addDetail("Contingencies: Human Services");
        dockerAppointment.addDetail("Homepages");
        dockerAppointment.addDetail("Human Center Methods");
        appointments.add(dockerAppointment);

        // Deuxième rendez-vous (comme dans l'image)
        Appointment blockAnalysis = new Appointment();
        blockAnalysis.setTitle("Block Analysis");
        blockAnalysis.addDetail("Block/INSS4");
        blockAnalysis.addDetail("Human Center Methods");
        appointments.add(blockAnalysis);

        return appointments;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}