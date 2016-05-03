package com.example.erik.laboration5_1;

import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by Erik on 30/4 030.
 */
public class SettingsActivity extends PreferenceActivity {
    final static String ACTION_PREFS_ONE = "com.example.erik.laboration5_1.PREFS_ONE";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();



/*        String action = getIntent().getAction();
        if(action != null && action.equals(ACTION_PREFS_ONE)) {
            addPreferencesFromResource(R.xml.preferences);
        }

        else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            addPreferencesFromResource(R.xml.preference_headers_legacy);
        }*/
    }

    // only called on Honeycomb and later
/*    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_preferences, target);
    }

    @Override
    public boolean isValidFragment(String fragmentName) {
        return MyPreferenceFragment.class.getName().equals(fragmentName);
    }*/
}
