package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.R;

public class LogoutActivity extends AppCompatActivity implements View.OnClickListener{
    Button noBtn,yesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        noBtn = (Button)findViewById(R.id.no);
        yesBtn = (Button)findViewById(R.id.yes);
        noBtn.setOnClickListener(this);
        yesBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no:
                Intent i=new Intent(LogoutActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.yes:

                    SharedPreferences sharedpreferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                Intent ilog=new Intent(LogoutActivity.this,LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ilog);
                finish();

                if (Global.feedItems!=null) {
                    Global.feedItems.clear();
                }
                else {
                    //Toast.makeText(getApplicationContext(),"List is Null",Toast.LENGTH_SHORT).show();
                }

                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}
