package com.example.maciej.wallet.base;

/**
 * Created by maciej on 10/10/16.
 */
public class BasePresenter<T extends BaseView> implements BasePresenterInterface {
    public T view;

    public BasePresenter(T view) {
        this.view = view;
    }
}
