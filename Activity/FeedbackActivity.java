package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class FeedbackActivity extends AppCompatActivity {
    ImageView profileIv;
    TextView userNameTv;
    EditText feedbackTv;
    Button cancelBtn,saveBtn;
    ProgressDialog pDialog;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        profileIv = (ImageView)findViewById(R.id.profileImg);
        userNameTv = (TextView)findViewById(R.id.userName);
        feedbackTv = (EditText)findViewById(R.id.feedbacktxt);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FeedbackActivity.this, HomeActivity.class);
                startActivity(i);
                finish();

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(FeedbackActivity.this,SettingActivity.class);
                startActivity(i);
                finish();*/

                if (Network.isNetworkCheck(getApplicationContext())) {

                    getUserFeedback();
                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void getUserFeedback() {

        String feedbackStr=feedbackTv.getText().toString();

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=user_feedback&userId=208&type=Add&feedback=bhke

        String feedback_url=Utility.Base_Url+"&action=user_feedback&userId="+loginId+"&type=Add&feedback="+feedbackStr;
        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, feedback_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(RateusActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("feedbackresponse", response);
                        String mainResponse=response;
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            String displyMessage=jobj.getString("displyMessage");
                            Log.d("ssstatus",""+status);
                            if (status == true) {

                                String data=jobj.getString("data");

                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();


                            } else if (status == false) {
                                displyMessage = jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(), displyMessage, Toast.LENGTH_LONG).show();
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
           /* @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_USERID, userid);
                params.put(Utility.KEY_RATING, rating);

                params.put("status","1");
                return params;



            }*/
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
        Intent i=new Intent(FeedbackActivity.this,HomeActivity.class);
        startActivity(i);
        finish();

    }
}
