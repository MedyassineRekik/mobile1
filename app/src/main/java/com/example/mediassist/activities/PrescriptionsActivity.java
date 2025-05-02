package com.example.mediassist.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.adapters.PrescriptionAdapter;
import com.example.mediassist.models.Prescription;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class PrescriptionsActivity extends AppCompatActivity {
    private static final int PICK_PDF_FILE = 101;

    private RecyclerView prescriptionsRecyclerView;
    private PrescriptionAdapter adapter;
    private List<Prescription> prescriptionsList = new ArrayList<>();
    private Uri pdfUri;

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
        prescriptionsList = getSamplePrescriptions();
        adapter = new PrescriptionAdapter(prescriptionsList);
        prescriptionsRecyclerView.setAdapter(adapter);

        // Bouton Importer Ordonnance
        importPrescriptionButton.setOnClickListener(v -> showImportPrescriptionDialog());
    }

    private void showImportPrescriptionDialog() {
        // Inflater le layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_import_prescription, null);

        // Créer le dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(dialogView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Initialiser les vues
        TextInputEditText etDate = dialogView.findViewById(R.id.etDate);
        CardView importCard = dialogView.findViewById(R.id.importCard);
        TextView tvFileName = dialogView.findViewById(R.id.tvFileName);

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

        // Sélection de fichier PDF
        importCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_PDF_FILE);
        });

        // Gérer les boutons
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnImport = dialogView.findViewById(R.id.btnImport);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnImport.setOnClickListener(v -> {
            // Récupérer les valeurs
            TextInputEditText etTitle = dialogView.findViewById(R.id.etPrescriptionTitle);
            TextInputEditText etLaboratory = dialogView.findViewById(R.id.etLaboratory);

            String title = etTitle.getText().toString().trim();
            String laboratory = etLaboratory.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            // Validation
            if (title.isEmpty() || pdfUri == null) {
                Toast.makeText(this, "Veuillez remplir le titre et sélectionner un fichier", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer la nouvelle ordonnance
            Prescription newPrescription = new Prescription();
            newPrescription.setTitle(title);
            newPrescription.setLaboratory(laboratory.isEmpty() ? "Non spécifié" : laboratory);
            newPrescription.setDate(date.isEmpty() ? "Date non spécifiée" : date);
            newPrescription.setPdfUri(pdfUri.toString());

            // Ajouter à la liste
            prescriptionsList.add(newPrescription);
            adapter.notifyItemInserted(prescriptionsList.size() - 1);

            // Ici vous devriez aussi sauvegarder le fichier PDF localement
            // savePdfFile(pdfUri);

            Toast.makeText(this, "Ordonnance importée", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                pdfUri = data.getData();
                // Afficher le nom du fichier sélectionné
                String fileName = getFileNameFromUri(pdfUri);
                TextView tvFileName = findViewById(R.id.tvFileName);
                if (tvFileName != null) {
                    tvFileName.setText(fileName);
                }
            }
        }
    }

    @SuppressLint("Range")
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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
}