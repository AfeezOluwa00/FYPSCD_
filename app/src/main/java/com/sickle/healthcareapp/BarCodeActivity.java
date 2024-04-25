package com.sickle.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mysicklecellapp.R;

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class BarCodeActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private ZBarScannerView scannerView;

    // Replace "YOUR_API_KEY" with your actual API key from upcdatabase.org
    private static final String UPC_DATABASE_API_KEY = "YOUR_API_KEY";

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

        // Start the scanner
        scannerView.setResultHandler(result -> {
            String scannedContent = result.getContents();
            getProductInformation(scannedContent);
        });
        scannerView.startCamera(); // Start camera on resume
    }

    private void getProductInformation(String barcode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.upcdatabase.org/product/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UpcDatabaseApi upcDatabaseApi = retrofit.create(UpcDatabaseApi.class);

        Call<UpcDatabaseResponse> call = upcDatabaseApi.getProductInfo(barcode, "Token " + UPC_DATABASE_API_KEY);
        call.enqueue(new Callback<UpcDatabaseResponse>() {
            @Override
            public void onResponse(Call<UpcDatabaseResponse> call, Response<UpcDatabaseResponse> response) {
                if (response.isSuccessful()) {
                    UpcDatabaseResponse productInfo = response.body();
                    if (productInfo != null && productInfo.getItem() != null) {
                        String productName = productInfo.getItem().getTitle();
                        Toast.makeText(BarCodeActivity.this, "Product Name: " + productName, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(BarCodeActivity.this, "Product information not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BarCodeActivity.this, "Failed to get product information", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            @Override
            public void onFailure(Call<UpcDatabaseResponse> call, Throwable t) {
                Toast.makeText(BarCodeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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

    // Retrofit interface for upcdatabase.org API
    public interface UpcDatabaseApi {
        @GET("{id}")
        Call<UpcDatabaseResponse> getProductInfo(@Path("id") String id, @Header("Authorization") String authorization);
    }

    // Model class for the API response
    public class UpcDatabaseResponse {
        private Item item;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    public class Item {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
