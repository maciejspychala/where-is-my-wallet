package com.example.maciej.wallet.map;

import android.location.Location;

import com.example.maciej.wallet.base.BasePresenterInterface;

/**
 * Created by maciej on 10/10/16.
 */
public interface MapFragmentPresenterInterface extends BasePresenterInterface {

    void positionChosen(Location location);

    String formatTitle(String title);
}
