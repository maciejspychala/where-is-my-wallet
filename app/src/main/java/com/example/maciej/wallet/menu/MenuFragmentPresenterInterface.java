package com.example.maciej.wallet.menu;

import android.app.Activity;

import com.example.maciej.wallet.base.BasePresenterInterface;

/**
 * Created by maciej on 10/10/16.
 */
public interface MenuFragmentPresenterInterface extends BasePresenterInterface {
    void itemSelected(int item);

    void setTracking(Activity activity);
}
