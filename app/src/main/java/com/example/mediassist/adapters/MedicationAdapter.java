package com.example.mediassist.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediassist.R;
import com.example.mediassist.models.Medication;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private final List<Medication> medicationList;

    public MedicationAdapter(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication medication = medicationList.get(position);
        holder.bind(medication);
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    static class MedicationViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView dosageText;
        private final TextView frequencyText;
        private final TextView scheduleText;
        private final ImageView medicationImage;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.medicationName);
            dosageText = itemView.findViewById(R.id.medicationDosage);
            frequencyText = itemView.findViewById(R.id.medicationFrequency);
            scheduleText = itemView.findViewById(R.id.medicationSchedule);
            medicationImage = itemView.findViewById(R.id.medicationImage);
        }

        public void bind(Medication medication) {
            nameText.setText(medication.getName());
            dosageText.setText(medication.getDosage());
            frequencyText.setText(medication.getFrequency());
            scheduleText.setText(medication.getSchedule());
            if (medication.getImageUri() != null) {
                Glide.with(itemView.getContext())
                        .load(Uri.parse(medication.getImageUri()))
                        .into(medicationImage);
            } else {
                medicationImage.setImageResource(R.drawable.ic_medication);
            }
        }}}

