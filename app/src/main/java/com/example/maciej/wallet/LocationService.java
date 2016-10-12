package com.example.maciej.wallet;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by maciej on 08/10/16.
 */
public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleApiClient googleApiClient;
    private final int TIME_INTERVAL = 10000;
    private final int MIN_TIME_INTERVAL = 5000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        googleApiClient = new GoogleApiClient.Builder(getBaseContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        DataHolder.setUserLocation(getBaseContext(), locationToLatLng(LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient)));
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(TIME_INTERVAL);
        locationRequest.setFastestInterval(MIN_TIME_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    private LatLng locationToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        checkDistanceToCar(location);
        DataHolder.setUserLocation(getBaseContext(), locationToLatLng(location));
    }

    private void checkDistanceToCar(Location location) {
        if (DataHolder.getCarLocation() == null) {
            DataHolder.init(getBaseContext());
        }
        try {
            Location car = new Location("");
            car.setLatitude(DataHolder.getCarLocation().latitude);
            car.setLongitude(DataHolder.getCarLocation().longitude);
            location.setAccuracy(0f);
            float distance = location.distanceTo(new Location(car));
            if (distance < 20.0f) {
                Toast.makeText(getBaseContext(), "in a car. Distance: " + Float.toString(distance), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
