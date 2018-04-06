package com.example.orphy.todoapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.example.orphy.data.SharedPreferencesManager;

/**
 * Created on 1/5/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_pref_frag);
        sharedPreferencesManager = SharedPreferencesManager.getInstance();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(sharedPreferencesManager.DARK_THEME_PREFERENCE_KEY)) {
            Toast.makeText(getActivity(), "Theme Preferences changed", Toast.LENGTH_SHORT).show();
            getActivity().recreate();

            sharedPreferencesManager.write(sharedPreferencesManager.RECREATE_ACTIVITY_KEY, true);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
