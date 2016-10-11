package com.example.maciej.wallet.map;

import android.widget.TextView;

import com.example.maciej.wallet.R;
import com.example.maciej.wallet.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by maciej on 11/10/16.
 */
public class MapFragment extends BaseFragment<MapFragmentPresenterInterface> implements MapFragmentView {

    public enum Item {CAR, WALLET};

    @BindView(R.id.map_title)
    TextView title;

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
}
