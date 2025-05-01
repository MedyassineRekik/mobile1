package com.example.mediassist.models;

import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private String title;
    private List<String> details = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDetails() {
        return details;
    }

    public void addDetail(String detail) {
        this.details.add(detail);
    }
}