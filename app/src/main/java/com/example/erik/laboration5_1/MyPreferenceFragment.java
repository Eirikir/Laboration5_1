package com.example.erik.laboration5_1;

import android.os.Bundle;
import android.os.Environment;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 30/4 030.
 */
public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        // ok, now for the voice list
        ListPreference voice = (ListPreference) getPreferenceScreen().findPreference("voice");

        // get all available voices
        File baseDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dialpad/sounds/");
        String[] entries = baseDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return new File(dir, filename).isDirectory();   // add directories to array
            }
        });

        voice.setEntries(entries);
        voice.setEntryValues(entries);  // reuse array, since we only use file names
    }
}
