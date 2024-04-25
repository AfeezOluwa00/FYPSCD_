package com.sickle.healthcareapp.home;

public class HomeItem {

    private String medicineName;
    private String dosageSummary;
    private String activeIngredientName;
    private String activeIngredientStrength;
    private String medicineNDC;
    private String timings;
    private String dose;

    public HomeItem(String medicineName, String dosageSummary,String mDose,String mtiming,
                    String mActiveIngredientName,String mActiveIngredientStrength,String mActiveNDC) {
        this.medicineName = medicineName;
        this.dosageSummary = dosageSummary;
        this.dose = mDose;
        this.timings = mtiming;
        this.medicineNDC = mActiveNDC;
        this.activeIngredientName = mActiveIngredientName;
        this.activeIngredientStrength = mActiveIngredientStrength;

    }



    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }





    public String getMedicineName() {
        return medicineName;
    }

    public String getDosageSummary() {
        return dosageSummary;
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

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }


}
