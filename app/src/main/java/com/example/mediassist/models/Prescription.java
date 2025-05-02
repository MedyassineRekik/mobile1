package com.example.mediassist.models;

public class Prescription {
    private String title;
    private String laboratory;
    private String date;
    private String pdfUri;

    // Getters et setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLaboratory() { return laboratory; }
    public void setLaboratory(String laboratory) { this.laboratory = laboratory; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPdfUri() { return pdfUri; }
    public void setPdfUri(String pdfUri) { this.pdfUri = pdfUri; }
}