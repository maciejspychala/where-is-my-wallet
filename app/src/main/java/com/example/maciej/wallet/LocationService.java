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
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        saveLastLocation();
        initLocationUpdates();
    }


    void saveLastLocation() {
        if (checkForPermission() && googleApiClient != null && googleApiClient.isConnected()) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                DataHolder.setUserLocation(getBaseContext(), locationToLatLng(lastLocation));
            }
        }
    }

    private synchronized void initLocationUpdates() {
        initDataHolder();
        if (checkForPermission() && DataHolder.isTracking() && googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    private boolean checkForPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            stopSelf();
            return false;
        }
        return true;
    }

    private synchronized void removeLocationUpdates() {
        initDataHolder();
        if (!DataHolder.isTracking() && googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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
            if (distance < 30.0f && DataHolder.isTracking()) {
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
        DataHolder.setTracking(getBaseContext(), false);
        removeLocationUpdates();
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
