package com.example.orphy.todoapp;

import com.example.orphy.domain.UIThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created on 12/12/2017.
 */

public class UiThread implements UIThread {

    public UiThread() {}

    @Override
    public Scheduler getUiThread() {
        return AndroidSchedulers.mainThread();
    }
}
