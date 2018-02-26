package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class AdonsDiscriptionActivity extends AppCompatActivity {

    Dialog dialog;
    Context context = this;
    ProgressDialog pDialog;
    Toolbar toolbar;
    TextView countttTv, quntityTxt;
    ImageView dishImg, cartimg;
    Button addtocartBtn, addBtn, subBtn;
    TextView dishcodeTv, dishNameTv, dishPriceTv;
    RelativeLayout mainLayout;
    ListView dayListView, timeListView;
    public String id, ordersequenceno, modifiedBy, confirmedby, userid, trackingid, paymentmethodid, discountid, cityId, pinCode, indexValue, createdOn,
            createdBy, modifiedon, createdby, modifiedOn, sum, displyMessage;
    String[] dayArr = new String[]{"Today", "Tomorrow"};
    String[] timeArr = new String[]{"11:00 AM", "2:00 PM", "4:00 PM", "08:00 PM"};
    String dishid, dishimage, dishcode, dishname, dishdescription, price;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;
    public String deliveryCharges = "";
    int count = 0, countQuntity = 1;
    String quantityStr;
    boolean isQuantity=false;
    String dishId,menuId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_discription);
        Global.isbreakfastClick = true;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dishImg = (ImageView) findViewById(R.id.dishImg);
        dishcodeTv = (TextView) findViewById(R.id.dishcode);
        quntityTxt = (TextView) findViewById(R.id.quntityTxt);
        dishNameTv = (TextView) findViewById(R.id.dishName);
        dishPriceTv = (TextView) findViewById(R.id.dishPrice);
        countttTv = (TextView) findViewById(R.id.counttt);
       /* pincodeEt = (EditText) findViewById(R.id.pincodetxt);
        deliverychargeTv = (TextView) findViewById(R.id.deliverycharge);*/
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        addBtn = (Button) findViewById(R.id.addBtn);
        subBtn = (Button) findViewById(R.id.subBtn);

        addtocartBtn = (Button) findViewById(R.id.addtocart);

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        addtocartBtn.setText("Add to Cart");
        addtocartBtn.setEnabled(true);

        //checkBtn = (Button) findViewById(R.id.checkBtn);
        cartimg = (ImageView) findViewById(R.id.cartimg);
        cartimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdonsDiscriptionActivity.this, Maincart.class);
                startActivity(i);
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.adonsdiscription));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdonsDiscriptionActivity.this, AdonsActivity.class);
                startActivity(i);
                finish();
            }
        });

        Intent intent = getIntent();

        dishId = intent.getStringExtra("dishId");
        dishimage = intent.getStringExtra("dishImg");
        dishcode = intent.getStringExtra("dishCode");
        dishname = intent.getStringExtra("dishName");
        dishdescription = intent.getStringExtra("dishDiscription");
        price = intent.getStringExtra("dishPrice");
        menuId = intent.getStringExtra("menuId");



       /* dishid=intent.getStringExtra("dishIdTday");
        dishname=intent.getStringExtra("dishnameTday");
        dishcode=intent.getStringExtra("dishcodeTday");
        price=intent.getStringExtra("dishpriceTday");
        dishimage=intent.getStringExtra("dishImgTday");
        dishdescription=intent.getStringExtra("dishDiscriptionTday");*/


        //Log.d("dishimageDis",dishimage);
        /*Log.d("dishcodeDis",dishcode);
        Log.d("dishnameDis",dishname);
        Log.d("dishdescriptionDis",dishdescription);
        Log.d("priceDis",price);*/

        dishcodeTv.setText(dishcode);
        dishNameTv.setText(dishname);
        dishPriceTv.setText("₹" + price);
        Picasso.with(this)
                .load(dishimage)
                .into(dishImg);

        if (Network.isNetworkCheck(getApplicationContext())) {

            getDuplicateDish();

        }
        else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }



       /* checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {
                    //String pincodeStr=pincodeEt.getText().toString();

                    //String pincode_url= Utility.Base_Url+"&action=get_pincode_details&pinCode="+pincodeStr;

                    getpinCode();
                    //Parser.getpinCode(pincode_url,deliveryCharges);

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                }

            }
        });*/

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isQuantity=true;
                if (countQuntity<1000) {
                    countQuntity = countQuntity + 1;
                    quantityStr= String.valueOf(countQuntity);
                    quntityTxt.setText(quantityStr);
                }


            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isQuantity=true;
                if (countQuntity>1) {
                    countQuntity = countQuntity - 1;
                    quantityStr= String.valueOf(countQuntity);
                    quntityTxt.setText(quantityStr);
                }

            }
        });

        addtocartBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {
                    getcart();
                   /* if (isQuantity==true) {
                        getcart();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.quntityadd), Toast.LENGTH_LONG).show();
                    }*/

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                }

            }
        });
    }




    public void getcart() {


        dishid = getIntent().getStringExtra("dishId");
        Log.d("loginIdddd", loginId);
        Log.d("brekdishhhid", dishid);

        // String URL_MYORDER= Utility.URL_DESCRIPTION+"&pincode="+LoginActivity.id;
        //String URL_ADDTOCART = Utility.Base_Url+"&action=manage_carts&type=Add&dishId="+dishid+"&userId="+loginId;
        String URL_ADDTOCART = Utility.Base_Url + "&action=manage_carts&userId=" + loginId + "&dishId=" + dishid + "&quantity="+quantityStr+"&menuId=addons&type=Add";


        // String nnnn="http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=get_pincode_details&pinCode=13312";

        // showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ADDTOCART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();

                        Log.d("addcartresponse", response.toString());
                        //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        addtocartBtn.setText("Added Successfully");
                        addtocartBtn.setEnabled(false);
                        count++;
                        countttTv.setText("" + count);
                        Log.d("counttt", "" + count);
                        JSONObject jobj = null;
                        Intent i=new Intent(AdonsDiscriptionActivity.this,AdonsActivity.class);
                        startActivity(i);
                        finish();
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            displyMessage = jobj.getString("displyMessage");
                            Log.d("orderstatus", "" + status);
                           /* addtocartBtn.setText("Added Successfully");
                            addtocartBtn.setEnabled(false);*/


                            if (status == true) {


                                JSONObject jsonObject = jobj.getJSONObject("data");
                                id = jsonObject.getString("id");
                                Log.d("cartidd", id);
                             /*   cityId = jsonObject.getString("cityId");
                                pinCode = jsonObject.getString("pinCode");
                                deliveryCharges = jsonObject.getString("deliveryCharges");
                                indexValue = jsonObject.getString("indexValue");
                                createdOn = jsonObject.getString("createdOn");
                                modifiedOn = jsonObject.getString("modifiedOn");
                                createdBy = jsonObject.getString("createdBy");
                                modifiedBy = jsonObject.getString("modifiedBy");*/
                                addtocartBtn.setText("Added Successfully");
                                addtocartBtn.setEnabled(false);
                                count++;
                                countttTv.setText("" + count);
                                Log.d("counttt", "" + count);


                            } else if (status == false && displyMessage.equals("Already Exist!")) {
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
                        // hideProgressDialog();
                        Log.d("error", error.toString());
                    }
                }) {
          /*  @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_USERID, LoginActivity.id);
                return params;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDuplicateDish() {


        String checkcart_url=Utility.Base_Url+"&action=check_cart&dishId="+dishId+"&userId="+loginId+"&menuId="+menuId;

        Log.d("checkcart_url",checkcart_url);

        //showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, checkcart_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("checkcartresponse",response.toString());
                        JSONObject jobj= null;
                        try {

                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            String displaymsg=jobj.getString("displyMessage");
                            if (status==true) {
                                //Toast.makeText(getApplicationContext(),displaymsg,Toast.LENGTH_LONG).show();
                                addtocartBtn.setText("Already Added");
                                addtocartBtn.setBackgroundColor(getResources().getColor(R.color.gpsbtn));
                                addtocartBtn.setClickable(false);
                            }
                            else if (status==false) {
                                //Toast.makeText(getApplicationContext(),displaymsg,Toast.LENGTH_LONG).show();
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
        Intent i = new Intent(AdonsDiscriptionActivity.this, AdonsActivity.class);
        startActivity(i);


        /*Alcarter alcarter = new Alcarter();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, alcarter).commit();*/
    }
}


 /*   private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();


    }*/
