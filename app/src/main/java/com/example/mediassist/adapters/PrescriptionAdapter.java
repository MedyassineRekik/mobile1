package com.example.mediassist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.models.Prescription;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    private final List<Prescription> prescriptions;

    public PrescriptionAdapter(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription, parent, false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {
        Prescription prescription = prescriptions.get(position);
        holder.bind(prescription);
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    static class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView labText;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.prescriptionTitle);
            labText = itemView.findViewById(R.id.laboratoryName);
        }

        public void bind(Prescription prescription) {
            titleText.setText(prescription.getTitle());
            labText.setText(prescription.getLaboratory());
        }
    }
}