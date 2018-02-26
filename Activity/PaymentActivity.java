package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;


public class PaymentActivity extends AppCompatActivity {


    private WebView webview;
    TextView titleTv;
    ImageView backIv;
    private static final String TAG = "Main";
    Context context;
    String displyMessage;
    String orderId;
    ProgressDialog pDialog;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;
    private ProgressDialog progressBar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_payment);

        orderId=getIntent().getStringExtra("dataStr");
        Log.d("orderId",""+orderId);

        webview = (WebView) findViewById(R.id.webview);
        titleTv = (TextView) findViewById(R.id.title);
        backIv = (ImageView) findViewById(R.id.back);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        titleTv.setText(getResources().getString(R.string.payment));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PaymentActivity.this, CheckoutActivity.class);
                startActivity(i);
                finish();
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        //String url_pay="139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=payment&orderId="+orderId;


        if (Network.isNetworkCheck(getApplicationContext())) {
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            //progressBar = ProgressDialog.show(PaymentActivity.this, "WebView Example", "Loading...");
            showProgressDialog();



            webview.setWebViewClient(new WebViewClient() {
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    showProgressDialog();
                }

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.i(TAG, "Processing webview url click...");
                    view.loadUrl(url);
                    return true;

                }

                public void onPageFinished(WebView view, String url) {
                    Log.i(TAG, "Finished loading URL: " + url);
                    Log.i(TAG, "Finished loading URL: " +url);
                   hideProgressDialog();

                }


                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                    Log.e(TAG, "Error: " + description);
                    Toast.makeText(PaymentActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(description);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    alertDialog.show();
                }
            });
            String url_pay=Utility.Base_Url+"&action=payment&orderId="+orderId;
            webview.loadUrl(url_pay);
           /* webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.setInitialScale(50);*/

        } /*else {

            showAlertbox();


        }*/


    }

    public void showAlertbox() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.network_dialog, null);

        dialog.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });
        dialog.setCancelable(false);
        AlertDialog ad = dialog.create();
        ad.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(PaymentActivity.this, CheckoutActivity.class);
        startActivity(i);
        finish();

    }
}