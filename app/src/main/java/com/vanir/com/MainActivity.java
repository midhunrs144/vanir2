package com.vanir.com;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//import android.graphics.Bitmap;
import android.view.View;
//import android.widget.ProgressBar;
//import android.Manifest;
import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
//import android.webkit.GeolocationPermissions;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private WebView webview ;
    private String URL = "https://vanir.in";
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = DialogUtilities.showProgressBar(this);
        dialog.show();

        webview =(WebView)findViewById(R.id.webView);
        //webview.setWebViewClient(new CustomWebViewClient());

        //Only hide the scrollbar, not disables the scrolling:
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.clearCache(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(URL);


        webview.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView webview, int errorCode, String description, String failingUrl)
            {
                try
                {
                    webview.stopLoading();
                }
                catch (Exception e)
                {

                }

                if (webview.canGoBack())
                {
                    webview.goBack();
                }

                webview.loadUrl("about:blank");
                dialog.show();
                if(!isConnected()){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Oops");
                    alertDialog.setMessage("It seems you lost the internet connection");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    alertDialog.show();
                }
                else {
                    webview.loadUrl(URL);
                }
                super.onReceivedError(webview, errorCode, description, failingUrl);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                //hide loading image
                dialog.dismiss();
                findViewById(R.id.imageLoading1).setVisibility(View.GONE);
                //show webview
                findViewById(R.id.webView).setVisibility(View.VISIBLE);
            }
        });

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed()
    {

        if (webview.getUrl().equals(URL)){
            finish();
        }

        if (webview.canGoBack())
        {
            webview.goBack();
            return;
        }
//        else
//        {
//            super.onBackPressed();
//        }
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.vanir.com.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity com AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.vanir.com.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean isConnected(){
        final String command = "ping -c 1 google.com";
        try {
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
