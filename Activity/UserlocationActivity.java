package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Adapter.LocationAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.UserLocation;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserlocationActivity extends AppCompatActivity {

    ListView userlocListView;
    List<UserLocation> userlocationList;
    Button allowBtn;
    double lat,lon;
    LocationAdapter locationAdapter;
    String addressName,regionName,cityname,stateName;
    JSONArray jsonArray;
    String[] cityArr;
    String[] regionNameArr;
    String[] cityNameArr;
    String[] stateNameArr;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlocation);
        userlocListView = (ListView)findViewById(R.id.userloclistview);
        allowBtn = (Button)findViewById(R.id.allowBtn);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        getmanualLocation();



    }

    private void getmanualLocation() {

        String userlocation_url= Utility.Base_Url+"&action=get_user_location&lat=&long=";
        userlocationList=new ArrayList<UserLocation>();
        showProgressDialog();

        Log.d("userlocation_url",userlocation_url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, userlocation_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("userlocationresp",response);
                        JSONObject jobj= null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            if (status==true) {

                                jsonArray=jobj.getJSONArray("data");
                                cityArr=new String[jsonArray.length()];
                                regionNameArr=new String[jsonArray.length()];
                                cityNameArr=new String[jsonArray.length()];
                                stateNameArr=new String[jsonArray.length()];

                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String id=jsonObject.getString("id");
                                    String stateName=jsonObject.getString("stateName");
                                    String cityName=jsonObject.getString("cityName");
                                    String regionName=jsonObject.getString("regionName");
                                    String address=regionName+","+cityName+","+stateName;
                                    cityArr[i]=address;
                                    regionNameArr[i]=regionName;
                                    cityNameArr[i]=cityName;
                                    stateNameArr[i]=stateName;
                                    UserLocation userLocation=new UserLocation();
                                    userLocation.setId(id);
                                    userLocation.setCityName(cityName);
                                    userLocation.setRegionName(regionName);
                                    userLocation.setStateName(stateName);
                                    userlocationList.add(userLocation);

                                }
                                locationAdapter = new LocationAdapter(UserlocationActivity.this,userlocationList);
                                userlocListView.setAdapter(locationAdapter);

                            }
                            else if (status==false) {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.usernamepwdinc),Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                            }

                            userlocListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    addressName=cityArr[i];
                                    regionName=regionNameArr[i];
                                    cityname=cityNameArr[i];
                                    stateName=stateNameArr[i];
                                    Intent intent=new Intent(UserlocationActivity.this, HomeActivity.class);
                                    intent.putExtra("address",addressName);
                                    intent.putExtra("regionName",regionName);
                                    intent.putExtra("cityname",cityname);
                                    intent.putExtra("stateName",stateName);
                                    startActivity(intent);

                                }
                            });

                            allowBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getmanualLocation();

                                }
                            });
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

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
        finish();
    }
}
