package com.example.maciej.wallet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by maciej on 10/10/16.
 */
public abstract class BaseFragment<T extends BasePresenterInterface> extends Fragment implements BaseView {

    public Unbinder unbinder;
    public T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = initFragmentPresenter();
        return view;
    }

    protected abstract T initFragmentPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public abstract int getLayoutId();
}

