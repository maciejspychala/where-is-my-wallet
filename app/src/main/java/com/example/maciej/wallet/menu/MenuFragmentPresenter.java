package com.example.maciej.wallet.menu;

import android.app.Activity;

import com.example.maciej.wallet.DataHolder;
import com.example.maciej.wallet.MainActivity;
import com.example.maciej.wallet.base.BasePresenter;

/**
 * Created by maciej on 09/10/16.
 */
public class MenuFragmentPresenter extends BasePresenter<MenuFragmentView> implements MenuFragmentPresenterInterface {

    public MenuFragmentPresenter(MenuFragmentView view) {
        super(view);
    }

    @Override
    public void itemSelected(int item) {
        view.openMapFragment(item);
    }

    @Override
    public void setTracking(Activity activity) {
        DataHolder.setTracking(activity.getBaseContext(), !DataHolder.isTracking());
        ((MainActivity) activity).startLocationService();
        view.setTrackingCheckBox(DataHolder.isTracking());
    }
}
