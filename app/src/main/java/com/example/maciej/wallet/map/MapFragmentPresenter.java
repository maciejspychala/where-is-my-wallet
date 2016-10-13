package com.example.maciej.wallet.map;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.maciej.wallet.DataHolder;
import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BasePresenter;
import com.google.android.gms.maps.model.LatLng;

import static com.example.maciej.wallet.map.MapFragment.CAR;
import static com.example.maciej.wallet.map.MapFragment.SHOW_WALLET;
import static com.example.maciej.wallet.map.MapFragment.WALLET;

/**
 * Created by maciej on 11/10/16.
 */
public class MapFragmentPresenter extends BasePresenter<MapFragmentView> implements MapFragmentPresenterInterface {

    MapModel model;

    public MapFragmentPresenter(MapFragmentView view, Bundle arguments, Resources resources) {
        super(view);
        model = new MapModel(arguments.getInt(MapFragment.KEY));
        onCreate(resources);
    }

    @Override
    public void onCreate(Resources resources) {
        if (model.key == CAR) {
            view.hideInMyPocketButton();
        }
        if (model.key == SHOW_WALLET) {
            view.setViewForShowingWalletPosition();
        }
        view.setTitle(formatTitle(resources));
    }

    @Override
    public void onMapLoaded() {
        view.setMapCamera(DataHolder.getUserLocation());
    }

    @Override
    public void setWalletInPocket(Context context) {
        DataHolder.setWalletInPocket(context, true);
        view.close();
    }


    @Override
    public void positionChosen(LatLng location, Context context) {
        if (model.key == CAR) {
            DataHolder.setCarLocation(context, location);
        } else {
            DataHolder.setWalletInPocket(context, false);
            DataHolder.setWalletLocation(context, location);
        }
        view.close();
    }

    @Override
    public String formatTitle(Resources resources) {
        String title = "";
        if (model.key == SHOW_WALLET) {
            title = resources.getString(R.string.wallet_position);
        } else {
            String.format(resources.getString(R.string.map_title),
                    model.key == WALLET ? resources.getString(R.string.wallet) : resources.getString(R.string.car));
        }
        return title;
    }
}
