package com.example.maciej.wallet.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;

/**
 * Created by maciej on 11/10/16.
 */
public class MapFragment extends BaseFragment<MapFragmentPresenterInterface> implements MapFragmentView, OnMapReadyCallback {

    @BindView(R.id.map_view)
    MapView mapView;

    @BindView(R.id.map_title)
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected MapFragmentPresenterInterface initFragmentPresenter() {
        return new MapFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void setTitle(String text) {
        title.setText(text);
    }

    @Override
    public void close() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public enum Item {CAR, WALLET}
}
