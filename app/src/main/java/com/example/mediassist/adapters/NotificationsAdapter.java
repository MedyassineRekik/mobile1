package com.example.mediassist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediassist.R;
import com.example.mediassist.models.Notification;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private List<Notification> notifications;

    public NotificationsAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.messageText.setText(notification.getMessage());
        holder.medicationNameText.setText(notification.getMedicationName());
        holder.instructionsText.setText(notification.getInstructions());

        holder.completeButton.setOnClickListener(v -> {
            // Marquer comme complété
            notification.setCompleted(true);
            holder.completeButton.setText("Completed");
            holder.completeButton.setBackgroundTintList(
                    v.getContext().getResources().getColorStateList(R.color.green_completed));
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, medicationNameText, instructionsText;
        Button completeButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.notificationMessage);
            medicationNameText = itemView.findViewById(R.id.medicationName);
            instructionsText = itemView.findViewById(R.id.instructions);
            completeButton = itemView.findViewById(R.id.completeButton);
        }
    }
}