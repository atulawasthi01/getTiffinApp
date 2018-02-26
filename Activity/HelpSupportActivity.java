package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.R;

public class HelpSupportActivity extends AppCompatActivity {
    TextView helpsupportTv;
    ImageView backIv;
    TextView titleTv;
    WebView hsweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);
        titleTv = (TextView)findViewById(R.id.title);
        helpsupportTv = (TextView)findViewById(R.id.helpsupportytxt);
        backIv = (ImageView)findViewById(R.id.back);
        titleTv.setText(getResources().getString(R.string.helpsupport));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HelpSupportActivity.this,SettingActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(HelpSupportActivity.this,SettingActivity.class);
        startActivity(i);
        finish();
    }
}
