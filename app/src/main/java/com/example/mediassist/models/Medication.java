package com.example.mediassist.models;

public class Medication {
    private String name;
    private String dosage;
    private String frequency;
    private String schedule;
    private String imageUri ;

    public Medication(String name, String dosage, String frequency, String schedule, int imageResId) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.schedule = schedule;
        this.imageUri  = imageUri ;
    }

    // Getters
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getSchedule() { return schedule; }
    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}