package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;
import com.yumtiff.mohitkumar.tiffin.Utility.Validate;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfiledtlActivity extends AppCompatActivity {
    TextView titleTv,toasttxtTv;
    ImageView backIv;
    EditText unameEt,pwdEt,cpwdEt;
    //CheckBox sameaddressChk;
    Button proceedBtn;
    ProgressDialog pDialog;
    String unameStr,emailStr,pwdStr,cpwdStr,daddressStr,mobileStrr;

    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;


    String id,roleId,username,dishid,referwinid ,image,mobileNumber,deliveryAddress,otp,password,qrcode,merchantId,indexValue,
            createdOn,modifiedOn,createdBy,modifiedBy,displyMessage,referid,specialofferid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profiledtl);
        titleTv = (TextView)findViewById(R.id.title);
        backIv = (ImageView)findViewById(R.id.back);
        unameEt = (EditText)findViewById(R.id.uname);
        //emailEt = (EditText)findViewById(R.id.email);
        pwdEt = (EditText)findViewById(R.id.pwd);
        cpwdEt = (EditText)findViewById(R.id.cpwd);
       /* sameaddressChk = (CheckBox)findViewById(R.id.sameaddressChk);
        emailEt.setText(Global.fbemail);
        homeaddressEt = (EditText)findViewById(R.id.homeaddress);
        officeaddressEt = (EditText)findViewById(R.id.officeaddress);*/
        proceedBtn = (Button)findViewById(R.id.proceedBtn);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        Log.d("loginId",loginId);




        mobileStrr = getIntent().getStringExtra("mobile");
        Log.d("mobile",mobileStrr);



        titleTv.setText(getResources().getString(R.string.profiledtl));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ProfiledtlActivity.this,SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

       /* homeaddressStr=homeaddressEt.getText().toString();
        officeaddressStr=officeaddressEt.getText().toString();*/

        /*if (sameaddressChk.isChecked()) {
            homeaddressStr=homeaddressEt.getText().toString();
            officeaddressStr=officeaddressEt.getText().toString();
            Toast.makeText(getApplicationContext(),"check",Toast.LENGTH_LONG).show();

            officeaddressEt.setText(homeaddressStr);
        }
        else {
            officeaddressEt.setText("");
        }*/

       /* sameaddressChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sameaddressChk.isChecked()) {
                    homeaddressStr=homeaddressEt.getText().toString();
                    officeaddressStr=officeaddressEt.getText().toString();
                    //Toast.makeText(getApplicationContext(),"check",Toast.LENGTH_LONG).show();

                    officeaddressEt.setText(homeaddressStr);
                }
                else {
                    officeaddressEt.setText("");
                }
            }
        });*/




        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unameStr=unameEt.getText().toString();
                //emailStr=emailEt.getText().toString();
                pwdStr=pwdEt.getText().toString();
                cpwdStr=cpwdEt.getText().toString();
                //homeaddressStr=homeaddressEt.getText().toString();
                //officeaddressStr=officeaddressEt.getText().toString();
                Log.d("unameStr",unameStr);
                Log.d("emailStr",emailStr);
                Log.d("pwdStr",pwdStr);


                //Toast.makeText(getApplicationContext(),""+unameStr+""+emailStr+""+pwdStr+""+cpwdStr+""+daddressStr,Toast.LENGTH_LONG).show();
                if (unameStr.equalsIgnoreCase("") && emailStr.equalsIgnoreCase("") && pwdStr.equalsIgnoreCase("") && cpwdStr.equalsIgnoreCase("")) {

                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.fieldsblank));
                }
                else if (unameStr.equalsIgnoreCase("")) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.unameblank));
                }
                else if (emailStr.equalsIgnoreCase("")) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.emailidblank));
                }
                else if (!Validate.emailValidator(emailStr)) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.validemail));
                }
                else if (pwdStr.equalsIgnoreCase("")) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.pwdblank));
                }
               /* else if (pwdStr.length()>7) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.pwdlength));
                }*/
                else if (cpwdStr.equalsIgnoreCase("")) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.cpwdblank));
                }
                else if (cpwdStr.length()<7) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.pwdlength));
                }
                else if (!pwdStr.equalsIgnoreCase(cpwdStr)) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.confirmpwd));
                }

                else {
                    /*Intent i=new Intent(ProfiledtlActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();*/

                    getSignup();
                }
            }
        });

    }

    public void showCustomAlert() {

        Context context = getApplicationContext();
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getLayoutInflater();

        // Call toast.xml file for toast layout
        View toastRoot = inflater.inflate(R.layout.activity_custom_toast, null);
        toasttxtTv = (TextView)toastRoot.findViewById(R.id.toasttxt);


        Toast toast = new Toast(context);

        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }

    private void getSignup() {

        Log.d("uname",unameStr);
        Log.d("pass",pwdStr);

        //Log.d("res",Utility.URL_REGISTER);



        showProgressDialog();

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_users&type=Add&userName=mohit&mailId=mgupta926@gmail.com&password=1234&deliveryAddress=hvbjkvb&mobileNumber=8750813101

        String signupurl=Utility.Base_Url+"&action=manage_users&type=Add&userName="+unameStr+"&password="+pwdStr+"&mobileNumber="+mobileStrr;
        String signupspacefree_url=signupurl.replaceAll("\\s+", "");

        Log.d("signupUrl",signupurl);
        Log.d("signupspacefree_url",signupspacefree_url);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, signupspacefree_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(ProfiledtlActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("signupresponse", response);
                        String mainResponse=response;
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            Log.d("ssstatus",""+status);
                            if (status == true) {

                                JSONObject jsonObject=jobj.getJSONObject("data");

                                id = jsonObject.getString("id");
                                referwinid = jsonObject.getString("refer&winId");
                                dishid= jsonObject.getString("dishId");
                                specialofferid=jsonObject.getString("specialofferId");
                                referid=jsonObject.getString("referId");
                                username = jsonObject.getString("userName");
                                image = jsonObject.getString("image");
                                mobileNumber = jsonObject.getString("mobileNumber");
                                deliveryAddress = jsonObject.getString("deliveryAddress");
                                otp = jsonObject.getString("otp");
                                password = jsonObject.getString("password");
                                qrcode = jsonObject.getString("qrcode");
                                merchantId = jsonObject.getString("merchantId");
                                indexValue = jsonObject.getString("indexValue");
                                createdOn = jsonObject.getString("createdOn");
                                modifiedOn = jsonObject.getString("modifiedOn");
                                createdBy = jsonObject.getString("createdBy");
                                modifiedBy = jsonObject.getString("modifiedBy");
                                indexValue = jsonObject.getString("indexValue");
                                displyMessage = jobj.getString("displyMessage");

                                Toast.makeText(ProfiledtlActivity.this,"You are Sign In",Toast.LENGTH_LONG).show();

                                Log.d("displyMessage", displyMessage);
                                Log.d("username",username);
                                Log.d("mobileNumber",mobileNumber);
                                Log.d("password",password);

                                Intent i = new Intent(ProfiledtlActivity.this, LoginActivity.class);
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
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
           /* @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_USERNAME, unameStr);
                params.put(Utility.KEY_MAILID, emailStr);
                params.put(Utility.KEY_PASSWOR, pwdStr);
                params.put(Utility.KEY_DELIVERYADDRESS, daddressStr);
                params.put(Utility.KEY_MOBILENUMBER,mobileStrr);
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


}
