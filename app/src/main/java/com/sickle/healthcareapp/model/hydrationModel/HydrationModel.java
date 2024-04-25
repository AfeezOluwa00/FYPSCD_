package com.sickle.healthcareapp.model.hydrationModel;

public class HydrationModel {

    private int id;
    private String hydrationDate;
    private int ounceValue;
    private int ounceBottleValue;
    private int ounceGlassValue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHydrationDate() {
        return hydrationDate;
    }

    public void setHydrationDate(String hydrationDate) {
        this.hydrationDate = hydrationDate;
    }

    public int getOunceValue() {
        return ounceValue;
    }

    public void setOunceValue(int ounceValue) {
        this.ounceValue = ounceValue;
    }


    public int getOunceBottleValue() {
        return ounceBottleValue;
    }

    public void setOunceBottleValue(int ounceBottleValue) {
        this.ounceBottleValue = ounceBottleValue;
    }

    public int getOunceGlassValue() {
        return ounceGlassValue;
    }

    public void setOunceGlassValue(int ounceGlassValue) {
        this.ounceGlassValue = ounceGlassValue;
    }
}
