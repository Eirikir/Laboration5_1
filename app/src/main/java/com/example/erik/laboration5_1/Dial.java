package com.example.erik.laboration5_1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Erik on 21/4 021.
 */
public class Dial extends ImageButton {
//    private String name;
    private char value;
    private EditText nmbField;
    private Drawable mainImg, pressImg;
    private SoundManager sndManager;
    private int soundId;
    private boolean animRun = false;  // used for onKey
    private Animation fadeIn = new AlphaAnimation(0, 1);

    public Dial(Context context, AttributeSet attrs) {
        super(context, attrs);

        fadeIn.setDuration(1000);
        setAnimation(fadeIn);

        TypedArray attrValues = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Dial, 0,0);
        try {
            char ch = attrValues.getString(R.styleable.Dial_btn_name).charAt(0);
            mainImg = this.getBackground();
            int resID = getResources().getIdentifier("dialpad_" + ch + "_pressed", "drawable", context.getPackageName());
            pressImg = ResourcesCompat.getDrawable(getResources(), resID, null);


            if(ch == 'p')
                value = '#';
            else if(ch == 's')
                value = '*';
            else
                value = ch;

            // find correlating sound file
            sndManager = SoundManager.getInstance();
            soundId = sndManager.loadSnd(value);
//            soundId = DialPad.loadSnd(context, value);

        } finally {
            attrValues.recycle();
        }

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg) {
                animRun = true;
            }

            @Override
            public void onAnimationRepeat(Animation arg) {
            }

            @Override
            public void onAnimationEnd(Animation arg) {
                animRun = false;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setBackground(pressImg);
                        btnPressed();

                        return true;
                    case MotionEvent.ACTION_UP:
                        setBackground(mainImg);
                        return true;
                }

                return false;
            }
        });

    }

    private void btnPressed() {
        if(nmbField != null)
            nmbField.append(""+value);

        // if no external storage is found, show toast
        if(!sndManager.playSnd(soundId)) {
            Toast toast = Toast.makeText(this.getContext(), "No external storage found!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void keyPress() {
        if(!animRun) {
            startAnimation(fadeIn);
            btnPressed();
        }
    }

    public int getSoundId() { return soundId; }

    public void setNmbField(EditText field) { nmbField = field; }

}
