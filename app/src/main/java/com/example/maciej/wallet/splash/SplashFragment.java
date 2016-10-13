package com.example.maciej.wallet.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.example.maciej.wallet.MainActivity;
import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;
import com.example.maciej.wallet.menu.MenuFragment;

import butterknife.OnClick;

/**
 * Created by maciej on 07/10/16.
 */
public class SplashFragment extends BaseFragment<SplashFragmentPresenterInterface> implements SplashFragmentView {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private boolean permissionGranted;

    @Override
    protected SplashFragmentPresenterInterface initFragmentPresenter() {
        return new SplashFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionGranted) {
            permissionGranted = false;
            ((MainActivity) getActivity()).showFragment(new MenuFragment());
        }
    }

    @OnClick(R.id.permission_button)
    public void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onPermissionGranted();
                }
                break;
        }
    }

    @Override
    public void displayMenu() {
        ((MainActivity) getActivity()).startLocationService();
        if (isResumed()) {
            ((MainActivity) getActivity()).showFragment(new MenuFragment());
        } else {
            permissionGranted = true;
        }
    }
}
