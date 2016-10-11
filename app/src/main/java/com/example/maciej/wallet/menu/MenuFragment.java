package com.example.maciej.wallet.menu;

import com.example.maciej.wallet.MainActivity;
import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;
import com.example.maciej.wallet.map.MapFragment;

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
        presenter.itemSelected(MapFragment.Item.CAR);
    }

    @OnClick(R.id.menu_wallet)
    public void openMapFragmentForWallet() {
        presenter.itemSelected(MapFragment.Item.WALLET);
    }

    @Override
    public void openMapFragment(MapFragment.Item item) {
        ((MainActivity) getActivity()).showFragment(new MapFragment(), true);
    }
}
