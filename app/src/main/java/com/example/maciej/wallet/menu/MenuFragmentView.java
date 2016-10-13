package com.example.maciej.wallet.menu;

import com.example.maciej.wallet.base.BaseView;

/**
 * Created by maciej on 09/10/16.
 */
public interface MenuFragmentView extends BaseView {
    void openMapFragment(int item);

    void setTrackingCheckBox(boolean val);
}
