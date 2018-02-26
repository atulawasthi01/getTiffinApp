package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RateusActivity extends AppCompatActivity {
    Button cancelBtn,saveBtn;
    RatingBar ratingBar;
    String displyMessage,id,userid,rating;
    ProgressDialog pDialog;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId,ratingStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateus);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        saveBtn = (Button)findViewById(R.id.submitBtn);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                final int numStars = ratingBar.getNumStars();

                ratingStr = String.valueOf(ratingBar.getRating());
                //Toast.makeText(getApplicationContext(),ratingStr,Toast.LENGTH_LONG).show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {

                    getrating();
                }
                else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                }

            }
    });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RateusActivity.this,SettingActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    public void getrating(){

        String rateus_url=Utility.Base_Url+"&action=user_rating&userId="+loginId+"&type=Add&rating="+ratingStr;


        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, rateus_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(RateusActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("ratingresponse", response);
                        String mainResponse=response;
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            Log.d("ssstatus",""+status);
                            if (status == true) {

                               String data=jobj.getString("data");
                                String displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),"Thank You for Rating",Toast.LENGTH_LONG).show();


                                Intent i = new Intent(RateusActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();

                            } else if (status == false) {
                        displyMessage = jobj.getString("displyMessage");
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.usernamepwdinc), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
            e.printStackTrace();
        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Log.d("error", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_USERID, userid);
                params.put(Utility.KEY_RATING, rating);

                params.put("status","1");
                return params;



            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
        Intent i=new Intent(RateusActivity.this,SettingActivity.class);
        startActivity(i);
        finish();
    }
}






