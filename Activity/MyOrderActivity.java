package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Adapter.OrderStatusAdapter;
import com.yumtiff.mohitkumar.tiffin.Model.StatusChild;
import com.yumtiff.mohitkumar.tiffin.Model.StatusGroup;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    ImageView foodImg, backIv;
    ProgressDialog pDialog;
    Toolbar toolbar;
    ExpandableListView orderstatusexplView;
    TextView ordernumberTv, foodTitleTv, fooddiscriptionTv, foodpriceTv, deliverytimeTv, titleTv;
    Button cancelrefundBtn;
    public  ImageView orderprocessimg,inprocessimg,dispatchimg,deliverImg;
    RelativeLayout ordeprocessLayout;
    String id, ordersequenceno, isconfirmed, confirmedby, userid, dishid, paymentmethodid, discountid, categoryid, delieverycostid, totalamount, delieverydate, indexvalue,
            createdon, modifiedon, createdby, modiefiedby, status, displyMessage,isPreOrder,subTotal,deliveryTime,deliveryAddress;
    public static String trackingid;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;

    public ArrayList<StatusGroup> foodgrpList = new ArrayList<StatusGroup>();
    public static ArrayList<ArrayList<StatusChild>> list2;
    List<String> foodNameList;
    List<StatusGroup> foodList;
    ArrayList<StatusChild> childList;
    OrderStatusAdapter orderStatusAdapter;
    boolean isGroupClick=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        orderstatusexplView = (ExpandableListView)findViewById(R.id.orderstatusexplview);
        titleTv = (TextView) findViewById(R.id.title);
        orderprocessimg = (ImageView)findViewById(R.id.orderprocessimg);
        inprocessimg = (ImageView)findViewById(R.id.inprocessimg);
        dispatchimg = (ImageView)findViewById(R.id.dispatchimg);
        deliverImg = (ImageView)findViewById(R.id.deliverImg);
        /*orderprocessimg = (ImageView)findViewById(R.id.orderprocessimg);
        inprocessimg = (ImageView)findViewById(R.id.inprocessimg);
        dispatchimg = (ImageView)findViewById(R.id.dispatchimg);
        deliverImg = (ImageView)findViewById(R.id.deliverImg);
        ordeprocessLayout = (RelativeLayout)findViewById(R.id.ordeprocessLayout);
        ordeprocessLayout.setVisibility(View.GONE);*/
        /*foodImg = (ImageView) findViewById(R.id.catoneimg);
        ordernumberTv = (TextView) findViewById(R.id.ordernumber);
        foodTitleTv = (TextView) findViewById(R.id.titlefood);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        orderprocessimg = (ImageView)findViewById(R.id.orderprocessimg);
        inprocessimg = (ImageView)findViewById(R.id.inprocessimg);
        dispatchimg = (ImageView)findViewById(R.id.dispatchimg);
        deliverImg = (ImageView)findViewById(R.id.deliverImg);
        fooddiscriptionTv = (TextView) findViewById(R.id.discripfood);
        foodpriceTv = (TextView) findViewById(R.id.pricefood);
        deliverytimeTv = (TextView) findViewById(R.id.deliverytime);*/
        //cancelrefundBtn = (Button) findViewById(R.id.cancelrefundBtn);
        backIv = (ImageView) findViewById(R.id.back);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        Log.d("loginId",loginId);

        titleTv.setText(getResources().getString(R.string.myorder));
        backIv.setVisibility(View.INVISIBLE);
        /*backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"back",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MyOrderActivity.this,MyAccountActivity.class);
                startActivity(i);
                finish();
            }
        });*/

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);



        if (Network.isNetworkCheck(getApplicationContext())) {

            getmyorder();

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }

      /*  orderstatusexplView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                //isGroupClick=true;

                if (isGroupClick==true) {
                    ordeprocessLayout.setVisibility(View.VISIBLE);
                    isGroupClick=false;
                }
                else {
                    ordeprocessLayout.setVisibility(View.GONE);
                    isGroupClick=true;

                }
                return false;
            }
        });*/







       /* backIv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getApplicationContext(),"backkk",Toast.LENGTH_LONG).show();
               Intent i=new Intent(MyOrderActivity.this,MyAccountActivity.class);
               startActivity(i);
               finish();
           }
       });*/



    }



    public void getmyorder() {

        foodNameList = new ArrayList<String>();
        foodList = new ArrayList<StatusGroup>();
        list2 = new ArrayList<ArrayList<StatusChild>>();



        String URL_MYORDER = Utility.Base_Url + "&action=get_order_history&userId="+loginId+"&status=1";

        http://www.yumtiff.com/api/ws/controller/?access=true&action=get_order_history&userId=389&status=1

        Log.d("orderUrl", URL_MYORDER);

        //showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_MYORDER,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();

                        Log.d("orderresponse", response.toString());
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");
                            Log.d("orderstatus", "" + status);
                            if (status == true) {
                                JSONArray jsonArray=jobj.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getString("id");
                                    ordersequenceno = jsonObject.getString("orderSequenceNumber");
                                    userid = jsonObject.getString("userId");
                                    dishid = jsonObject.getString("dishId");
                                    trackingid = jsonObject.getString("trackingId");
                                    isPreOrder = jsonObject.getString("isPreOrder");
                                    paymentmethodid = jsonObject.getString("paymentMethodId");
                                    discountid = jsonObject.getString("discountId");
                                    categoryid = jsonObject.getString("categoryId");
                                    delieverycostid = jsonObject.getString("deliverycostId");
                                    totalamount = jsonObject.getString("totalAmount");
                                    subTotal = jsonObject.getString("subTotal");
                                    delieverydate = jsonObject.getString("deliveryDate");
                                    deliveryTime = jsonObject.getString("deliveryTime");
                                    deliveryAddress = jsonObject.getString("deliveryAddress");
                                    indexvalue = jsonObject.getString("indexValue");
                                    createdon = jsonObject.getString("createdOn");
                                    modifiedon = jsonObject.getString("modifiedOn");
                                    createdby = jsonObject.getString("createdBy");
                                    modiefiedby = jsonObject.getString("modifiedBy");
                                    displyMessage = jobj.getString("displyMessage");

                                    StatusGroup statusGroup=new StatusGroup();
                                    statusGroup.setId(id);
                                    statusGroup.setOrdersequenceno(ordersequenceno);
                                    statusGroup.setUserid(userid);
                                    statusGroup.setDishid(dishid);
                                    statusGroup.setTrackingid(trackingid);
                                    statusGroup.setIsPreOrder(isPreOrder);
                                    statusGroup.setPaymentmethodid(paymentmethodid);
                                    statusGroup.setDiscountid(discountid);
                                    statusGroup.setCategoryid(categoryid);
                                    statusGroup.setDelieverycostid(delieverycostid);
                                    statusGroup.setTotalamount(totalamount);
                                    statusGroup.setSubTotal(subTotal);
                                    statusGroup.setDelieverydate(delieverydate);
                                    statusGroup.setDeliveryAddress(deliveryAddress);
                                    statusGroup.setDeliveryTime(deliveryTime);

                                    foodgrpList.add(statusGroup);
                                    JSONArray childArray = jsonObject.getJSONArray("dishDetails");
                                    childList = new ArrayList<StatusChild>();
                                    for (int j = 0; j < childArray.length(); j++) {

                                        JSONObject childObj = childArray.getJSONObject(j);


                                        String idchild = childObj.getString("id");
                                        String userIdchild = childObj.getString("userId");
                                        String categoryIdchild = childObj.getString("categoryId");
                                        String subcategoryIdchild = childObj.getString("subcategoryId");
                                        String regionIdchild = childObj.getString("regionId");
                                        String discountIdchild = childObj.getString("discountId");
                                        String dishNamechild = childObj.getString("dishName");
                                        String dishPricechild = childObj.getString("dishPrice");
                                        String dishImagechild = childObj.getString("dishImage");
                                        String quantitychild = childObj.getString("quantity");
                                        String ratingchild = childObj.getString("rating");
                                        String dishDescriptionchild = childObj.getString("dishDescription");
                                        String dishCodechild = childObj.getString("dishCode");
                                        String statuschild = childObj.getString("status");
                                        String createdOnchild = childObj.getString("createdOn");
                                        String modifiedOnchild = childObj.getString("modifiedOn");

                                        StatusChild child = new StatusChild();
                                        child.setId(idchild);
                                        child.setUserId(userIdchild);
                                        child.setCategoryId(categoryIdchild);
                                        child.setDishName(dishNamechild);
                                        child.setDishPrice(dishPricechild);
                                        child.setDishImage(dishImagechild);
                                        child.setQuantity(quantitychild);
                                        child.setRating(ratingchild);
                                        child.setDishDescription(dishDescriptionchild);
                                        child.setDishCode(dishCodechild);
                                        child.setStatus(statuschild);
                                        child.setCreatedOn(createdOnchild);
                                        child.setModifiedOn(modifiedOnchild);

                                        childList.add(child);


                                    }

                                    list2.add(childList);
                                }



                                Log.d("ordersequenceno",ordersequenceno);
                                Log.d("trackingid", trackingid);
                                Log.d("displyMessage", displyMessage);


                               /* if (trackingid.equals("0")) {
                                    orderprocessimg.setBackground(getResources().getDrawable(R.drawable.ordersiconsgreen));
                                    inprocessimg.setBackground(getResources().getDrawable(R.mipmap.inprocess));
                                    dispatchimg.setBackground(getResources().getDrawable(R.mipmap.dispatched));
                                    deliverImg.setBackground(getResources().getDrawable(R.mipmap.delivered));

                                }
                                else if (trackingid.equals("1")) {
                                    orderprocessimg.setBackground(getResources().getDrawable(R.mipmap.orderplace));
                                    inprocessimg.setBackground(getResources().getDrawable(R.drawable.inprocessgreen));
                                    dispatchimg.setBackground(getResources().getDrawable(R.mipmap.dispatched));
                                    deliverImg.setBackground(getResources().getDrawable(R.mipmap.delivered));
                                }

                                else if (trackingid.equals("2")) {
                                    orderprocessimg.setBackground(getResources().getDrawable(R.mipmap.orderplace));
                                    inprocessimg.setBackground(getResources().getDrawable(R.mipmap.inprocess));
                                    dispatchimg.setBackground(getResources().getDrawable(R.drawable.dispatchedgreen));
                                    deliverImg.setBackground(getResources().getDrawable(R.mipmap.delivered));
                                }
                                else if (trackingid.equals("3")) {
                                    orderprocessimg.setBackground(getResources().getDrawable(R.mipmap.orderplace));
                                    inprocessimg.setBackground(getResources().getDrawable(R.mipmap.inprocess));
                                    dispatchimg.setBackground(getResources().getDrawable(R.mipmap.dispatched));
                                    deliverImg.setBackground(getResources().getDrawable(R.drawable.deliveredgreen));
                                }*/



                            } else if (status == false) {
                                displyMessage = jobj.getString("displyMessage");
                                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.usernamepwdinc), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                            }
                            orderStatusAdapter = new OrderStatusAdapter(MyOrderActivity.this, foodgrpList, list2);
                            orderstatusexplView.setAdapter(orderStatusAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hideProgressDialog();
                        Log.d("error", error.toString());
                    }
                }) {
           /* @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_ORDER_USERID, LoginActivity.id);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MyOrderActivity.this, MyAccountActivity.class);
        startActivity(i);
        finish();
    }



}