package com.example.app.utils;

import android.location.Location;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationProviderByMe{

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location me;
    private boolean located = false;

    public LocationProviderByMe(FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;

    }

    public Location getMe() {
        return me;
    }

    public boolean isLocated() {
        return located;
    }
}
