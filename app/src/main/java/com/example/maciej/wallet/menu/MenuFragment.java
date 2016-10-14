package com.example.maciej.wallet.menu;

import android.widget.CheckBox;

import com.example.maciej.wallet.MainActivity;
import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;
import com.example.maciej.wallet.map.MapFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by maciej on 07/10/16.
 */
public class MenuFragment extends BaseFragment<MenuFragmentPresenterInterface> implements MenuFragmentView {

    @BindView(R.id.tracking_checkbox)
    CheckBox trackingCheckBox;

    @Override
    protected MenuFragmentPresenterInterface initFragmentPresenter() {
        return new MenuFragmentPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @OnClick(R.id.menu_car)
    public void openMapFragmentForCar() {
        presenter.itemSelected(MapFragment.CAR);
    }

    @OnClick(R.id.menu_wallet)
    public void openMapFragmentForWallet() {
        presenter.itemSelected(MapFragment.WALLET);
    }

    @OnClick(R.id.tracking_button)
    public void setTracking() {
        presenter.setTracking(getActivity());
    }

    @Override
    public void openMapFragment(int item) {
        ((MainActivity) getActivity()).showFragment(MapFragment.newInstance(item), true);
    }

    @Override
    public void setTrackingCheckBox(boolean val) {
        trackingCheckBox.setChecked(val);
    }
}
