package com.example.mediassist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediassist.R;
import com.example.mediassist.models.ScheduleItem;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private final List<ScheduleItem> scheduleItems;

    public ScheduleAdapter(List<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem item = scheduleItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView descText;
        private final TextView timeText;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.scheduleTitle);
            descText = itemView.findViewById(R.id.scheduleDescription);
            timeText = itemView.findViewById(R.id.scheduleTime);
        }

        public void bind(ScheduleItem item) {
            titleText.setText(item.getTitle());
            descText.setText(item.getDescription());
            timeText.setText(item.getTime());

            // Masquer la description si vide
            descText.setVisibility(item.getDescription().isEmpty() ? View.GONE : View.VISIBLE);
            timeText.setVisibility(item.getTime().isEmpty() ? View.GONE : View.VISIBLE);
        }
    }
}