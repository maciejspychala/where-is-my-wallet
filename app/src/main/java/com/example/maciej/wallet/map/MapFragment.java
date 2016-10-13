package com.example.maciej.wallet.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maciej.wallet.DataHolder;
import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by maciej on 11/10/16.
 */
public class MapFragment extends BaseFragment<MapFragmentPresenterInterface> implements MapFragmentView, OnMapReadyCallback {

    public static int CAR = 1;
    public static int WALLET = 2;
    public static int SHOW_WALLET = 3;
    public static final String KEY = "KEY";
    private GoogleMap map;
    private boolean showWallet;

    @BindView(R.id.map_view)
    MapView mapView;

    @BindView(R.id.map_title)
    TextView title;

    @BindView(R.id.in_my_pocket)
    Button inPocket;

    @BindView(R.id.map_buttons)
    View buttonsLayout;

    @BindView(R.id.map_pin)
    ImageView mapPin;

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
        if (map != null) {
            presenter.positionChosen(map.getCameraPosition().target, getContext());
        }
    }

    @OnClick(R.id.in_my_pocket)
    public void setWalletInPocket() {
        presenter.setWalletInPocket(getContext());
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
    public void setMapCamera(LatLng location) {
        if (map != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.5f));
        }
    }

    @Override
    public void setViewForShowingWalletPosition() {
        showWallet = true;
        if (map != null) {
            map.addMarker(new MarkerOptions().position(DataHolder.getWalletLocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_balance_wallet_black_48dp)));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(DataHolder.getWalletLocation(), 13.5f));
            showWallet = false;
        }
        mapPin.setVisibility(View.INVISIBLE);
        buttonsLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (!showWallet) {
            presenter.onMapLoaded();
        } else {
            setViewForShowingWalletPosition();
        }
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
