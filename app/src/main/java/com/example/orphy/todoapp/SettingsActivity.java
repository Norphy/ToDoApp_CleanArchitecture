package com.example.orphy.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.orphy.data.SharedPreferencesManager;

/**
 * Created on 1/3/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        if(sharedPreferencesManager.read(SharedPreferencesManager.DARK_THEME_PREFERENCE_KEY, false)) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

}
