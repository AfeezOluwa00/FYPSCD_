package com.sickle.healthcareapp.model.medicineNameModels;

public class MedicineFireStoreModel {

    private String medicineName;
    private int day;
    private int month;
    private int year;
    private int noOfTimesPerDay;
    private int totalDoses;
    private String timings;
    private String alertType;
    private String medicineNDC;
    private String activeIngredientName;
    private String activeIngredientStrength;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNoOfTimesPerDay() {
        return noOfTimesPerDay;
    }

    public void setNoOfTimesPerDay(int noOfTimesPerDay) {
        this.noOfTimesPerDay = noOfTimesPerDay;
    }

    public int getTotalDoses() {
        return totalDoses;
    }

    public void setTotalDoses(int totalDoses) {
        this.totalDoses = totalDoses;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getMedicineNDC() {
        return medicineNDC;
    }

    public void setMedicineNDC(String medicineNDC) {
        this.medicineNDC = medicineNDC;
    }

    public String getActiveIngredientName() {
        return activeIngredientName;
    }

    public void setActiveIngredientName(String activeIngredientName) {
        this.activeIngredientName = activeIngredientName;
    }

    public String getActiveIngredientStrength() {
        return activeIngredientStrength;
    }

    public void setActiveIngredientStrength(String activeIngredientStrength) {
        this.activeIngredientStrength = activeIngredientStrength;
    }
}
