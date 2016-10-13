package com.example.maciej.wallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.maciej.wallet.map.MapFragment;
import com.example.maciej.wallet.menu.MenuFragment;
import com.example.maciej.wallet.splash.SplashFragment;

public class MainActivity extends AppCompatActivity {

    public static final String SHOW_WALLET = "SHOW_WALLET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder.init(this);
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean(SHOW_WALLET, false)) {
            showFragment(MapFragment.newInstance(MapFragment.SHOW_WALLET));
        } else if (savedInstanceState == null) {
            checkForPermission();
        }
    }


    public void showFragment(Fragment fragment) {
        showFragment(fragment, false);
    }

    public void showFragment(Fragment fragment, boolean backstack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_root, fragment);
        if (backstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showFragment(new SplashFragment());
        } else {
            startLocationService();
            showFragment(new MenuFragment());
        }
    }

    public void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }
}
