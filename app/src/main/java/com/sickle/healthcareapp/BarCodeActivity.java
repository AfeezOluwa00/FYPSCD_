package com.sickle.healthcareapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mysicklecellapp.R;
import com.sickle.healthcareapp.home.AddDialog;

import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class BarCodeActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private ZBarScannerView scannerView;

    // FDA API key (replace with your actual key)
    private static final String FDA_API_KEY = "tvCQTJlyexutBbdOX2MUkftYYWPzaqD6pQDz1b3K";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        // Request camera permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            initializeScanner();
        }
    }

    private void initializeScanner() {
        // Initialize the scannerView
        scannerView = new ZBarScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(new ZBarScannerView.ResultHandler() {
            @Override
            public void handleResult(Result rawResult) {
                AddDialog.barCodeScanned = rawResult.getContents();

                    onBackPressed();

                Toast.makeText(BarCodeActivity.this,
                        rawResult.getContents(),Toast.LENGTH_SHORT).show();
            }
        });

        // Start the scanner
//        scannerView.setResultHandler(result -> {
//            String scannedContent = result.getContents();
//            getFdaData(scannedContent);
//        });
        scannerView.startCamera(); // Start camera on resume
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scannerView != null) {
            scannerView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.stopCamera();
        }


    }

    // Retrofit interface for FDA API
    public interface FdaApi {
        @GET("drug/ndc.json")
        Call<FdaApiResponse> getSubstanceData(@Query("search") String searchQuery, @Header("Authorization") String authorization);
    }

    // Model class for FDA API response
    public class FdaApiResponse {
        // Define your model based on the JSON response structure
        // Example: Assume a field called 'results' is present in the JSON
        private List<Substance> results;

        public List<Substance> getResults() {
            return results;
        }
    }

    // Model class for the 'substance' object in the FDA API response
    public class Substance {
        // Define fields based on the actual structure of 'substance' in the JSON response
        // Example: Assume a field called 'code' is present in the 'substance' object
        private String code;

        public String getCode() {
            return code;
        }
    }

    private void getFdaData(String searchCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.fda.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FdaApi fdaApi = retrofit.create(FdaApi.class);

        Call<FdaApiResponse> call = fdaApi.getSubstanceData("codes.code:" + searchCode, "Bearer " + FDA_API_KEY);
        call.enqueue(new Callback<FdaApiResponse>() {
            @Override
            public void onResponse(Call<FdaApiResponse> call, Response<FdaApiResponse> response) {
                if (response.isSuccessful())
                {
                    FdaApiResponse fdaData = response.body();
                    if (fdaData != null && fdaData.getResults() != null && !fdaData.getResults().isEmpty()) {
                        Substance substance = fdaData.getResults().get(0);
                        // Access other fields as needed, for example:
                        String substanceCode = substance.getCode();
                        Toast.makeText(BarCodeActivity.this, "FDA API Substance Code: " + substanceCode, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(BarCodeActivity.this, "FDA API Data not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BarCodeActivity.this, "Failed to get FDA API data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FdaApiResponse> call, Throwable t) {
                Toast.makeText(BarCodeActivity.this, "FDA API Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
