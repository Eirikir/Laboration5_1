package com.example.erik.laboration5_1;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Erik on 20/4 020.
 */

public class DialPad extends TableLayout {
    private static SoundPool sndPool; // Ugly solution, please forgive me :)
//    private Map<Character, Dial> dials = new HashMap<>();

    public DialPad(Context context, AttributeSet attrs) {
        super(context, attrs);

        sndPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        setFocusableInTouchMode(true);
        requestFocus();

        // listen to hardware keyboard
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                int action = event.getAction();
                if (action != KeyEvent.ACTION_DOWN) // ignore repeats
                    return true;

                int value = event.getUnicodeChar(); // '#' & '*' demands unicode value

                Dial dial = null;
                switch (value) {
                    case '1': dial = (Dial) findViewById(R.id.dial_1); break;
                    case '2': dial = (Dial) findViewById(R.id.dial_2); break;
                    case '3': dial = (Dial) findViewById(R.id.dial_3); break;
                    case '4': dial = (Dial) findViewById(R.id.dial_4); break;
                    case '5': dial = (Dial) findViewById(R.id.dial_5); break;
                    case '6': dial = (Dial) findViewById(R.id.dial_6); break;
                    case '7': dial = (Dial) findViewById(R.id.dial_7); break;
                    case '8': dial = (Dial) findViewById(R.id.dial_8); break;
                    case '9': dial = (Dial) findViewById(R.id.dial_9); break;
                    case '0': dial = (Dial) findViewById(R.id.dial_0); break;
                    case '#': dial = (Dial) findViewById(R.id.dial_p); break;
                    case '*': dial = (Dial) findViewById(R.id.dial_s); break;
                }

                if(dial != null)    // send press notification to proper dial
                    dial.keyPress();
                return true;
            }
        });

    }

    public static int loadSnd(Context context, char ch) {
/*        if(isExternalStorageReadable())
            System.out.println("readable!");
*/
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

        File sndFile = new File(dir, "/dialpad/sounds/mamacita_us/"+fileName+".mp3");
        return sndPool.load(sndFile.getAbsolutePath(), 1);
    }

    public static boolean playSnd(int soundID) {
        if(!isExternalStorageReadable())
            return false;


        sndPool.play(soundID, 1, 1, 0, 0, 1);
        return true;
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
            return true;
        return false;
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;
        return false;
    }

}
