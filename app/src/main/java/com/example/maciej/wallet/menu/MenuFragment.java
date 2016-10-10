package com.example.maciej.wallet.menu;

import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by maciej on 07/10/16.
 */
public class MenuFragment extends BaseFragment<MenuFragmentPresenterInterface> implements MenuFragmentView {


    @Override
    protected MenuFragmentPresenterInterface initFragmentPresenter() {
        return new MenuFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @OnClick(R.id.menu_car)
    public void openMapFragmentForCar() {
        //TODO open mapFragment for car
    }

    @OnClick(R.id.menu_wallet)
    public void openMapFragmentForWallet() {
        //TODO open mapFragment for wallet
    }

    @Override
    public void openMapFragment(String key) {
        //TODO open mapFragment for key
    }
}
