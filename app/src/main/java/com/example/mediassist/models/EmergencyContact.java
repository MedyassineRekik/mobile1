package com.example.mediassist.models;

public class EmergencyContact {
    private String name;
    private String relation;
    private String phoneNumber;
    private int photoResId;

    public EmergencyContact(String name, String relation, String phoneNumber, int photoResId) {
        this.name = name;
        this.relation = relation;
        this.phoneNumber = phoneNumber;
        this.photoResId = photoResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getPhotoResId() {
        return photoResId;
    }
}