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

public class MyProfileActivity extends AppCompatActivity {
    ImageView backIv;
    RelativeLayout mainrpofileLayout,editprofileLayout;
    TextView nameTv,titleTv;
    EditText nameEt;
    ProgressDialog pDialog;
    Button editBtn,deleteBtn,editdoneBtn;
    String id,ordersequenceno,isconfirmed,confirmedby,userid,dishid,trackingid,paymentmethodid,discountid,mobilenumber,delieverycostid,totalamount,delieverydate,indexvalue,
            createdon,modifiedon,createdby,modiefiedby,status,displyMessage,referwin,specialoffer,referid,username,mailid,delieveryaddress,image,otp,password,qrcode,merchantid,officeAddress;

    ImageView profileeditImg,profileImg;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static int RESULT_LOAD = 1;
    String imgDecodableString="drawable://" + R.drawable.profile+".png";
    //String nameStr,emailStr,addressStr,name,address;
    SharedPreferences profileSp;
    public static final String profilePrefrence = "profileSp";
    public static final String profileImgPrefrence = "profileImg";

    SharedPreferences profileSpp;
    public static final String profilePrefrencee = "profileSpp";


    String usernameStr,emailidStr,deliveryaddressStr;
    String uname,email,delAddress,officeAddressStr;

    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;
    String nameStr,emailStr,homeaddressStr,officeaddresStr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mainrpofileLayout = (RelativeLayout) findViewById(R.id.mainrofileLayout);
        editprofileLayout = (RelativeLayout) findViewById(R.id.editprofileLayout);
        nameTv = (TextView) findViewById(R.id.name);
        /*emailTv = (TextView) findViewById(R.id.email);
        addressTv = (TextView) findViewById(R.id.address);
        officeaddressTv = (TextView) findViewById(R.id.officeaddress);*/
        nameEt = (EditText) findViewById(R.id.nameet);
        /*emailEt = (EditText) findViewById(R.id.emailet);
        addressEt = (EditText) findViewById(R.id.addresset);
        officeaddressEt = (EditText) findViewById(R.id.officeaddresset);*/
        editBtn = (Button) findViewById(R.id.editBtn);
        //deleteBtn = (Button)findViewById(R.id.deleteBtn);
        editdoneBtn = (Button) findViewById(R.id.doneBtn);
        profileeditImg = (ImageView) findViewById(R.id.profileeditImg);
        profileImg = (ImageView) findViewById(R.id.profileImg);
        backIv = (ImageView) findViewById(R.id.back);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyProfileActivity.this, MyAccountActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        Log.d("loginId", loginId);

        titleTv = (TextView) findViewById(R.id.title);
        titleTv.setText(getResources().getString(R.string.myprofile));

        profileSp = getSharedPreferences(profilePrefrence, MODE_PRIVATE);
        emailidStr = profileSp.getString("mailId", "");
        usernameStr=profileSp.getString("userName","");
        deliveryaddressStr=profileSp.getString("deliveryAddress","");
        officeAddressStr=profileSp.getString("officeAddress","");

        Log.d("officeAddresssss",officeAddressStr);

        nameTv.setText(usernameStr);
        /*emailTv.setText(emailidStr);
        addressTv.setText(deliveryaddressStr);
        officeaddressTv.setText(officeAddressStr);*/


       /* profileSpp = getSharedPreferences(profilePrefrencee, MODE_PRIVATE);
        emailStr = profileSpp.getString("emailStr", "");
        nameStr=profileSpp.getString("nameStr","");
        homeaddressStr=profileSpp.getString("homeaddressStr","");
        officeaddresStr=profileSpp.getString("officeaddresStr","");


        nameTv.setText(nameStr);
        emailTv.setText(emailStr);
        addressTv.setText(homeaddressStr);
        officeaddressTv.setText(officeaddresStr);*/

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainrpofileLayout.setVisibility(View.INVISIBLE);
                editprofileLayout.setVisibility(View.VISIBLE);



                /*nameEt.setText(nameStr);
                emailEt.setText(emailStr);
                addressEt.setText(homeaddressStr);
                officeaddressEt.setText(officeaddresStr);*/

                nameEt.setText(usernameStr);
                /*emailEt.setText(emailidStr);
                addressEt.setText(deliveryaddressStr);
                officeaddressEt.setText(officeAddressStr);*/
            }
        });

        editdoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network.isNetworkCheck(getApplicationContext())) {

                    getUpdateProfile();

                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }
            }
        });




       /* profileSp = getSharedPreferences(profilePrefrence, MODE_PRIVATE);
        emailidStr = profileSp.getString("mailId", "");
        usernameStr=profileSp.getString("userName","");
        deliveryaddressStr=profileSp.getString("deliveryAddress","");
        officeAddressStr=profileSp.getString("officeAddress","");

        Log.d("officeAddresssss",officeAddressStr);

        nameTv.setText(usernameStr);
        emailTv.setText(emailidStr);
        addressTv.setText(deliveryaddressStr);
        officeaddressTv.setText(officeAddressStr);




        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainrpofileLayout.setVisibility(View.INVISIBLE);
                editprofileLayout.setVisibility(View.VISIBLE);
                *//*nameStr=nameEt.getText().toString();
                emailStr=emailEt.getText().toString();
                addressStr=addressEt.getText().toString();
                officeAddress=officeaddressEt.getText().toString();*//*


                nameEt.setText(usernameStr);
                emailEt.setText(emailidStr);
                addressEt.setText(deliveryaddressStr);
                officeaddressEt.setText(officeAddressStr);



               *//* profileSp = getApplicationContext().getSharedPreferences(profilePrefrence, MODE_PRIVATE);
                SharedPreferences.Editor editor = profileSp.edit();
                editor.putString("name",nameStr);
                editor.putString("email",emailStr);
                editor.putString("address",addressStr);
                editor.commit();
                editor.apply();*//*


            }
        });

        editdoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network.isNetworkCheck(getApplicationContext())) {


                    getUpdateProfile();

                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }
            }
        });

       *//* profileeditImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(MyProfileActivity.this)) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD);
                }

            }
        });*//*

       *//* deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network.isNetworkCheck(getApplicationContext())) {
                    getUserRemove();
                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }
            }
        });*//*

    }

    private void getUserRemove() {

        String user_delete_url=Utility.Base_Url+"&action=manage_users&type=Delete&id="+loginId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, user_delete_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hideProgressDialog();

                Log.d("profileresponse",response.toString());
                JSONObject jobj= null;
                try {
                    jobj = new JSONObject(response);
                    boolean status=jobj.getBoolean("status");
                    displyMessage=jobj.getString("displyMessage");

                    if (status==true) {

                        JSONObject jsonObject=jobj.getJSONObject("data");

                        Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();


                    }
                    else if (status==false) {

                        displyMessage=jobj.getString("displyMessage");
                        //Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
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
                        //hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void getUpdateProfile() {

        usernameStr=nameEt.getText().toString();
        emailidStr=emailEt.getText().toString();
        deliveryaddressStr=addressEt.getText().toString();
        officeAddressStr=officeaddressEt.getText().toString();

        String update_url=Utility.Base_Url+"&action=manage_users&type=Edit&editId="+loginId+"&userName="+usernameStr+"&mailId="+emailidStr+"&deliveryAddress="+deliveryaddressStr+"&officeAddress="+officeAddressStr;

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_users&type=Edit&editId=150&userName=mohit&mailId=mgupta926@gmail.com&deliveryAddress=sector-23&officeAddress=sector-56



        StringRequest stringRequest = new StringRequest(Request.Method.GET, update_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hideProgressDialog();

                Log.d("profileresponse",response.toString());
                JSONObject jobj= null;
                try {
                    jobj = new JSONObject(response);
                    boolean status=jobj.getBoolean("status");
                    displyMessage=jobj.getString("displyMessage");
                    *//*Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                    Log.d("profilestatus",""+status);
                    mainrpofileLayout.setVisibility(View.VISIBLE);
                    editprofileLayout.setVisibility(View.INVISIBLE);
                    nameTv.setText(usernameStr);
                    emailTv.setText(emailidStr);
                    addressTv.setText(deliveryaddressStr);
                    officeaddressTv.setText(officeAddressStr);*//*
                    if (status==true) {

                        JSONObject jsonObject=jobj.getJSONObject("data");

                        Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                        mainrpofileLayout.setVisibility(View.VISIBLE);
                        editprofileLayout.setVisibility(View.INVISIBLE);
                        nameTv.setText(usernameStr);
                        emailTv.setText(emailidStr);
                        addressTv.setText(deliveryaddressStr);
                        officeaddressTv.setText(officeAddressStr);

                        profileSpp = getApplicationContext().getSharedPreferences(profilePrefrencee, MODE_PRIVATE);
                        SharedPreferences.Editor editor = profileSpp.edit();
                        editor.putString("deliveryaddressStr",deliveryaddressStr);
                        editor.putString("officeAddressStr",officeAddressStr);
                        editor.commit();
                        editor.apply();

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
                        //hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }*/
    }

    private void getUpdateProfile() {



        usernameStr=nameEt.getText().toString();
        /*emailidStr=emailEt.getText().toString();
        deliveryaddressStr=addressEt.getText().toString();
        officeAddressStr=officeaddressEt.getText().toString();*/

        String update_url=Utility.Base_Url+"&action=manage_users&type=Edit&editId="+loginId+"&userName="+usernameStr;

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_users&type=Edit&editId=150&userName=mohit&mailId=mgupta926@gmail.com&deliveryAddress=sector-23&officeAddress=sector-56



        StringRequest stringRequest = new StringRequest(Request.Method.GET, update_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hideProgressDialog();

                Log.d("profileresponse",response.toString());
                JSONObject jobj= null;
                try {
                    jobj = new JSONObject(response);
                    boolean status=jobj.getBoolean("status");
                    displyMessage=jobj.getString("displyMessage");

                   /* Log.d("profilestatus",""+status);
                    mainrpofileLayout.setVisibility(View.VISIBLE);
                    editprofileLayout.setVisibility(View.INVISIBLE);
                    nameTv.setText(nameStr);
                    emailTv.setText(emailStr);
                    addressTv.setText(homeaddressStr);
                    officeaddressTv.setText(officeaddresStr);*/
                    if (status==true) {

                        JSONObject jsonObject=jobj.getJSONObject("data");

                        Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                        mainrpofileLayout.setVisibility(View.VISIBLE);
                        editprofileLayout.setVisibility(View.INVISIBLE);
                        nameTv.setText(usernameStr);
                        /*emailTv.setText(emailidStr);
                        addressTv.setText(deliveryaddressStr);
                        officeaddressTv.setText(officeAddressStr);*/
                        profileSp = getApplicationContext().getSharedPreferences(profilePrefrence, MODE_PRIVATE);
                        SharedPreferences.Editor editorProfile = profileSp.edit();
                        editorProfile.putString("userName",usernameStr);
                        /*editorProfile.putString("mailId",emailidStr);
                        editorProfile.putString("deliveryAddress",deliveryaddressStr);
                        editorProfile.putString("officeAddress",officeAddressStr);*/
                        editorProfile.commit();
                        editorProfile.apply();

                        //String usernameStr,emailidStr,deliveryaddressStr,officeAddressStr;

                       /* profileSpp = getApplicationContext().getSharedPreferences(profilePrefrencee, MODE_PRIVATE);
                        SharedPreferences.Editor editor = profileSpp.edit();
                        editor.putString("nameStr",nameStr);
                        editor.putString("emailStr",emailStr);
                        editor.putString("homeaddressStr",homeaddressStr);
                        editor.putString("officeaddresStr",officeaddresStr);
                        editor.commit();
                        editor.apply();*/

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
                        //hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(MyProfileActivity.this,MyAccountActivity.class);
        startActivity(i);
        finish();
    }

   /* private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }*/




}





