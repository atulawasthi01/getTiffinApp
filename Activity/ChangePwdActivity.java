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
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePwdActivity extends AppCompatActivity {
    ImageView backIv;
    TextView titleTv;
    Button saveBtn,cancelBtn;
    EditText oldpwdEt,newpwdEt,confpwdEt;
    ProgressDialog pDialog;
    String displyMessage,editid,oldpwdStr,newpwdStr,confpwdStr;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        backIv = (ImageView)findViewById(R.id.back);
        titleTv = (TextView)findViewById(R.id.title);
        oldpwdEt = (EditText)findViewById(R.id.oldpwd);
        newpwdEt = (EditText)findViewById(R.id.newpwd);
        confpwdEt = (EditText)findViewById(R.id.confpwd);
        saveBtn=(Button)findViewById(R.id.save);
        cancelBtn = (Button)findViewById(R.id.cancel);


        oldpwdStr = oldpwdEt.getText().toString();
        newpwdStr = newpwdEt.getText().toString();
        confpwdStr = confpwdEt.getText().toString();

        titleTv.setText(getResources().getString(R.string.changepwd));

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {

                    getchangepwd();
                }

                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ChangePwdActivity.this,MyAccountActivity.class);
                startActivity(i);
                finish();
            }
        });


        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChangePwdActivity.this,MyAccountActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ChangePwdActivity.this, MyAccountActivity.class);
        startActivity(i);
        finish();

    }

    private void getchangepwd() {

        oldpwdStr = oldpwdEt.getText().toString();
        newpwdStr = newpwdEt.getText().toString();

        //String Changepwd_Url="http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=change_password&type=Edit"+"editId="+LoginActivity.id+"password="+oldpwdStr+"password1="+newpwdStr;

        //String BaseURL = Utility.Base_Url+"access=true&action=change_password&type=Edit"+old.getText().toString()+"&password1="+newpassword.getText().toString().trim()+"&id="+KEY_USERID;

        String Changepwd_Url=Utility.Base_Url+"&action=change_password&type=Edit&editId="+loginId+"&password="+oldpwdStr+"&password1="+newpwdStr;
        http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=change_password&type=Edit&editId=306&password=admin&password1=admin@123



        Log.d("oldpwdStr",oldpwdStr);
        Log.d("newpwdStr",newpwdStr);

        /*Log.d("uname",unameStr);
        Log.d("email",emailStr);
        Log.d("pass",pwdStr);
        Log.d("daddress",daddressStr);
        //Log.d("res",Utility.URL_REGISTER);*/

        showProgressDialog();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Changepwd_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(ChangePwdActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("pwdresponse", response);
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            displyMessage=jobj.getString("displyMessage");
                            Log.d("pwdstatus",""+status);
                            Log.d("pwdmsg",displyMessage);
                            if (status == true) {

                                Toast.makeText(getApplicationContext(),displyMessage, Toast.LENGTH_LONG).show();


                            } else if (status == false) {
                                Toast.makeText(getApplicationContext(),displyMessage, Toast.LENGTH_LONG).show();
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
            /*@Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_EDiTID, LoginActivity.id);
                params.put(Utility.KEY_PASS, oldpwdStr);
                params.put(Utility.KEY_PASS1, newpwdStr);
                //params.put(Utility.KEY_DELIVERYADDRESS, daddressStr);
                // params.put(Utility.KEY_MOBILENUMBER,mobileNumber);
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


}





