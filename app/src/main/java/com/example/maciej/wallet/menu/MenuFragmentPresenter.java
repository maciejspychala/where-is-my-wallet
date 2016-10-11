package com.example.maciej.wallet.menu;

import com.example.maciej.wallet.base.BasePresenter;
import com.example.maciej.wallet.map.MapFragment;

/**
 * Created by maciej on 09/10/16.
 */
public class MenuFragmentPresenter extends BasePresenter<MenuFragmentView> implements MenuFragmentPresenterInterface {

    public MenuFragmentPresenter(MenuFragmentView view) {
        super(view);
    }

    @Override
    public void itemSelected(MapFragment.Item item) {
        view.openMapFragment(item);
    }
}
