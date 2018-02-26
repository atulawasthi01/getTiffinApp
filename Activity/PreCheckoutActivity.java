package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mohit.gupta on 4/14/2017.
 */

public class PreCheckoutActivity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView backIv;
    TextView titleTv,subtotalTv,deliverychargeTv,totalTv,couponcodeTv,pincodeerrorTv;
    EditText pincodeEt,couponcodeEt;
    Button checkoutBtn,pincodeBtn,applyBtn;
    /*RadioGroup addressGrp;
    RadioButton homeRd,officeRd;*/
    public String displyMessage,couponcode,amount,deliveryCharges;
    SharedPreferences dishpriceSp;
    public static final String dishpricePrefrence = "price";
    public static int totaldishPrice,delcharges,totalcharges,globaltotalcharges;
    String id,userId,title,description,couponCode,amountt,image,startDate,validDate,indexValue,createdOn,modifiedon,modifiedBy,createdby;
    JSONArray couponArray;
    String[] couponArr;
    String[] couponidArr;
    String[] amountArr;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId,homeAddress,officeAddress,pincodeId,delivaryTime="0.0",deliveryadtetimeStr="January 00 2000 00:00 p.m.",couponId="0",dataStr;
    boolean ispincodeClick=false;
    String deliveryAddress,delDate,delTime,delAddress;
    String homeStr,officeStr,addressStr, currentDateStr,delAddressStr,couponcodeStr;
    String predishStr,precatIdStr,prettlAmountStr,predeliveryDate,predeliveryTime,quantityStr,premenuStr;
    SharedPreferences profileSp,profileimgSp;
    public static final String profilePrefrence = "profileSp";
    int prettlamountsub;
    String finaltotalPrice="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutdetail);
        backIv = (ImageView) findViewById(R.id.back);
        titleTv = (TextView) findViewById(R.id.title);
        subtotalTv = (TextView) findViewById(R.id.subtotal);
        deliverychargeTv = (TextView) findViewById(R.id.deliverycharge);
        totalTv = (TextView) findViewById(R.id.total);
        pincodeEt = (EditText) findViewById(R.id.pincodetxt);
        //deladdressTv = (TextView) findViewById(R.id.deladdressTxt);
        pincodeerrorTv = (TextView) findViewById(R.id.pincodeerrortxt);
        couponcodeEt = (EditText) findViewById(R.id.couponcode);
        checkoutBtn = (Button) findViewById(R.id.checkbtn);
        pincodeBtn = (Button) findViewById(R.id.pincodeBtn);
       /* addressGrp = (RadioGroup)findViewById(R.id.dtyperdgrp);
        homeRd = (RadioButton)findViewById(R.id.homechk);
        officeRd = (RadioButton)findViewById(R.id.officechk);*/
        finaltotalPrice=String.valueOf(Global.TotalPrice);
        Log.d("finaltotalPrice",finaltotalPrice);

        couponcodeTv = (TextView) findViewById(R.id.couponcodetv);
        applyBtn = (Button) findViewById(R.id.applyBtn);
        pincodeerrorTv.setText("");

        titleTv.setText(getResources().getString(R.string.checkoutdtl));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i=new Intent(PreCheckoutActivity.this, HomeActivity.class);
                startActivity(i);*/
                finish();
            }
        });
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        /*homeAddress = loginSp.getString("deliveryAddress","");
        officeAddress = loginSp.getString("officeAddress","");*/

        /*profileSp = getSharedPreferences(profilePrefrence, MODE_PRIVATE);
        homeAddress=profileSp.getString("deliveryAddress","");
        officeAddress=profileSp.getString("officeAddress","");
        delAddressStr=homeAddress;
        deladdressTv.setText(homeAddress);
        Log.d("homeAddress",homeAddress);
        Log.d("officeAddress",officeAddress);*/

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateStr = df.format(c.getTime());
        Log.d("Currenttime",currentDateStr);





        /*deliveryAddress=Maincart.delivaryTime;
        Log.d("deliveryAddressss",deliveryAddress);
        delDate=deliveryAddress.substring(0,16);
        delTime=deliveryAddress.substring(17,27);
        Log.d("substr",delTime);*/




        //Log.d("delivaryTime",Maincart.delivaryTime);





        dishpriceSp = getSharedPreferences(dishpricePrefrence, MODE_PRIVATE);
        predishStr = dishpriceSp.getString("predishIdStr","");
        precatIdStr = dishpriceSp.getString("catidd","");
        prettlAmountStr = dishpriceSp.getString("totalprice","");
        quantityStr = dishpriceSp.getString("quantity","");
        premenuStr = dishpriceSp.getString("menuId","");
       /* predeliveryDate = dishpriceSp.getString("deliveryDatePre","");
        predeliveryTime = dishpriceSp.getString("deliveryTimePre","");*/
        predeliveryDate=PreOrderSelectListActivity.tomorrowAsString;
        predeliveryTime=PreOrderSelectListActivity.deliveryTimee;








        subtotalTv.setText("₹"+ Global.TotalPrice);

        prettlamountsub= Global.TotalPrice;




        pincodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ispincodeClick=true;
                if (Network.isNetworkCheck(getApplicationContext())) {
                    getpinCode();
                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }
            }
        });
        /*Log.d("dellchare",deliveryCharges);
        delcharges= Integer.parseInt(deliveryCharges);
        totalcharges=totaldishPrice+delcharges;
        Log.d("totalchagesss",""+totalcharges);*/

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Global.isPrepay=true;


                if (Network.isNetworkCheck(getApplicationContext())) {
                    if (ispincodeClick == true) {
                        Intent i=new Intent(PreCheckoutActivity.this,DeliveryAddressActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please get Pincode First",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }

            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {
                    if (ispincodeClick == true) {
                        if (prettlamountsub >= 100) {
                            getoffers();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.couponcodeerr), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please get Pincode First",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                }
            }
        });

       /* addressGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.homechk:
                        homeStr = ((RadioButton)findViewById(addressGrp.getCheckedRadioButtonId())).getText().toString();
                        addressStr=homeStr;
                        deladdressTv.setText(homeAddress);
                        delAddressStr=homeAddress;
                        Log.d("delAddressStr1",delAddressStr);

                        break;
                    case R.id.officechk:
                        officeStr = ((RadioButton)findViewById(addressGrp.getCheckedRadioButtonId())).getText().toString();
                        addressStr=officeStr;
                        deladdressTv.setText(officeAddress);
                        delAddressStr=officeAddress;
                        Log.d("delAddressStr2",delAddressStr);

                        break;
                }
            }
        });*/
    }






    public void getpinCode() {

        String pincodeStr=pincodeEt.getText().toString();
        String pincode_url= Utility.Base_Url+"&action=get_pincode_details&pinCode="+pincodeStr;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, pincode_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();

                        Log.d("pincoderesponse", response.toString());
                        //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            displyMessage =jobj.getString("displyMessage");
                            Log.d("orderstatus", "" + status);


                            if (status == true) {


                                JSONObject jsonObject = jobj.getJSONObject("data");
                                pincodeId = jsonObject.getString("id");
                                String pincode=jsonObject.getString("pinCode");
                                deliveryCharges=jsonObject.getString("deliveryCharges");

                                deliverychargeTv.setText("₹"+deliveryCharges);

                                Log.d("dellchare",deliveryCharges);
                                delcharges= Integer.parseInt(deliveryCharges);
                                //int prettlAmount= Integer.parseInt(prettlAmountStr);
                                totalcharges=Global.TotalPrice+delcharges;
                                Log.d("totalchagesss",""+totalcharges);
                                totalTv.setText("₹"+totalcharges);
                                globaltotalcharges=totalcharges;
                                pincodeerrorTv.setText("");
                                checkoutBtn.setClickable(true);

                            }

                            else if(status ==false ) {

                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.pincodeerr),Toast.LENGTH_LONG).show();
                                pincodeerrorTv.setText("Not Available Coming soon");
                                deliverychargeTv.setText("₹ 00");
                                totalTv.setText("₹"+Global.TotalPrice);
                                //totalTv.setText(""+totaldishPrice);
                                checkoutBtn.setClickable(false);

                            }
                            else {

                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                                //totalTv.setText(""+totaldishPrice);
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


    public void getpayment() {

        Log.d("globaltotalcharges",""+globaltotalcharges);
        Log.d("subtotal",""+totaldishPrice);
        Log.d("couponId",couponId);
        //String URL_PAYMENT=Utility.Base_Url+"&action=manage_orders&userId="+loginId+"&dishId="+Maincart.disStr+"&discountId="+couponId+"&categoryId="+Maincart.catId+"&deliverycostId="+pincodeId+"&totalAmount="+""+globaltotalcharges+"&subTotal="+""+totaldishPrice+"&deliveryDate="+Maincart.delivaryTime+"&trackingId=0";
        String URL_PREORDER_PAYMENT=Utility.Base_Url+"&action=manage_orders&userId="+loginId+"&deliverycostId="+pincodeId+"&quantity="+quantityStr+"&deliveryTime="+predeliveryTime+"&deliveryDate="+predeliveryDate+"&deliveryAddress="+delAddressStr+"&categoryId="+precatIdStr+"&dishId="+predishStr+"&discountId="+couponId+"&totalAmount="+globaltotalcharges+"&subTotal="+prettlAmountStr+"&menuId="+premenuStr+"&isPreOrder=0";

        Log.d("URL_PREORDER",URL_PREORDER_PAYMENT);

        //String URL_PAYMENT="http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=manage_orders&userId=150&dishId=4,5,7&discountId=1&categoryId=1&deliverycostId=2&totalAmount=1000&subTotal=800&deliveryDate=2017-03-18%2007:03:31&trackingId=1"+LoginActivity.id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PREORDER_PAYMENT,
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
                                dataStr=jobj.getString("data");
                                displyMessage=jobj.getString("displayMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_LONG).show();
                                Log.d("dataStr",dataStr);
                                //Toast.makeText(getApplicationContext(),dataStr,Toast.LENGTH_LONG).show();
                                Intent i=new Intent(PreCheckoutActivity.this,PaymentActivity.class);
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

    private void getoffers() {
        couponcodeStr=couponcodeEt.getText().toString();
        String offercoupon_url=Utility.Base_Url+"&action=get_coupon_apply_details&couponCode="+couponcodeStr+"&userId="+loginId;
        Log.d("offercoupon_url",offercoupon_url);
        //offerCouponList = new ArrayList<OfferCoupon>();
        //showProgressDialog();
        //Log.d("loginidddd",LoginActivity.id);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, offercoupon_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("offerresponse", response.toString());
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            Boolean status = jobj.getBoolean("status");
                            Log.d("status",""+status);
                            //couponArray =jobj.getJSONArray("data");
                            if (status==false) {
                                JSONObject jsonObject = jobj.getJSONObject("data");

                                if (jsonObject!=null) {

                                    id = jsonObject.getString("id");
                                    userId = jsonObject.getString("userId");
                                    title = jsonObject.getString("title");
                                    description = jsonObject.getString("description");
                                    couponCode = jsonObject.getString("couponCode");
                                    amountt = jsonObject.getString("amount");
                                    image = jsonObject.getString("image");
                                    startDate = jsonObject.getString("startDate");
                                    validDate = jsonObject.getString("validDate");
                                    indexValue = jsonObject.getString("indexValue");
                                    createdOn = jsonObject.getString("createdOn");
                                    modifiedon = jsonObject.getString("modifiedOn");
                                    modifiedBy = jsonObject.getString("modifiedBy");
                                    createdby = jsonObject.getString("createdBy");
                                    String displyMessage=jobj.getString("displyMessage");

                                    couponcodeTv.setText("Coupon Code Amount: ₹" + amountt);
                                    int amt = Integer.parseInt(amountt);
                                    //Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_SHORT).show();
                                    totalcharges = totalcharges - amt;
                                    totalTv.setText("₹" + totalcharges);
                                    applyBtn.setClickable(false);
                                }
                                else if (jsonObject==null) {
                                    String displyMessage=jobj.getString("displyMessage");
                                    Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_SHORT).show();
                                }

                            }
                            else if (status==true) {
                                Toast.makeText(getApplicationContext(),"Coupon Already Used",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(getApplicationContext(),displyMessage,Toast.LENGTH_SHORT).show();
                            }

                            /*for (int i = 0; i < couponArray.length(); i++) {


                                JSONObject obj2 = couponArray.getJSONObject(i);
                                id = obj2.getString("id");
                                userId = obj2.getString("userId");
                                title = obj2.getString("title");
                                description = obj2.getString("description");
                                couponCode = obj2.getString("couponCode");
                                amountt = obj2.getString("amount");
                                image = obj2.getString("image");
                                startDate = obj2.getString("startDate");
                                validDate = obj2.getString("validDate");
                                indexValue = obj2.getString("indexValue");
                                createdOn = obj2.getString("createdOn");
                                modifiedon = obj2.getString("modifiedOn");
                                modifiedBy = obj2.getString("modifiedBy");
                                createdby = obj2.getString("createdBy");


                                OfferCoupon offerCoupon = new OfferCoupon();
                                offerCoupon.setId(id);
                                offerCoupon.setUserId(userId);
                                offerCoupon.setTitle(title);
                                offerCoupon.setDescription(description);
                                offerCoupon.setCouponCode(couponCode);
                                offerCoupon.setAmount(amount);
                                offerCoupon.setImage(image);
                                offerCoupon.setStartDate(startDate);
                                offerCoupon.setValidDate(validDate);
                                offerCoupon.setIndexValue(indexValue);
                                offerCoupon.setCreatedOn(createdOn);
                                offerCoupon.setModifiedBy(modifiedBy);
                                offerCoupon.setModifiedon(modifiedon);
                                offerCoupon.setCreatedby(createdby);

                                Log.d("couponCode",couponCode);
                                Log.d("amountt",amountt);

                                //Toast.makeText(getApplicationContext(),couponCode,Toast.LENGTH_LONG).show();






                                //Toast.makeText(getActivity(),""+breakFastList.toString(),Toast.LENGTH_LONG).show();

                            }

                            Log.d("couponarray",""+couponArray.length());
                            Log.d("couponCode",couponCode);

                            //Log.d("couponcodeeee",couponcode);
                            couponArr=new String[couponArray.length()];
                            amountArr=new String[couponArray.length()];
                            couponidArr=new String[couponArray.length()];*/

                            /*couponcodeEt.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {




                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    if (ispincodeClick==true) {


                                        String couponcodeStr = couponcodeEt.getText().toString();
                                        Log.d("couponarrayyy", "" + couponArray.length());
                                        Log.d("amountt", amountt);

                                        for (int p = 0; p < couponArray.length(); p++) {
                                            Log.d("couponcodeeeeee", couponCode);
                                            Log.d("totalcharges", "" + totalcharges);

                                            couponArr[p] = couponCode;
                                            couponidArr[p] = id;
                                            couponId=couponidArr[p];
                                            if (couponArr[p].equals(couponcodeStr)) {
                                                amountArr[p] = amountt;
                                                Toast.makeText(getApplicationContext(), "" + amountArr[p], Toast.LENGTH_LONG).show();
                                                Log.d("amountarrr", "" + amountArr[p]);
                                                int amunt = Integer.parseInt(amountArr[p]);
                                                Toast.makeText(getApplicationContext(), "chal gya", Toast.LENGTH_LONG).show();
                                                Log.d("totalcharges", "" + totalcharges);
                                                Log.d("amunt", "" + amunt);
                                                totalcharges = totalcharges - amunt;
                                                totalTv.setText("" + totalcharges);

                                            } else {
                                                //Toast.makeText(getApplicationContext(),"nhi chala",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } else if (ispincodeClick==false) {
                                        Toast.makeText(CheckoutActivity.this, "Please Get Pin Code First", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });*/






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
                }); /*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENO, mobileStr);
                params.put(Utility.KEY_PASSWORD, pwdStr);
                return params;
            }
        };
*/
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        Intent i=new Intent(PreCheckoutActivity.this,HomeActivity.class);
        startActivity(i);*/
        finish();
    }
}
