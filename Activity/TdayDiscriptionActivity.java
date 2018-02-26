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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Adapter.TdayAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TdayDiscriptionActivity extends AppCompatActivity {
    String  dishId,menuId,ddishimage, ddishcode, ddishname, ddishdescription, dprice,id,displyMessage;
    TextView ddishcodeTv,ddishNameTv,ddishPriceTv,countttTv,quntityTxt;
    ImageView dishImg;
    Button addtocartbtn,addBtn,subBtn;
    Dialog dialog;
    Context context=this;
    ProgressDialog pDialog;
    Toolbar toolbar;
    ImageView cartimg;
    ListView dayListView,timeListView;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId,deliveryCharges,startTime,endTime,quantityStr;
    int countQuntity=1;
    TdayAdapter tdayAdapter;
    Calendar calendar1,calendar2,calander3,calendar4;
    Date time1,time2,time3,commenTime;
    boolean isQuantity=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_discription);
        Global.isbreakfastClick=true;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dishImg = (ImageView)findViewById(R.id.dishImg);
        ddishcodeTv = (TextView)findViewById(R.id.dishcode);
        ddishNameTv = (TextView)findViewById(R.id.dishName);
        ddishPriceTv = (TextView)findViewById(R.id.dishPrice);
        quntityTxt = (TextView) findViewById(R.id.quntityTxt);
        /*pincodeEt = (EditText) findViewById(R.id.pincodetxt);
        deliverychargeTv =(TextView)findViewById(R.id.deliverycharge);*/
        addtocartbtn=(Button) findViewById(R.id.addtocart);
        addBtn = (Button) findViewById(R.id.addBtn);
        subBtn = (Button) findViewById(R.id.subBtn);
        //checkBtn = (Button) findViewById(R.id.checkBtn);
        countttTv = (TextView)findViewById(R.id.counttt);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        addtocartbtn.setText("Add to Cart");
        addtocartbtn.setEnabled(true);
        cartimg=(ImageView)findViewById(R.id.cartimg);
       /* cartimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.istdayBackclick=true;
                Intent i =new Intent(TdayDiscriptionActivity.this,Maincart.class);
                startActivity(i);
                finish();
            }
        });*/








        /*cartimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TdayDiscriptionActivity.this,CartActivity.class);
                startActivity(i);
                finish();
            }
        });*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.tdaydiscription));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TdayDiscriptionActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        Intent intent=getIntent();

       /* ddishimage=intent.getStringExtra("ddishImg");
        ddishcode=intent.getStringExtra("ddishCode");
        ddishname=intent.getStringExtra("ddishName");
        ddishdescription=intent.getStringExtra("ddishDiscription");
        dprice=intent.getStringExtra("ddishPrice");*/

        dishId=intent.getStringExtra("dishIdTday");
        ddishname=intent.getStringExtra("dishnameTday");
        ddishcode=intent.getStringExtra("dishcodeTday");
        dprice=intent.getStringExtra("dishpriceTday");
        ddishimage=intent.getStringExtra("dishImgTday");
        menuId=intent.getStringExtra("menuId");
        ddishdescription=intent.getStringExtra("dishDiscriptionTday");
        startTime=intent.getStringExtra("startTime");
        endTime=intent.getStringExtra("endTime");

        Log.d("startTime",startTime);
        Log.d("endTime",endTime);

        time1 = null;
        try {
            time1 = new SimpleDateFormat("HH:mm").parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);

        time2 = null;

        try {
            time2 = new SimpleDateFormat("HH:mm").parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);
        calendar2.add(Calendar.DATE, 1);

        //Toast.makeText(getApplicationContext(),"Start Time: "+startTime+" End Time: "+endTime+"",Toast.LENGTH_SHORT).show();

        /*Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("IST (UTC +5:30)"));
        String localTime = date.format(currentLocalTime);*/

        calander3 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(calander3.getTime());
        Log.d("currenttime",time);

        time3 = null;
        try {
            time3 = new SimpleDateFormat("HH:mm").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar4 = Calendar.getInstance();
        calendar4.setTime(time3);
        calendar4.add(Calendar.DATE, 1);
        commenTime = calendar4.getTime();
        Log.d("commenTime",""+commenTime);



        //Toast.makeText(getApplicationContext(),time,Toast.LENGTH_SHORT).show();

        ddishcodeTv.setText(ddishcode);
        ddishNameTv.setText(ddishname);
        ddishPriceTv.setText("₹"+dprice);
        Picasso.with(this)
                .load(ddishimage)
                .into(dishImg);

        if (Network.isNetworkCheck(getApplicationContext())) {

            getDuplicateDish();

        }
        else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }

        /*checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {
                    //String pincodeStr=pincodeEt.getText().toString();

                    //String pincode_url= Utility.Base_Url+"&action=get_pincode_details&pinCode="+pincodeStr;

                    getpinCode();
                    //Parser.getpinCode(pincode_url,deliveryCharges);

                }
                else {
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


        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.istdayBackclick=true;
                if (Network.isNetworkCheck(getApplicationContext())) {
                    if (isQuantity==true) {

                        getcart();

                        /*if ((commenTime.after(calendar1.getTime()) || commenTime.compareTo(calendar1.getTime()) == 0)
                                && commenTime.before(calendar2.getTime())) {
                            Toast.makeText(getApplicationContext(), "Time match", Toast.LENGTH_SHORT).show();*/

                        /*} else {
                            Toast.makeText(getApplicationContext(), "Please order Dishes in Between " + startTime + " To " + endTime, Toast.LENGTH_SHORT).show();
                        }*/
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.quntityadd), Toast.LENGTH_LONG).show();
                    }
                   /* if (commenTime.after(calendar1.getTime()) && commenTime.before(calendar2.getTime())) {
                        //checkes whether the current time is between 14:49:00 and 20:11:13.
                        Toast.makeText(getApplicationContext(),"Time match",Toast.LENGTH_SHORT).show();
                        getcart();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please order Dishes in Between "+startTime+" To " +endTime,Toast.LENGTH_SHORT).show();
                    }*/

                    //getotpverified();
                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
public void onBackPressed() {
    super.onBackPressed();
        Global.istdayBackclick=true;
    Intent i = new Intent(TdayDiscriptionActivity.this, HomeActivity.class);
    startActivity(i);
    finish();
}

    public void getcart() {

        String dishIdd=getIntent().getStringExtra("dishIdTday");
        // Toast.makeText(getApplicationContext(),LoginActivity.id,Toast.LENGTH_LONG).show();



        //String URL_ADDTOCART = Utility.Base_Url+"&action=manage_carts&type=Add&dishId="+dishIdd+"&userId="+loginId;
        String URL_ADDTOCART = Utility.Base_Url+"&action=manage_carts&userId="+loginId+"&dishId="+dishIdd+"&quantity="+quantityStr+"&menuId=todaysmenu&type=Add";

        Log.d("orderUrl", URL_ADDTOCART);

        // showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ADDTOCART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // hideProgressDialog();

                        Log.d("orderresponse", response.toString());
                        JSONObject jobj = null;
                        addtocartbtn.setText("Added Successfully");
                        addtocartbtn.setEnabled(false);
                        int count=0;
                        count++;
                        countttTv.setText(""+count);
                        Log.d("counttt",""+count);
                        Intent i=new Intent(TdayDiscriptionActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish();
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            displyMessage=jobj.getString("displyMessage");
                            Log.d("orderstatus", "" + status);
                            if (status == true) {

                                 //Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_LONG).show();

                                JSONObject jsonObject = jobj.getJSONObject("data");
                                id = jsonObject.getString("id");
                                addtocartbtn.setText("Added Successfully");
                                addtocartbtn.setEnabled(false);
                            }
                            else if(status ==false &&displyMessage.equals("displyMessage")) {
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
                        // hideProgressDialog();
                        Log.d("error", error.toString());
                    }
                }) {
           /* @Override
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
                                addtocartbtn.setText("Already Added");
                                addtocartbtn.setBackgroundColor(getResources().getColor(R.color.gpsbtn));
                                addtocartbtn.setClickable(false);
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




}










