package com.example.erik.laboration5_1;

import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Created by Erik on 30/4 030.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    final static String ACTION_PREFS_ONE = "com.example.erik.laboration5_1.PREFS_ONE";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        // listen to changes to default preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals("voice")) // if voice is changed, map new sounds to soundmanager
        {
            SoundManager.getInstance().mapSounds(prefs);
//            String newFamily = prefs.getString("voice", "mamacita_us");
//            SoundManager.getInstance().mapSounds(newFamily);
        }
    }

}
