package com.example.erik.laboration5_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 1/5 001.
 */
public class SoundManager {
    private static SoundManager manager = null;
    private SoundPool sndPool;
    private String sndFamily;

    private SoundManager() {
        sndPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
//        sndFamily = "mamacita_us";
//        mapSounds("mamacita_us");
    }

    public static SoundManager getInstance() {
        if(manager == null)
            manager = new SoundManager();
        return manager;
    }

/*    public int loadSnd(char ch) {
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
    }*/

    Map<Character, Integer> charSound = new HashMap<>();
//    public void mapSounds(String newFamily) {
    public void mapSounds(SharedPreferences prefs) {
        String newFamily = prefs.getString("voice", "mamacita_us");
        System.out.println(newFamily);
        if(newFamily.equals(sndFamily)) // if sound family is unchanged
            return;

//        sndPool.release();
        sndFamily = newFamily;

        char[] chars = {'1','2','3','4','5','6','7','8','9','0','#','*'};
        File dir = Environment.getExternalStorageDirectory();
        String fileName = null;

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
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
//            File sndFile = new File(sndFamily+"/"+fileName+".mp3");
            charSound.put(ch, sndPool.load(sndFile.getAbsolutePath(), 1));
//            return sndPool.load(sndFile.getAbsolutePath(), 1);
        }

    }

    public boolean playSound(char value) {
        if(!isExternalStorageReadable())
            return false;

        int sndID = charSound.get(value);
        sndPool.play(sndID, 1, 1, 0, 0, 1);
        return true;
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
