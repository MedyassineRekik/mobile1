package com.example.mediassist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.models.EmergencyContact;

import java.util.List;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ContactViewHolder> {

    private List<EmergencyContact> contactsList;
    private OnContactClickListener listener;

    public interface OnContactClickListener {
        void onContactClick(EmergencyContact contact);
    }

    public EmergencyContactAdapter(List<EmergencyContact> contactsList, OnContactClickListener listener) {
        this.contactsList = contactsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emergency_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        EmergencyContact contact = contactsList.get(position);
        holder.nameText.setText(contact.getName());
        holder.relationText.setText(contact.getRelation());
        holder.phoneText.setText(contact.getPhoneNumber());
        holder.photoImage.setImageResource(contact.getPhotoResId());

        holder.itemView.setOnClickListener(v -> listener.onContactClick(contact));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, relationText, phoneText;
        ImageView photoImage;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            relationText = itemView.findViewById(R.id.relationText);
            phoneText = itemView.findViewById(R.id.phoneText);
            photoImage = itemView.findViewById(R.id.photoImage);
        }
    }
}