package com.sickle.healthcareapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HospitalsResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<Hospital> results;

    public String getStatus() {
        return status;
    }

    public List<Hospital> getResults() {
        return results;
    }
}
