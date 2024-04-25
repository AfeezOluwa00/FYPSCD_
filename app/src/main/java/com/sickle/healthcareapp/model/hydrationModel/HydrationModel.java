package com.sickle.healthcareapp.model.hydrationModel;

public class HydrationModel {

    private int id;
    private String hydrationDate;
    private double ounceValue;
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

    public double getOunceValue() {
        return ounceValue;
    }

    public void setOunceValue(double ounceValue) {
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
