package com.example.mediassist.models;

public class Notification {
    private String message;
    private String medicationName;
    private String instructions;
    private boolean isCompleted;

    // Getters et setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}