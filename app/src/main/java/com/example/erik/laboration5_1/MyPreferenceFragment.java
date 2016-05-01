package com.example.erik.laboration5_1;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Erik on 30/4 030.
 */
public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
