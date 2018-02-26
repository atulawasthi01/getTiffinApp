package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.yumtiff.mohitkumar.tiffin.Adapter.Historyadapter;
import com.yumtiff.mohitkumar.tiffin.Adapter.HistAdapter;

import com.yumtiff.mohitkumar.tiffin.Model.Child;
import com.yumtiff.mohitkumar.tiffin.Model.Group;
import com.yumtiff.mohitkumar.tiffin.Model.History;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    Context context = this;
    Dialog dialog;
    ImageView backIv;
    TextView titleTv;
    ListView historylistview;
    RatingBar ratingBar;
    Button cancelrateBtn,submitrateBtn;
    List<History> historyList;
    Historyadapter historyadapter;
    ListView listView;
    ExpandableListView exphistryListView;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId;
    String[] catonetitleArr;
    String[] catonepriceArr;
    String[] catonedisArr;
    String[] cattwotitleArr;
    String[] cattwopriceArr;
    String[] cattwodisArr;
    String[] catthreetitleArr;
    String[] catthreepriceArr;
    String[] catthreedisArr;
    String ratingStr;
    ProgressDialog pDialog;
    HistAdapter preorderrrAdapter;
    int[] catoneimgArr = {
            R.drawable.chicken,
            R.drawable.thali
    };
    int[] cattwoimgArr = {
            R.drawable.burger,
            R.drawable.thali};
    int[] catthreeimgArr = {
            R.drawable.aalizera,
            R.drawable.rice};
    String id, trackingid, quantity, userid, dishid, image, dishname, paymentmethodid, regionid, subcategoryid, categoryid, totalamount, indexValue, dishprice, rating,
            createdOn, modifiedOn, createdBy, modifiedBy, displyMessage, confirmedby, ordersequencenumber, discountid, delievercostid, delieverydate, dishimage, dishdescription, dishcode;

    public ArrayList<Group> foodgrpList = new ArrayList<Group>();
    public static ArrayList<ArrayList<Child>> list2;
    List<String> foodNameList;
    List<Group> foodList;
    ArrayList<Child> childList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        exphistryListView = (ExpandableListView) findViewById(R.id.hisList);
        catonetitleArr = this.getResources().getStringArray(R.array.foodtitle);
        catonepriceArr = this.getResources().getStringArray(R.array.foodprice);
        catonedisArr = this.getResources().getStringArray(R.array.fooddiscription);
        cattwotitleArr = this.getResources().getStringArray(R.array.foodtitletwo);
        cattwopriceArr = this.getResources().getStringArray(R.array.foodpricetwo);
        cattwodisArr = this.getResources().getStringArray(R.array.fooddiscription);
        catthreetitleArr =this.getResources().getStringArray(R.array.foodtitlethree);
        catthreepriceArr = this.getResources().getStringArray(R.array.foodpricethree);
        catthreedisArr = this.getResources().getStringArray(R.array.fooddiscription);

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");


        backIv = (ImageView)findViewById(R.id.back);
        titleTv = (TextView)findViewById(R.id.title);
        titleTv.setText(getResources().getString(R.string.myhistory));
        historyList = new ArrayList<History>();
        //dinnerList = new ArrayList<Dinner>();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        if (Network.isNetworkCheck(getApplicationContext())) {
            gethistory();
        }
        else {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
        }

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HistoryActivity.this,MyAccountActivity.class);
                startActivity(i);
                finish();
            }
        });

      exphistryListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
          @Override
          public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
              getratingDish();
              return true;
          }
      });
    }

    private void getratingDish() {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.show();
        dialog.setContentView(R.layout.ratedish_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        ratingBar = (RatingBar)dialog.findViewById(R.id.ratingBar);
        cancelrateBtn = (Button)dialog.findViewById(R.id.cancelBtn);
        submitrateBtn = (Button)dialog.findViewById(R.id.submitBtn);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                final int numStars = ratingBar.getNumStars();

                ratingStr = String.valueOf(ratingBar.getRating());
                //Toast.makeText(getApplicationContext(),ratingStr,Toast.LENGTH_LONG).show();
            }
        });


        submitrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network.isNetworkCheck(getApplicationContext())) {

                    //getrating();
                }
                else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HistoryActivity.this,MyAccountActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HistoryActivity.this, MyAccountActivity.class);
        startActivity(i);
        finish();

    }

  /*  private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }*/

  private void gethistory() {

      foodNameList = new ArrayList<String>();
      foodList = new ArrayList<Group>();
      final List<Child> list = new ArrayList<>();
      list2 = new ArrayList<ArrayList<Child>>();
      List<Child> list3 = new ArrayList<>();

      String history_url=Utility.Base_Url+"&action=get_order_history&userId="+loginId+"&trackingId=4";

      //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=get_order_history&userId=265&trackingId=4

      StringRequest stringRequest = new StringRequest(Request.Method.GET, history_url,
    // public static   String urll="http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=get_offers_list&userId=54";
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      // hideProgressDialog();
                      //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                      Log.d("historyresponse", response.toString());
                      JSONObject jobj = null;

                      try {
                          JSONObject jsonObject = new JSONObject(response);
                          boolean statusss=jsonObject.getBoolean("status");

                          if (statusss==true) {
                          JSONArray array = jsonObject.getJSONArray("data");

                          for (int i = 0; i < array.length(); i++) {

                              JSONObject obj = array.getJSONObject(i);

                              String id = obj.getString("id");
                              String orderSequenceNumber = obj.getString("orderSequenceNumber");
                              String confirmedBy = obj.getString("confirmedBy");
                              String userId = obj.getString("userId");
                              String dishId = obj.getString("dishId");
                              String trackingId = obj.getString("trackingId");
                              String isPreOrder = obj.getString("isPreOrder");
                              String paymentMethodId = obj.getString("paymentMethodId");
                              String discountId = obj.getString("discountId");
                              String categoryId = obj.getString("categoryId");
                              String deliverycostId = obj.getString("deliverycostId");
                              String totalAmount = obj.getString("totalAmount");
                              String subTotal = obj.getString("subTotal");
                              String deliveryDate = obj.getString("deliveryDate");
                              String indexValue = obj.getString("indexValue");
                              String createdOn = obj.getString("createdOn");
                              String modifiedOn = obj.getString("modifiedOn");
                              String createdBy = obj.getString("createdBy");
                              String modifiedBy = obj.getString("modifiedBy");
                              String status = obj.getString("status");

                              Group foodGroup = new Group();

                              foodGroup.setId(id);
                              foodGroup.setOrderSequenceNumber(orderSequenceNumber);
                              foodGroup.setConfirmedBy(confirmedBy);
                              foodGroup.setUserId(userId);
                              foodGroup.setDishId(dishId);
                              foodGroup.setTrackingId(trackingId);
                              foodGroup.setIsPreOrder(isPreOrder);
                              foodGroup.setPaymentMethodId(paymentMethodId);
                              foodGroup.setDiscountId(discountId);
                              foodGroup.setCategoryId(categoryId);
                              foodGroup.setDeliverycostId(deliverycostId);
                              foodGroup.setTotalAmount(totalAmount);
                              foodGroup.setSubTotal(subTotal);
                              foodGroup.setDeliveryDate(deliveryDate);
                              foodGroup.setIndexValue(indexValue);
                              foodGroup.setCreatedOn(createdOn);
                              foodGroup.setModifiedOn(modifiedOn);
                              foodGroup.setCreatedBy(createdBy);
                              foodGroup.setModifiedBy(modifiedBy);
                              foodGroup.setStatus(status);

                              foodgrpList.add(foodGroup);


                              JSONArray childArray = obj.getJSONArray("dishDetails");
                              childList = new ArrayList<Child>();
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

                                  Child child = new Child();
                                  child.setId(idchild);
                                  child.setUserId(userIdchild);
                                  child.setCategoryId(categoryIdchild);
                                  child.setSubcategoryId(subcategoryIdchild);
                                  child.setRegionId(regionIdchild);
                                  child.setDiscountId(discountIdchild);
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


                          }
                          else if (statusss==false) {
                              Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                          }
                          preorderrrAdapter = new HistAdapter(HistoryActivity.this, foodgrpList, list2);
                          exphistryListView.setAdapter(preorderrrAdapter);
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
         /*   @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Utility.KEY_USERID, userid);
                params.put(Utility.KEY_TRACKINGID, trackingid);

                params.put("status","1");
                return params;



            }*/
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}



