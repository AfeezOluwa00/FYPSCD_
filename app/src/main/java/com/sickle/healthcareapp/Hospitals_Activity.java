package com.sickle.healthcareapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mysicklecellapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sickle.healthcareapp.Interface.HospitalApi;
import com.sickle.healthcareapp.model.Hospital;
import com.sickle.healthcareapp.model.HospitalsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Hospitals_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitl);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check if location permissions are granted
        if (checkLocationPermissions()) {
            // Request the current location once
            new FetchHospitalsTask().execute();
        } else {
            // You may want to request permissions at this point
            requestLocationPermissions();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }

    private class FetchHospitalsTask extends AsyncTask<Void, Void, LatLng> {

        @Override
        protected LatLng doInBackground(Void... voids) {
            // Use the last known location for the API call
            if (checkLocationPermissions() && isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(Hospitals_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(Hospitals_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(LatLng currentLocation) {
            super.onPostExecute(currentLocation);
            if (currentLocation != null) {
                fetchHospitals(currentLocation.latitude, currentLocation.longitude);
            } else {
                // Handle the case where the current location is not available
                Toast.makeText(Hospitals_Activity.this, "Current location not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchHospitals(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HospitalApi service = retrofit.create(HospitalApi.class);

        String currentLocation = latitude + "," + longitude;
        Call<HospitalsResponse> call = service.getHospitals(currentLocation, 5000, "hospital", "AIzaSyCRg9fpci2f_LcXjwuiyDgnVgHiuAj06FM");

        call.enqueue(new Callback<HospitalsResponse>() {
            @Override
            public void onResponse(Call<HospitalsResponse> call, Response<HospitalsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hospital> hospitals = response.body().getResults();
                    displayHospitalsOnMap(hospitals);
                }
            }

            @Override
            public void onFailure(Call<HospitalsResponse> call, Throwable t) {
                Log.e("TAG", "onFailure called", t);
            }
        });
    }

    private void displayHospitalsOnMap(List<Hospital> hospitals) {
        if (googleMap != null) {
            for (Hospital hospital : hospitals) {
                LatLng hospitalLocation = new LatLng(hospital.getLatitude(), hospital.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(hospitalLocation).title(hospital.getName()));
            }

            // Move the camera to the first hospital
            if (!hospitals.isEmpty()) {
                LatLng firstHospitalLocation = new LatLng(hospitals.get(0).getLatitude(), hospitals.get(0).getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstHospitalLocation, 15));
            }
        }
    }

    private void displayCurrentLocationOnMap(LatLng currentLocation) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        }
    }

    private boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    private boolean isLocationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Location services are required to be enabled in Android P (API 28) and above
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            // For devices before Android P, check the global location setting
            try {
                int locationMode;
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                return locationMode != Settings.Secure.LOCATION_MODE_OFF;
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
