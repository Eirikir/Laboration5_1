package com.example.erik.laboration5_1;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;

import java.io.File;

/**
 * Created by Erik on 1/5 001.
 */
public class SoundManager {
    private static SoundManager manager = null;
    private SoundPool sndPool;
    private String sndFamily;

    private SoundManager() {
        sndPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sndFamily = "mamacita_us";
    }

    public static SoundManager getInstance() {
        if(manager == null)
            manager = new SoundManager();
        return manager;
    }

    public int loadSnd(char ch) {
        File dir = Environment.getExternalStorageDirectory();
        String fileName = null;
        switch(ch) {
            case '0' : fileName = "zero"; break;
            case '1' : fileName = "one"; break;
            case '2' : fileName = "two"; break;
            case '3' : fileName = "three"; break;
            case '4' : fileName = "four"; break;
            case '5' : fileName = "five"; break;
            case '6' : fileName = "six"; break;
            case '7' : fileName = "seven"; break;
            case '8' : fileName = "eight"; break;
            case '9' : fileName = "nine"; break;
            case '#' : fileName = "pound"; break;
            case '*' : fileName = "star"; break;
        }

        File sndFile = new File(dir, "/dialpad/sounds/"+sndFamily+"/"+fileName+".mp3");
        return sndPool.load(sndFile.getAbsolutePath(), 1);
    }

    public void setSndFamily(String family) {
        sndFamily = family;
    }

    public boolean playSnd(int soundID) {
        if(!isExternalStorageReadable())
            return false;


        sndPool.play(soundID, 1, 1, 0, 0, 1);
        return true;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
            return true;
        return false;
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;
        return false;
    }
}
