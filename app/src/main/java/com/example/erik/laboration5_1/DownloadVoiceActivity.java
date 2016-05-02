package com.example.erik.laboration5_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Erik on 2/5 002.
 */
public class DownloadVoiceActivity extends AppCompatActivity {
    private String target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up action

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("SOURCE");
        target = extras.getString("TARGET");
        browser.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.endsWith(".dps")) {
                System.out.println(url);
                return true;
            }
 //           view.loadUrl(url);
            return true;
        }
    }
}
