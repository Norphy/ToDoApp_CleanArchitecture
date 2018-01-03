package com.example.orphy.domain;

import io.reactivex.Scheduler;

/**
 * Created on 12/10/2017.
 */

public interface UIThread {

    Scheduler getUiThread();
}
