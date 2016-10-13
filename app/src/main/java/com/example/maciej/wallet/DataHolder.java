package com.example.maciej.wallet;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by maciej on 08/10/16.
 */
public class DataHolder {
    public static String SHARED_PREF_KEY = "locations";

    public static String USER_LOCATION = "USER_LOCATION";
    public static String CAR_LOCATION = "CAR_LOCATION";
    public static String WALLET_LOCATION = "WALLET_LOCATION";
    public static String LAT = "LAT";
    public static String LNG = "LON";
    public static String IN_POCKET = "IN_POCKET";
    public static String TRACKING = "TRACKING";

    private static LatLng userLocation;
    private static LatLng carLocation;
    private static LatLng walletLocation;
    private static boolean walletInPocket;
    private static boolean tracking;

    public static void init(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        userLocation = getLocationFromPref(preferences, USER_LOCATION);
        carLocation = getLocationFromPref(preferences, CAR_LOCATION);
        walletLocation = getLocationFromPref(preferences, WALLET_LOCATION);
        walletInPocket = preferences.getBoolean(IN_POCKET, false);
        tracking = preferences.getBoolean(TRACKING, false);
    }

    private static LatLng getLocationFromPref(SharedPreferences preferences, String KEY) {
        float latitude = preferences.getFloat(KEY + LAT, 0);
        float longitude = preferences.getFloat(KEY + LNG, 0);
        return new LatLng(latitude, longitude);
    }

    public static void setUserLocation(Context context, LatLng userLocation) {
        if (userLocation != null) {
            DataHolder.userLocation = userLocation;
            setLocation(context, userLocation, USER_LOCATION);
        }
    }

    public static void setCarLocation(Context context, LatLng carLocation) {
        DataHolder.carLocation = carLocation;
        setLocation(context, carLocation, CAR_LOCATION);

    }

    public static void setWalletLocation(Context context, LatLng walletLocation) {
        DataHolder.walletLocation = walletLocation;
        setWalletInPocket(context, false);
        setLocation(context, walletLocation, WALLET_LOCATION);

    }

    public static void setWalletInPocket(Context context, boolean walletInPocket) {
        DataHolder.walletInPocket = walletInPocket;
        setBoolean(context, walletInPocket, IN_POCKET);
    }

    public static void setTracking(Context context, boolean tracking) {
        DataHolder.tracking = tracking;
        setBoolean(context, tracking, TRACKING);
    }

    private static void setBoolean(Context context, boolean val, String KEY) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY, val);
        editor.apply();
    }

    private static void setLocation(Context context, LatLng location, String KEY) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(KEY + LAT, (float) location.latitude);
        editor.putFloat(KEY + LNG, (float) location.longitude);
        editor.apply();
    }

    public static LatLng getUserLocation() {
        return userLocation;
    }

    public static LatLng getCarLocation() {
        return carLocation;
    }

    public static LatLng getWalletLocation() {
        return walletLocation;
    }

    public static boolean isWalletInPocket() {
        return walletInPocket;
    }

    public static boolean isTracking() {
        return tracking;
    }
}
