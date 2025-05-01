package com.example.mediassist.activities;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.adapters.ScheduleAdapter;
import com.example.mediassist.models.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private TextView monthYearText;
    private RecyclerView scheduleRecyclerView;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Initialisation des vues

        ImageView backButton = findViewById(R.id.backButton);
        monthYearText = findViewById(R.id.monthYearText);
        scheduleRecyclerView = findViewById(R.id.scheduleRecyclerView);
        calendarView = findViewById(R.id.calendarView);
        backButton.setOnClickListener(v -> finish());

        // Gérer la sélection de date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(this, "Date sélectionnée : " + selectedDate, Toast.LENGTH_SHORT).show();
            monthYearText.setText("Date : " + selectedDate);
        });

        setupReminders();
    }

    private void setupReminders() {
        List<ScheduleItem> scheduleItems = new ArrayList<>();

        // Exemple de rappel
        scheduleItems.add(new ScheduleItem(
                "Docteur",
                "Prise de sang prévue aujourd'hui.",
                "1.soe"
        ));

        // Ajoutez plus de rappels ici si besoin...

        ScheduleAdapter adapter = new ScheduleAdapter(scheduleItems);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleRecyclerView.setAdapter(adapter);
    }
}
