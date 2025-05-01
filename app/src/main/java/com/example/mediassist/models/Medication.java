package com.example.mediassist.models;

public class Medication {
    private String name;
    private String dosage;
    private String frequency;
    private String schedule;
    private int imageResId;

    public Medication(String name, String dosage, String frequency, String schedule, int imageResId) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.schedule = schedule;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getSchedule() { return schedule; }
    public int getImageResId() { return imageResId; }
}