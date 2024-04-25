package com.sickle.healthcareapp.home;

public class HomeItem {

    private String medicineName;
    private String dosageSummary;

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    private String timings;

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    private String dose;

    public HomeItem(String medicineName, String dosageSummary,String mDose,String mtiming) {
        this.medicineName = medicineName;
        this.dosageSummary = dosageSummary;
        this.dose = mDose;
        this.timings = mtiming;
    }

    String getMedicineName() {
        return medicineName;
    }

    String getDosageSummary() {
        return dosageSummary;
    }
}
