package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView textView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewId);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        getLocation(locationManager);

    }

    public Location getLocation(LocationManager locationManager) {
        this.locationManager = locationManager;
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!isGPSEnabled && !isNetworkEnabled) {
            // No location providers are enabled
            Log.e("LocationTracker", "No location providers are enabled.");
        } else {
            if (isNetworkEnabled) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        String locations = DB.getLocationAddress(MainActivity.this, location.getLatitude(), location.getLongitude());
                        textView.setText(locations.toString());
                    }
                } catch (SecurityException e) {
                    Log.e("LocationTracker", "No permission to access location data.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (isGPSEnabled) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                } catch (SecurityException e) {
                    Log.d("LocationTracker", "No permission to access location data.");
                }
            }
        }
        return location;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}