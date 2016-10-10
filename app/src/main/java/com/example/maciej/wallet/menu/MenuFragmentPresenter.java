package com.example.maciej.wallet.menu;

import com.example.maciej.wallet.base.BasePresenter;

/**
 * Created by maciej on 09/10/16.
 */
public class MenuFragmentPresenter extends BasePresenter<MenuFragmentView> implements MenuFragmentPresenterInterface {

    public MenuFragmentPresenter(MenuFragmentView view) {
        super(view);
    }

    public void itemSelected(String key) {
        view.openMapFragment(key);
    }
}
