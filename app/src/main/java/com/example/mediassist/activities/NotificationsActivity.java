package com.example.mediassist.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediassist.R;
import com.example.mediassist.adapters.NotificationsAdapter;
import com.example.mediassist.models.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Configuration de la toolbar
        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.title);
        title.setText("Notifications");
        backButton.setOnClickListener(v -> finish());

        // Configuration du RecyclerView
        RecyclerView notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Notification> notifications = getSampleNotifications();
        NotificationsAdapter adapter = new NotificationsAdapter(notifications);
        notificationsRecyclerView.setAdapter(adapter);
    }

    private List<Notification> getSampleNotifications() {
        List<Notification> notifications = new ArrayList<>();

        // Notification 1 (comme dans l'image)
        Notification notification1 = new Notification();
        notification1.setMessage("You have to take your medication");
        notification1.setMedicationName("OBH Combi");
        notification1.setInstructions("Eat something");
        notification1.setCompleted(false);

        // Notification 2
        Notification notification2 = new Notification();
        notification2.setMessage("Rappel de prise de médicament");
        notification2.setMedicationName("Paracétamol 500mg");
        notification2.setInstructions("Prendre avec un verre d'eau");
        notification2.setCompleted(true);

        notifications.add(notification1);
        notifications.add(notification2);

        return notifications;
    }
}