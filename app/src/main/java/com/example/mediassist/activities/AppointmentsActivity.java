package com.example.mediassist.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.mediassist.adapters.AppointmentAdapter;
import com.example.mediassist.models.Appointment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private RecyclerView appointmentsRecyclerView;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentsList = new ArrayList<>();

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
        appointmentsList = getSampleAppointments();
        adapter = new AppointmentAdapter(appointmentsList);
        appointmentsRecyclerView.setAdapter(adapter);

        // Bouton Ajouter Rendez-vous
        addAppointmentButton.setOnClickListener(v -> showAddAppointmentDialog());
    }

    private void showAddAppointmentDialog() {
        // Inflater le layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_appointment, null);

        // Créer le dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Initialiser les sélecteurs de date et heure
        TextInputEditText etDate = dialogView.findViewById(R.id.etDate);
        TextInputEditText etTime = dialogView.findViewById(R.id.etTime);
        AutoCompleteTextView actvCategory = dialogView.findViewById(R.id.actvCategory);

        // Configurer les catégories
        String[] categories = new String[]{"Analyse", "Consultation médicale", "Radiologie", "Chirurgie"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        actvCategory.setAdapter(categoryAdapter);

        // Sélecteur de date
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        etDate.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePicker.show();
        });

        // Sélecteur d'heure
        etTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        etTime.setText(selectedTime);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );
            timePicker.show();
        });

        // Gérer les boutons
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnAdd.setOnClickListener(v -> {
            // Récupérer les valeurs
            TextInputEditText etTitle = dialogView.findViewById(R.id.etAppointmentTitle);
            TextInputEditText etDetails = dialogView.findViewById(R.id.etDetails);

            String title = etTitle.getText().toString().trim();
            String category = actvCategory.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String details = etDetails.getText().toString().trim();

            // Validation
            if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer le nouveau rendez-vous
            Appointment newAppointment = new Appointment();
            newAppointment.setTitle(title);
            newAppointment.addDetail("Catégorie: " + (category.isEmpty() ? "Non spécifiée" : category));
            newAppointment.addDetail("Date: " + date + " à " + time);
            if (!details.isEmpty()) {
                newAppointment.addDetail("Détails: " + details);
            }

            // Ajouter à la liste
            appointmentsList.add(newAppointment);
            adapter.notifyItemInserted(appointmentsList.size() - 1);

            Toast.makeText(this, "Rendez-vous ajouté", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
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
}
