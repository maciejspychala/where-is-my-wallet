package com.example.maciej.wallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
