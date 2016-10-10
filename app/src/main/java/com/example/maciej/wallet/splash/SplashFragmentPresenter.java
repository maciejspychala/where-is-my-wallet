package com.example.maciej.wallet.splash;

import com.example.maciej.wallet.base.BasePresenter;

/**
 * Created by maciej on 10/10/16.
 */
public class SplashFragmentPresenter extends BasePresenter<SplashFragmentView> implements SplashFragmentPresenterInterface {

    public SplashFragmentPresenter(SplashFragmentView view) {
        super(view);
    }

    public void onPermissionGranted() {
        view.displayMenu();
    }
}
