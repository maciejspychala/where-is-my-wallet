package com.example.maciej.wallet.map;

import android.content.Context;
import android.content.res.Resources;

import com.example.maciej.wallet.base.BasePresenterInterface;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by maciej on 10/10/16.
 */
public interface MapFragmentPresenterInterface extends BasePresenterInterface {

    void positionChosen(LatLng location, Context context);

    String formatTitle(Resources resources);

    void onCreate(Resources resources);

    void onMapLoaded();
}
