package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeliveryAddressActivity extends AppCompatActivity {
    TextView titleTv;
    ImageView backIv;
    EditText streetEt,localityEt;
    TextView sectorTv,cityTv,stateTv;
    Button submitBtn;
    String streetStr,localityStr;
    String sectorStr="",cityStr="",stateStr="",completeaddress="",globCompAddress="";
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;
    public String quanityArrStr,menuidArrStr,categoryArrStr,dishIdArrStr,selectedtimeStr,currentDate,tomorrowAsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        titleTv = (TextView)findViewById(R.id.title);
        backIv = (ImageView)findViewById(R.id.back);
        streetEt = (EditText)findViewById(R.id.streetEt);
        localityEt = (EditText)findViewById(R.id.localityEt);
        sectorTv = (TextView)findViewById(R.id.sectorTv);
        cityTv = (TextView)findViewById(R.id.cityTv);
        stateTv = (TextView)findViewById(R.id.stateTv);
        submitBtn = (Button)findViewById(R.id.submitBtn);

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");



        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());
        Log.d("currentDate",currentDate);



       if (Global.isDenyloc==true) {


           sectorStr = HomeActivity.regionName;
           cityStr = HomeActivity.cityname;
           stateStr = HomeActivity.stateName;
           completeaddress=sectorStr+" , "+cityStr+" , "+stateStr;
           Log.d("completeaddress",completeaddress);
       }
        else if (Global.isAllowloc==true) {

           sectorStr = LocationActivity.address;
           cityStr = LocationActivity.city;
           stateStr = LocationActivity.state;
           completeaddress=sectorStr+" , "+cityStr+" , "+stateStr;
           Log.d("completeaddress",completeaddress);
       }

        Log.d("sectorStr",sectorStr);
        Log.d("cityStr",cityStr);
        Log.d("stateStr",stateStr);

        sectorTv.setText(sectorStr);
        cityTv.setText(cityStr);
        stateTv.setText(stateStr);




        titleTv.setText("Delivery Address");
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DeliveryAddressActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                streetStr=streetEt.getText().toString();
                localityStr=localityEt.getText().toString();
                Log.d("streetStr",streetStr);
                Log.d("localityStr",localityStr);
                globCompAddress=streetStr+" , "+localityStr+" , "+completeaddress;
                if (streetStr.equals("")) {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.street),Toast.LENGTH_SHORT).show();
                }
                else if (localityStr.equals("")) {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.locality),Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Network.isNetworkCheck(getApplicationContext())) {


                        if (Global.isTdaypay == true) {
                            gettdaypayment();
                            Global.isTdaypay = false;
                        } else if (Global.isPrepay == true) {
                            getprepayment();
                            Global.isPrepay = false;
                        } else if (Global.isTiffinpay == true) {

                            getTiffinpayment();
                            Global.isTiffinpay = false;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }

    public void gettdaypayment() {

        quanityArrStr=CartActivity.quanityArrStr;
        menuidArrStr=CartActivity.menuidArrStr;
        categoryArrStr=CartActivity.categoryArrStr;
        dishIdArrStr=CartActivity.dishIdArrStr;
        selectedtimeStr=CartActivity.selectedtimeStr;

        String url_payment=Utility.Base_Url+"&action=manage_orders&cart_items="+dishIdArrStr+"&quantity="+quanityArrStr+"&menuId="+menuidArrStr+
                "&deliveryTime="+selectedtimeStr+"&deliveryMenu="+categoryArrStr+"&userId="+loginId+"&subTotal="+Global.TotalPrice+"&totalAmount="
                +CheckoutActivity.totalcharges+"&deliverycostId=1"+"&deliveryDate="+currentDate+"&address="+globCompAddress+"&discountId=0";

        String url_pay=url_payment.replaceAll("\\s+", "");

        Log.d("url_pay",url_pay);




        //String URL_PAYMENT="http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_orders&userId=150&dishId=4,5,7&discountId=1&categoryId=1&deliverycostId=2&totalAmount=1000&subTotal=800&deliveryDate=2017-03-18%2007:03:31&trackingId=1"+LoginActivity.id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_pay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // hideProgressDialog();
                        Log.d("orderdtlresponse", response.toString());
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            String status = jobj.getString("status");

                            Log.d("orderssstatus", "" + status);
                            if (status.equals("success")) {
                                String dataStr=jobj.getString("data");
                                String displyMessage=jobj.getString("displayMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                                Log.d("dataStr",dataStr);
                                //Toast.makeText(getApplicationContext(),dataStr,Toast.LENGTH_LONG).show();
                                Intent i=new Intent(DeliveryAddressActivity.this,PaymentActivity.class);
                                i.putExtra("dataStr",dataStr);
                                startActivity(i);
                                finish();
                                //Global.TotalPrice=0;


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


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }


    public void getTiffinpayment() {

        String url_tiffinpayment=Utility.Base_Url+"&action=manage_orders&cart_items=1&quantity=1&menuId=tiffin&userId="+loginId+"&address="+globCompAddress+
                "&subTotal="+TiffinCheckoutActivity.tiffinpriceStr+"&totalAmount="+TiffinCheckoutActivity.tiffinpriceStr+"&deliveryDate="+currentDate;

        String url_tiffinpay=url_tiffinpayment.replaceAll("\\s+", "");

        Log.d("url_tiffinpay",url_tiffinpay);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_tiffinpay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // hideProgressDialog();
                        Log.d("tiffinorderdtlres", response.toString());
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            String status = jobj.getString("status");

                            Log.d("orderssstatus", "" + status);
                            if (status.equals("success")) {
                                String dataStr=jobj.getString("data");
                                String displyMessage=jobj.getString("displayMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                                Log.d("dataStr",dataStr);
                                //Toast.makeText(getApplicationContext(),dataStr,Toast.LENGTH_LONG).show();
                                Intent i=new Intent(DeliveryAddressActivity.this,PaymentActivity.class);
                                i.putExtra("dataStr",dataStr);
                                startActivity(i);
                                finish();

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


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void getprepayment() {

        quanityArrStr=PreActivity.quanityArrStr;
        menuidArrStr=PreActivity.menuidArrStr;
        categoryArrStr=PreActivity.categoryArrStr;
        dishIdArrStr=PreActivity.dishIdArrStr;
        selectedtimeStr=PreActivity.selectedtimeStr;

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tomorrowAsString = dateFormat.format(tomorrow);
        Log.d("tomorrowAsString",tomorrowAsString);

        String url_prepayment=Utility.Base_Url+"&action=manage_orders&cart_items="+dishIdArrStr+"&quantity="+quanityArrStr+"&menuId="+menuidArrStr+
                "&deliveryTime="+selectedtimeStr+"&deliveryMenu="+categoryArrStr+"&userId="+loginId+"&subTotal="+Global.TotalPrice+"&totalAmount="
                +PreCheckoutActivity.totalcharges+"&deliverycostId=1"+"&deliveryDate="+tomorrowAsString+"&address="+globCompAddress+"&discountId=0";

        String url_prepay=url_prepayment.replaceAll("\\s+", "");

        Log.d("url_prepay",url_prepay);




        //String URL_PAYMENT="http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_orders&userId=150&dishId=4,5,7&discountId=1&categoryId=1&deliverycostId=2&totalAmount=1000&subTotal=800&deliveryDate=2017-03-18%2007:03:31&trackingId=1"+LoginActivity.id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_prepay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // hideProgressDialog();
                        Log.d("orderdtlresponse", response.toString());
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            String status = jobj.getString("status");

                            Log.d("orderssstatus", "" + status);
                            if (status.equals("success")) {
                                String dataStr=jobj.getString("data");
                                String displyMessage=jobj.getString("displayMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                                Log.d("dataStr",dataStr);
                                //Toast.makeText(getApplicationContext(),dataStr,Toast.LENGTH_LONG).show();
                                Intent i=new Intent(DeliveryAddressActivity.this,PaymentActivity.class);
                                i.putExtra("dataStr",dataStr);
                                startActivity(i);
                                finish();
                                //Global.TotalPrice=0;


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


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
