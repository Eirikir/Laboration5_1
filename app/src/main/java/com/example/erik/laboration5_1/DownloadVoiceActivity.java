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
                //               Uri uri = Uri.parse(url);
                //               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //               startActivity(intent);

/*                DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
                req.allowScanningByMediaScanner();
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                req.setDestinationInExternalPublicDir("/dialpad/sounds", "test.zip");

                mgr.enqueue(req);
                */
                fileURLPath = url;
                new DownloadFile().execute();
            }
        });

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("SOURCE");    // source url
        target = extras.getString("TARGET");        // where to save files
        browser.loadUrl(url);
    }

    private class DownloadFile extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected String doInBackground(String... args) {
//            Uri uri = Uri.parse(fileURLPath);
/*            DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(fileURLPath));
            req.allowScanningByMediaScanner();
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            req.setDestinationInExternalPublicDir("/dialpad/sounds", "test.zip");

            mgr.enqueue(req);
*/
            InputStream is;
            try {
                URL url = new URL(fileURLPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.connect();

                is = con.getInputStream();

                File outputFile = new File(target, "tester.zip");
                FileOutputStream fos = new FileOutputStream(outputFile);

                // download file from URL to target directory
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();

                ZIP.decompress(outputFile.getAbsolutePath(), target);
                outputFile.delete();

/*                String[] na = {"kalle.zip", "kalle1.zip", "test.zip", "test-1.zip", "test-2.zip", "ida_dk", "peter_uk"};
                for(int i = 0; i < na.length; i++) {
                    File f1 = new File(target, na[i]);
                    f1.delete();
                }*/


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // init progressdialog
//            pDialog = ProgressDialog.show(getBaseContext(), "", "", true);
            pDialog = new ProgressDialog(DownloadVoiceActivity.this);
            pDialog.setTitle("Downloading voice");
//            pDialog.setMessage("Downloading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dismiss dialog
            pDialog.dismiss();
        }

//        @Override
//        protected void onProgressUpdate(String... args) {}
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
/*            if(url.endsWith(".dps")) {
                System.out.println(url);
                return true;
            }*/

/*            if(url.endsWith(".dps")) {
                DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
                req.allowScanningByMediaScanner();
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                req.setDestinationInExternalPublicDir("/dialpad/sounds", "test.zip");

                mgr.enqueue(req);
            }*/
            view.loadUrl(url);
            return true;
        }
    }
}
