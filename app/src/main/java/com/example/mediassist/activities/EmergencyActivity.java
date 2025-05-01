package com.example.mediassist.activities;

import android.content.Intent;
import android.net.Uri;
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
import com.example.mediassist.adapters.EmergencyContactAdapter;
import com.example.mediassist.models.EmergencyContact;
import com.google.android.material.textfield.TextInputEditText;

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
        addContactButton.setOnClickListener(v -> showAddContactDialog());
    }

    private void showAddContactDialog() {
        // Inflater le layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_emergency_contact, null);

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
            TextInputEditText etName = dialogView.findViewById(R.id.etContactName);
            TextInputEditText etRelation = dialogView.findViewById(R.id.etContactRelation);
            TextInputEditText etPhone = dialogView.findViewById(R.id.etContactPhone);

            String name = etName.getText().toString().trim();
            String relation = etRelation.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // Validation simple
            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ajouter le nouveau contact
            EmergencyContact newContact = new EmergencyContact(
                    name,
                    relation.isEmpty() ? "Contact" : relation,
                    phone,
                    R.drawable.ic_username);

            contactsList.add(newContact);
            adapter.notifyItemInserted(contactsList.size() - 1);

            // Ici vous devriez sauvegarder en base de données
            // saveContactToDatabase(newContact);

            Toast.makeText(this, "Contact ajouté", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
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
    }
}