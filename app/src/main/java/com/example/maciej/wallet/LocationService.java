package com.example.maciej.wallet;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
    private final int TIME_INTERVAL = 15000;
    private final int MIN_TIME_INTERVAL = 10000;
    private LocationRequest locationRequest;
    private boolean tracking;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLocationRequest();
        initGoogleClient();
    }

    private synchronized void initGoogleClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getBaseContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            googleApiClient.connect();
        }
    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(TIME_INTERVAL);
        locationRequest.setFastestInterval(MIN_TIME_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(LocationService.this, "new location", Toast.LENGTH_SHORT).show();
        saveLastLocation();
        initLocationUpdates();
    }


    void saveLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (googleApiClient != null && googleApiClient.isConnected()) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                DataHolder.setUserLocation(getBaseContext(), locationToLatLng(lastLocation));
            }
        }
    }

    private synchronized void initLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        initDataHolder();
        if (DataHolder.isTracking() && googleApiClient != null && googleApiClient.isConnected() && !tracking) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            tracking = true;
        }
    }

    private synchronized void removeLocationUpdates() {
        initDataHolder();
        if (!DataHolder.isTracking() && googleApiClient != null && googleApiClient.isConnected() && tracking) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            tracking = false;
        }
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
        DataHolder.setUserLocation(getBaseContext(), locationToLatLng(location));
        checkDistanceToCar(location);
    }

    private void checkDistanceToCar(Location location) {
        initDataHolder();
        try {
            Location car = new Location("");
            car.setLatitude(DataHolder.getCarLocation().latitude);
            car.setLongitude(DataHolder.getCarLocation().longitude);
            location.setAccuracy(0f);
            float distance = location.distanceTo(new Location(car));
            if (distance < 25.0f && tracking) {
                createNotification();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int start = super.onStartCommand(intent, flags, startId);
        initGoogleClient();
        initDataHolder();
        if (DataHolder.isTracking()) {
            initLocationUpdates();
        } else {
            removeLocationUpdates();
        }
        saveLastLocation();
        return start;
    }


    private void initDataHolder() {
        if (DataHolder.getCarLocation() == null) {
            DataHolder.init(getBaseContext());
        }
    }

    private void createNotification() {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setColor(0xff0c12e1)
                        .setVibrate(new long[]{100, 100, 100, 100})
                        .setSmallIcon(R.drawable.ic_account_balance_wallet_black_48dp);

        if (DataHolder.isWalletInPocket()) {
            notificationBuilder.setContentTitle(getString(R.string.congrats))
                    .setContentText(getString(R.string.reach_car_with_wallet));
        } else {
            notificationBuilder.setContentTitle(getString(R.string.ups))
                    .setContentText(getString(R.string.go_back_for_wallet));
            setIntentForNotification(notificationBuilder);
        }

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notificationBuilder.build());
        tracking = false;
        DataHolder.setTracking(getBaseContext(), false);
    }

    private void setIntentForNotification(NotificationCompat.Builder notificationBuilder) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainActivity.SHOW_WALLET, true);
        resultIntent.putExtras(bundle);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);
    }
}
