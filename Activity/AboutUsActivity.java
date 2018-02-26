package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.R;

public class AboutUsActivity extends AppCompatActivity {
    TextView aboutusTv,phoneTv,faxTv,facebookTv,titleTv;
    ImageView backIv;
    WebView aboutusweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutusTv = (TextView)findViewById(R.id.aboutustxt);
        phoneTv = (TextView)findViewById(R.id.phone);
        faxTv = (TextView)findViewById(R.id.fax);
        facebookTv = (TextView)findViewById(R.id.facebook);
        titleTv = (TextView)findViewById(R.id.title);
        backIv = (ImageView)findViewById(R.id.back);
        titleTv.setText(getResources().getString(R.string.aboutus));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AboutUsActivity.this,SettingActivity.class);
                startActivity(i);
                finish();
            }
        });

        /*aboutusweb = (WebView)findViewById(R.id.aboutusweb);
        aboutusweb.getSettings().setJavaScriptEnabled(true);
        aboutusweb.loadUrl("file:///android_asset/aboutus.html");*/




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(AboutUsActivity.this,SettingActivity.class);
        startActivity(i);
        finish();
    }

}
