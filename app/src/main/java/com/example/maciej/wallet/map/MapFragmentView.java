package com.example.maciej.wallet.map;

import com.example.maciej.wallet.base.BaseView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by maciej on 09/10/16.
 */
public interface MapFragmentView extends BaseView {

    void setTitle(String text);

    void close();

    void hideInMyPocketButton();

    void setMapCamera(LatLng location);

    void setViewForShowingWalletPosition();
}
