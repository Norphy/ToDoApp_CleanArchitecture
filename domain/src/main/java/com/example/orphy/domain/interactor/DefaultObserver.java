package com.example.orphy.domain.interactor;

import io.reactivex.observers.DisposableObserver;

/**
 * Created on 12/12/2017.
 */

public class DefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
