package com.example.erik.laboration5_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Set;

/**
 * Created by Erik on 29/4 029.
 */
public class CallListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up action

        TextView telNmbView = (TextView) findViewById(R.id.telNmbrs);

        // get preference file
        SharedPreferences call_list_pref = getSharedPreferences("preference_call_list", Context.MODE_PRIVATE);

        // add all numbers we find in preferences to view
        int idx = 1;
        while(true) {
            String tel = call_list_pref.getString("tel_"+idx, null);
            if(tel == null)
                break;
            telNmbView.append("\n" + tel);
            idx++;
        }
    }
}
