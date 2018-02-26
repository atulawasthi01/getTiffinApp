package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Adapter.Breakfastadapter;
import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdonsActivity extends AppCompatActivity {
    ListView listadonsListView;
    TextView titleTv;
    ImageView backIv;
    ProgressDialog pDialog;
    List<TdayChild> adonsList;
    Breakfastadapter breakfastadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adons);
        titleTv = (TextView)findViewById(R.id.title);
        backIv = (ImageView)findViewById(R.id.back);
        listadonsListView = (ListView)findViewById(R.id.listadons);
        titleTv.setText(getResources().getString(R.string.addons));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdonsActivity.this,CartActivity.class);
                startActivity(i);
                finish();
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        if (Network.isNetworkCheck(getApplicationContext())) {
            //getdata();
            getAdons();
        }
        else {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
        }

        listadonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TdayChild tdayChild = (TdayChild) adapterView.getItemAtPosition(i);
                String dishId=tdayChild.getId();
                String dishnamee=tdayChild.getDishName();
                String dishcodee=tdayChild.getDishCode();
                String dishpricee=tdayChild.getDishPrice();
                String dishImg=tdayChild.getDishImage();
                String dishDiscription=tdayChild.getDishDescription();
                String dishmenuId=tdayChild.getMenu();


                Intent iAl=new Intent(getApplicationContext(),AdonsDiscriptionActivity.class);
                iAl.putExtra("dishId",dishId);
                iAl.putExtra("dishImg",dishImg);
                iAl.putExtra("dishCode",dishcodee);
                iAl.putExtra("dishName",dishnamee);
                iAl.putExtra("dishDiscription",dishDiscription);
                iAl.putExtra("dishPrice",dishpricee);
                iAl.putExtra("menuId",dishmenuId);

                startActivity(iAl);
                finish();

            }
        });

    }

    private void getAdons() {

        String adons_url=Utility.Base_Url+"&action=get_addons_by_sift";

        //http://139.59.43.252/cnsTiffinService/api/ws/controller/?access=true&action=get_addons_by_sift

        adonsList = new ArrayList<TdayChild>();
        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, adons_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();

                        //Toast.makeText(MenuActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("alcarteresponse", response.toString());

                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Response is Null", Toast.LENGTH_LONG).show();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("status");
                                if (status == true) {
                                    JSONArray array = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject obj = array.getJSONObject(i);

                                        String idchild = obj.getString("id");
                                        String userIdchild = obj.getString("userId");
                                        String categoryIdchild = obj.getString("categoryId");
                                        String subcategoryIdchild = obj.getString("subcategoryId");
                                        String regionIdchild = obj.getString("regionId");
                                        String discountIdchild = obj.getString("discountId");
                                        String dishNamechild = obj.getString("dishName");
                                        String dishPricechild = obj.getString("dishPrice");
                                        String dishImagechild = obj.getString("dishImage");
                                        String quantitychild = obj.getString("quantity");
                                        String ratingchild = obj.getString("rating");
                                        String dishDescriptionchild = obj.getString("dishDescription");
                                        String dishCodechild = obj.getString("dishCode");
                                        String statuschild = obj.getString("status");
                                        String createdOn=obj.getString("createdOn");
                                        String modifiedOn=obj.getString("modifiedOn");
                                        String childMenu=obj.getString("menu");

                                        TdayChild child = new TdayChild();
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
                                        child.setCreatedOn(createdOn);
                                        child.setModifiedOn(modifiedOn);
                                        child.setMenu(childMenu);


                                        adonsList.add(child);
                                    }

                                } else if (status==false) {
                                    Toast.makeText(getApplicationContext(),"Data Empty",Toast.LENGTH_LONG).show();
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            breakfastadapter = new Breakfastadapter(AdonsActivity.this,adonsList);
                            listadonsListView.setAdapter(breakfastadapter);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        //Toast.makeText(MenuActivity.this,response,Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
                        Log.d("error", error.toString());
                    }
                }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null) {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(),
                            response.notModified);
                }

                return super.parseNetworkResponse(response);
            };

            /*@Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("RestaurantID", resId);
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(AdonsActivity.this,CartActivity.class);
        startActivity(i);
        finish();

    }
}
