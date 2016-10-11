package com.example.maciej.wallet.map;

import android.location.Location;

import com.example.maciej.wallet.base.BasePresenter;

/**
 * Created by maciej on 11/10/16.
 */
public class MapFragmentPresenter extends BasePresenter<MapFragmentView> implements MapFragmentPresenterInterface {

    public MapFragmentPresenter(MapFragmentView view) {
        super(view);
    }

    @Override
    public void positionChosen(Location location) {

    }

    @Override
    public String formatTitle(String title) {
        return null;
    }
}
