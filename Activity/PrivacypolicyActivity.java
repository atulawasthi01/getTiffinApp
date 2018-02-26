package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.R;

public class PrivacypolicyActivity extends AppCompatActivity {
    ImageView backIv;
    TextView titleTv,privacypolicyTv;
    WebView ppweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy);
        titleTv = (TextView)findViewById(R.id.title);
        privacypolicyTv = (TextView)findViewById(R.id.privacypolicytxt);
        backIv = (ImageView)findViewById(R.id.back);
        titleTv.setText(getResources().getString(R.string.privpolicy));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PrivacypolicyActivity.this,SettingActivity.class);
                startActivity(i);
                finish();
            }
        });

       /* ppweb.getSettings().setJavaScriptEnabled(true);
        ppweb.loadUrl("file:///android_asset/privacypolicy.html");*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(PrivacypolicyActivity.this,SettingActivity.class);
        startActivity(i);
        finish();
    }
}
