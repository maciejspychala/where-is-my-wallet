package com.example.maciej.wallet.map;

import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BasePresenter;

import butterknife.BindView;

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
        if (model.key == MapFragment.CAR) {
            view.hideInMyPocketButton();
        }
        view.setTitle(formatTitle(resources));
    }

    @Override
    public void positionChosen(Location location) {

    }

    @Override
    public String formatTitle(Resources resources) {
        return String.format(resources.getString(R.string.map_title),
                model.key == WALLET ? resources.getString(R.string.wallet) : resources.getString(R.string.car));
    }
}
