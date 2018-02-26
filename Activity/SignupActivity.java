package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

public class SignupActivity extends AppCompatActivity {
    Dialog dialog;
    Context context=this;
    EditText mobileEt,otpTv;
    Button proceedBtn;
    ProgressDialog pDialog;
    String mobileStr;
    ProgressDialog pdialog;
    TextView toasttxtTv;
    RelativeLayout resendotpRl,verifyRl;
    String otpStr;
    String id,roleId,firstName,lastName,mailId,image,mobileNumber,deliveryAddress,otp,password,qrcode,merchantId,indexValue,
            createdOn,modifiedOn,createdBy,modifiedBy,displyMessage,referid,status,dataStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        mobileEt = (EditText)findViewById(R.id.mobile);
        proceedBtn = (Button)findViewById(R.id.proceedBtn);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileStr = mobileEt.getText().toString();
                if (mobileStr.equalsIgnoreCase("")) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.mobileblank));


                }
                else if (mobileStr.length()!=10) {
                    showCustomAlert();
                    toasttxtTv.setText(getResources().getString(R.string.validmobile));
                }
                else {
                   /* Intent i=new Intent(SignupActivity.this,VerifyNoActivity.class);
                    i.putExtra("mob",mobileStr);
                    startActivity(i);
                    finish();*/
                    getotp();



                    dialog = new Dialog(context);
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.verify_popup_layout);

                    dialog.show();
                    otpTv = (EditText) dialog.findViewById(R.id.otp);
                    resendotpRl = (RelativeLayout)dialog.findViewById(R.id.resendotpRl);
                    verifyRl = (RelativeLayout)dialog.findViewById(R.id.verifyRl);

                    resendotpRl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    verifyRl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Network.isNetworkCheck(getApplicationContext())) {

                                getotpverified();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                            }
                           /* Intent i=new Intent(SignupActivity.this,ProfiledtlActivity.class);
                            i.putExtra("mobile",mobileStr);
                            startActivity(i);
                            finish();*/

                        }
                    });
                }
            }
        });
       
    }

    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        Intent i=new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(i);*/
        finish();

    }
   /* private void showProgressDialog() {
        if (!pdialog.isShowing())
            pdialog.show();
    }

    private void hideProgressDialog() {
        if (pdialog.isShowing())
            pdialog.hide();
    }*/



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

    private void getotp() {

        String OTP_URL=Utility.Base_Url+"&action=enter_mobile_number&type=Add&mobileNumber="+mobileStr;

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=enter_mobile_number&type=Add&mobileNumber=

        Log.d("mobileStr",OTP_URL);

        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("otpresponse",response.toString());
                        JSONObject jobj= null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            displyMessage=jobj.getString("displyMessage");
                            if (status==true) {

                                JSONObject jsonObject=jobj.getJSONObject("data");
                                id=jsonObject.getString("id");

                                //displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();




                            }
                            else if (status==false) {
                                displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();

                            }
                            else if (status==true && displyMessage.equals("Mobile Number Already Exist!!")) {
                                Toast.makeText(getApplicationContext(),"Mobile Number Already Exist",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
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
                        Log.d("error",error.toString());
                    }
                }) {
            /*@Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENUMBER,mobileNumber);
              //  params.put(Utility.KEY_OTP, otp);
                return params;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSp.edit();


*//**************** Storing data as KEY/VALUE pair *******************//*

        editor.putBoolean(" KEY_MOBILENO", true);           // Saving boolean - true/false
        editor.putInt("key_name2", Integer.parseInt("int value"));        // Saving integer
        editor.putFloat("KEY_PASSWORD", Float.parseFloat("float value"));    // Saving float
      */


    }


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

  /*  private void showProgressDialog() {
        if (!pdialog.isShowing())
            pdialog.show();
    }

    private void hideProgressDialog() {
        if (pdialog.isShowing())
            pdialog.hide();*/
  private void getotpverified() {

      otpStr=otpTv.getText().toString();

      String OTPVERIFY_OTP=Utility.Base_Url+"&action=otp_varified&otp="+otpStr+"&mobileNumber="+mobileStr;

      //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=otp_varified&type=Edit&otp=58e3b0cd03



      showProgressDialog();

      StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPVERIFY_OTP,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      hideProgressDialog();
                      //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                      Log.d("otpresponse",response.toString());
                      JSONObject jobj= null;
                      try {
                          jobj = new JSONObject(response);
                          boolean status=jobj.getBoolean("status");
                          if (status==true) {


                              dataStr=jobj.getString("data");
                              displyMessage=jobj.getString("displyMessage");
                              Log.d("displyMessage",displyMessage);
                              Intent i=new Intent(SignupActivity.this,ProfiledtlActivity.class);
                              i.putExtra("mobile",mobileStr);
                              startActivity(i);
                              finish();



                          }
                          else if (status==false) {
                              displyMessage=jobj.getString("displyMessage");
                              Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                          }
                          else {
                              Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
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
                      Log.d("error",error.toString());
                  }
              }) {
         /* @Override
          protected Map<String,String> getParams() {
              Map<String,String> params = new HashMap<String, String>();
              params.put(Utility.KEY_OTP,otp);
              //  params.put(Utility.KEY_OTP, otp);
              return params;
          }*/
      };

      RequestQueue requestQueue = Volley.newRequestQueue(this);
      requestQueue.add(stringRequest);
       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSp.edit();


*//**************** Storing data as KEY/VALUE pair *******************//*

        editor.putBoolean(" KEY_MOBILENO", true);           // Saving boolean - true/false
        editor.putInt("key_name2", Integer.parseInt("int value"));        // Saving integer
        editor.putFloat("KEY_PASSWORD", Float.parseFloat("float value"));    // Saving float
      */


  }




}