package com.li.ramon.accessinternet.parserxml;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.li.ramon.accessinternet.R;

/*实现一个首选项 Activity*/
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*现在推荐使用 PreferenceFragment 该方法过时了*/
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registers a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregisters the listener set in onResume().
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /* Sets refreshDisplay to true so that when the user returns to the main
           activity, the display refreshes to reflect the new settings.*/
        NetworkActivity.refreshDisplay = true;
    }
}
