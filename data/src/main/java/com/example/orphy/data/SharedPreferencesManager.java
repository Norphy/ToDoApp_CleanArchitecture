package com.example.orphy.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created on 1/4/2018.
 */

public class SharedPreferencesManager {

    private static SharedPreferencesManager INSTANCE;
    public static final String DARK_THEME_PREFERENCE_KEY = "dark_theme";
    public static final String RECREATE_ACTIVITY_KEY = "recreate_activity_key";
    private final SharedPreferences mSharedPreferences;

    private static SharedPreferences.OnSharedPreferenceChangeListener listener;

    private SharedPreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferencesManager getInstance(final Context context) {

        if(INSTANCE == null) {
            return INSTANCE = new SharedPreferencesManager(context);
        }

        return INSTANCE;
    }

    public static void setListener(Context context) {

    }

    public static SharedPreferencesManager getInstance() {
        if(INSTANCE != null) {
            return INSTANCE;
        }

        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }



    public boolean read(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public void write(String key, boolean value) {
        SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putBoolean(key, value);
        sharedPrefEditor.apply();
    }





}
