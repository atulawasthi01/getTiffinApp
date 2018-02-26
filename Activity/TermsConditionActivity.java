package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.R;

public class TermsConditionActivity extends AppCompatActivity {
    ImageView backIv;
    TextView titleTv,termsconditionTv;
    WebView tcWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);
        titleTv = (TextView)findViewById(R.id.title);
        backIv = (ImageView)findViewById(R.id.back);
        //tcWeb = (WebView)findViewById(R.id.tcweb);
        titleTv.setText(getResources().getString(R.string.termscondition));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TermsConditionActivity.this,SettingActivity.class);
                startActivity(i);
                finish();
            }
        });

        /*tcWeb.getSettings().setJavaScriptEnabled(true);
        tcWeb.loadUrl("file:///android_asset/termscondition.html");*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(TermsConditionActivity.this,SettingActivity.class);
        startActivity(i);
        finish();
    }
}
