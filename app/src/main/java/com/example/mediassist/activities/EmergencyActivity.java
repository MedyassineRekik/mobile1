package com.example.mediassist.activities;

import android.content.Intent;
import android.net.Uri;
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
import com.example.mediassist.adapters.EmergencyContactAdapter;
import com.example.mediassist.models.EmergencyContact;

import java.util.ArrayList;
import java.util.List;

public class EmergencyActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private EmergencyContactAdapter adapter;
    private List<EmergencyContact> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);



        // Initialisation des vues
        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.title);
        Button addContactButton = findViewById(R.id.addContactButton);
        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);

        // Configuration des éléments UI
        title.setText("Contacts d'urgence");
        backButton.setOnClickListener(v -> finish());

        // Configuration du RecyclerView
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeContacts();
        adapter = new EmergencyContactAdapter(contactsList, this::onContactClicked);
        contactsRecyclerView.setAdapter(adapter);

        // Gestion du bouton Ajouter
        addContactButton.setOnClickListener(v -> {
            // TODO: Implémenter l'ajout de contact
            // Intent intent = new Intent(EmergencyActivity.this, AddEmergencyContactActivity.class);
            // startActivity(intent);
        });
    }

    private void initializeContacts() {
        // Ajout des contacts initiaux
        contactsList.add(new EmergencyContact(
                "Dr. Marcus Horizon",
                "Cardiologue",
                "97098197",
                R.drawable.ic_username));

        contactsList.add(new EmergencyContact(
                "Police",
                "Urgences",
                "197",
                R.drawable.ic_username));

        contactsList.add(new EmergencyContact(
                "Mon fils",
                "Famille",
                "97098134",
                R.drawable.ic_username));
    }

    private void onContactClicked(EmergencyContact contact) {
        // Lancement de l'appel téléphonique
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
        startActivity(intent);
    }}

