package com.example.erik.laboration5_1;

import android.media.SoundPool;

/**
 * Created by Erik on 1/5 001.
 */
public class SoundManager {
    private SoundPool sp;
    private static SoundManager manager = null;
    private SoundManager() {

    }

    public static SoundManager getInstance() {
        if(manager == null)
            manager = new SoundManager();
        return manager;
    }
}
