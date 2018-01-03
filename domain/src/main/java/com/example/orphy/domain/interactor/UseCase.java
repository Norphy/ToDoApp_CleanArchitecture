package com.example.orphy.domain.interactor;

import com.example.orphy.domain.UIThread;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 12/7/2017.
 */

public abstract class UseCase<T, Params> {

    private final CompositeDisposable disposables;
    private final UIThread uiThread;

    UseCase(UIThread uiThread) {
        this.disposables = new CompositeDisposable();
        this.uiThread = uiThread;
    }

    abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(DisposableObserver<T> observer, Params params) {

        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(this.uiThread.getUiThread());
        addDisposable(observable.subscribeWith(observer));
    }

    public void dispose() {
        if(!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
