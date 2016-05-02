package com.example.erik.laboration5_1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    private EditText nmbField;
    private SharedPreferences call_list_pref, settings_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("DialPad");
        call_list_pref = getSharedPreferences("preference_call_list", Context.MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        settings_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        nmbField = (EditText) findViewById((R.id.nmbField));

        // set reference to nmbField to each dial
        final char[] ch = {'1','2','3','4','5','6','7','8','9','s','0','p'};
        for (int i = 0; i < ch.length; i++) {
            String dialID = "dial_" + ch[i];
            int resID = getResources().getIdentifier(dialID, "id", "com.example.erik.laboration5_1");
            Dial dial = ((Dial) findViewById(resID));
            dial.setNmbField(nmbField);
        }

        // Long click erase all text in nmbField
        ImageButton eraseBtn = (ImageButton) findViewById(R.id.eraseBtn);
        eraseBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                nmbField.getText().clear();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = null;

        switch(id) {
            case R.id.action_settings : intent = new Intent(this, SettingsActivity.class); break;
            case R.id.call_list : intent = new Intent(this, CallListActivity.class); break;
            case R.id.download :
//                File dir = Environment.getExternalStorageDirectory()+"/dialpad/sounds/";
                intent = new Intent(this, DownloadVoiceActivity.class)
                        .putExtra("SOURCE", "http://dt031g.programvaruteknik.nu/dialpad/sounds/")
                        .putExtra("TARGET", Environment.getExternalStorageDirectory().getAbsolutePath() + "/dialpad/sounds/");
/*                intent
                        .putExtra("URL", "http://dt031g.programvaruteknik.nu/dialpad/sounds/")
                        .putExtra("STORAGE");
                    */
                break;
        }

        if(intent != null) {
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Delete last character in nmbField
    public void delChar(View view) {
        int pos = nmbField.getText().toString().length();
        if(pos > 0)
            nmbField.getText().delete(pos-1, pos);
    }

    // call
    public void call(View v) {

        if(settings_pref.getBoolean("store_calls", true)) { // should we store calls?
            SharedPreferences.Editor editor = call_list_pref.edit();
//        editor.clear().commit();

            int idx = 0;
            String tel;
            do {
                idx++;
                tel = call_list_pref.getString("tel_" + idx, null);
            } while (tel != null);

            editor.putString("tel_" + idx, nmbField.getText().toString());
            editor.apply();
        }

        // call dialer
        String telNmb = nmbField.getText().toString();
//        Intent intent = new Intent(Intent.ACTION_DIAL);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.fromParts("tel", telNmb, "#"));
//        intent.setData(Uri.parse("tel:" + text));
//        if(checkCallingPermission(""))
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(this, "No permission found!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
