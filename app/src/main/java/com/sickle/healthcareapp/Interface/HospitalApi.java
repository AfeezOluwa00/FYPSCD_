package com.sickle.healthcareapp.Interface;

import com.sickle.healthcareapp.model.HospitalsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HospitalApi {

    @GET("maps/api/place/nearbysearch/json")
    Call<HospitalsResponse> getHospitals(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String apiKey
    );
}
