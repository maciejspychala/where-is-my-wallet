package com.example.maciej.wallet.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by maciej on 11/10/16.
 */
public class MapFragment extends BaseFragment<MapFragmentPresenterInterface> implements MapFragmentView, OnMapReadyCallback {

    public static int CAR = 1;
    public static int WALLET = 2;
    public static final String KEY = "KEY";
    private GoogleMap map;

    @BindView(R.id.map_view)
    MapView mapView;

    @BindView(R.id.map_title)
    TextView title;

    @BindView(R.id.in_my_pocket)
    Button inPocket;

    public static MapFragment newInstance(int item) {
        Bundle args = new Bundle();
        args.putInt(KEY, item);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }


    @Override
    protected MapFragmentPresenterInterface initFragmentPresenter() {
        return new MapFragmentPresenter(this, getArguments(), getResources());
    }

    @OnClick(R.id.set_position)
    public void setPosition() {
        presenter.positionChosen(map.getCameraPosition().target, getContext());
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
    public void hideInMyPocketButton() {
        inPocket.setVisibility(View.GONE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
