package com.example.mediassist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private final List<Appointment> appointments;

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.bind(appointment);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView detail1Text;
        private final TextView detail2Text;
        private final TextView detail3Text;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.appointmentTitle);
            detail1Text = itemView.findViewById(R.id.detail1);
            detail2Text = itemView.findViewById(R.id.detail2);
            detail3Text = itemView.findViewById(R.id.detail3);
        }

        public void bind(Appointment appointment) {
            titleText.setText(appointment.getTitle());

            List<String> details = appointment.getDetails();
            detail1Text.setText(details.size() > 0 ? details.get(0) : "");
            detail2Text.setText(details.size() > 1 ? details.get(1) : "");
            detail3Text.setText(details.size() > 2 ? details.get(2) : "");

            // Masquer les TextViews vides
            detail1Text.setVisibility(details.size() > 0 ? View.VISIBLE : View.GONE);
            detail2Text.setVisibility(details.size() > 1 ? View.VISIBLE : View.GONE);
            detail3Text.setVisibility(details.size() > 2 ? View.VISIBLE : View.GONE);
        }
    }
}