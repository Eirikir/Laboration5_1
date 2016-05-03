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
    public DialPad(Context context, AttributeSet attrs) {
        super(context, attrs);

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

}
