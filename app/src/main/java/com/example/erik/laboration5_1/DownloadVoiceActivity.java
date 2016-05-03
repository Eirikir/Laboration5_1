package com.example.erik.laboration5_1;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Erik on 2/5 002.
 */
public class DownloadVoiceActivity extends AppCompatActivity {
    private String target, fileURLPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up action

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());

        // download listener
        browser.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                fileURLPath = url;
                new DownloadFile().execute();
            }
        });

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("SOURCE");    // source url
        target = extras.getString("TARGET");        // where to save files
        browser.loadUrl(url);
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {
        private ProgressDialog pDialog;
        @Override
        protected String doInBackground(String... args) {
            InputStream is;
            try {
                URL url = new URL(fileURLPath);


                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.connect();

                int fileLength = con.getContentLength();    // can we get total size of file

                is = con.getInputStream();

                File outputFile = new File(target, "tester.zip");
                FileOutputStream fos = new FileOutputStream(outputFile);

                // download file from URL to target directory
                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0;  // progressed length
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;

                    // update progress if file length is known
                    if(fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));

                    Thread.sleep(1); // only to simulate slower download
                }
                fos.close();
                is.close();

                ZIP.decompress(outputFile.getAbsolutePath(), target);
                outputFile.delete();    // delete zip file

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch(Exception ex) {

            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            pDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // init progressdialog
            pDialog = new ProgressDialog(DownloadVoiceActivity.this);
            pDialog.setTitle("Downloading voice");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            // text in dialog
            int pos = fileURLPath.lastIndexOf("/");
            String name = fileURLPath.substring(pos+1);
            pDialog.setMessage(name);
            pDialog.setProgressNumberFormat(null);

            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dismiss dialog
            pDialog.dismiss();
        }

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
