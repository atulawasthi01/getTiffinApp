package com.yumtiff.mohitkumar.tiffin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Adapter.PreorderAdapter;
import com.yumtiff.mohitkumar.tiffin.Adapter.TiffinDishAdapter;
import com.yumtiff.mohitkumar.tiffin.Fragment.TodaysMenu;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiffinDiscriptionActivity extends AppCompatActivity {
    ImageView backIv, tiffinIv;
    TextView titleTv, priceTv, dishnameTv;
    ListView tiffinlistview;
    RelativeLayout tiffinlistviewLayout;
    ExpandableListView tiffinexplistview;
    String tiffinimgStr, tiffinnameStr;
    public static String tiffinpriceStr;
    TiffinDishAdapter tiffinDishAdapter;
    List<TdayChild> tiffinList;


    ArrayList<TdayChild> tdaychildList;
    Button proceedpayBtn;
    SharedPreferences Prefs;
    public static JSONObject tiffinObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiffin_discription);
        titleTv = (TextView) findViewById(R.id.title);
        priceTv = (TextView) findViewById(R.id.price);
        dishnameTv = (TextView) findViewById(R.id.dishname);
        tiffinIv = (ImageView) findViewById(R.id.tiffinImg);
        backIv = (ImageView) findViewById(R.id.back);
        tiffinlistview = (ListView) findViewById(R.id.tiffinlistview);
        tiffinlistviewLayout = (RelativeLayout) findViewById(R.id.tiffinlistviewLayout);
        proceedpayBtn = (Button) findViewById(R.id.proceedpayBtn);

        /*try {

            Log.d("PassedJson", tiffinObject.getString("category").toString());


        } catch (JSONException e) {
            Log.d("exception of chhild", e.toString());
        }*/


        //tiffinexplistview = (ExpandableListView)findViewById(R.id.explist);
        titleTv.setText(getResources().getString(R.string.tiffindis));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TiffinDiscriptionActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        tiffinimgStr=getIntent().getStringExtra("dishImage");
        tiffinnameStr=getIntent().getStringExtra("dishName");
        tiffinpriceStr=getIntent().getStringExtra("dishPrice");
        //tdaychildList=(ArrayList<TdayChild>)getIntent().getSerializableExtra("tiffinlist");
        /*Log.d("tiffinimgStr",tiffinimgStr);
        Log.d("tiffinpriceStr",tiffinpriceStr);
        Log.d("tdaychildList",tdaychildList.toString());*/

        tiffinList = new ArrayList<TdayChild>();

        /* Gson gson = new Gson();
        String jsonText = Prefs.getString("list", null);
        TdayChild tdayChild = gson.fromJson(jsonText, TdayChild.class);*/

         GetTiffinChild();
        tiffinDishAdapter=new TiffinDishAdapter(this, tdaychildList);
        tiffinlistview.setAdapter(tiffinDishAdapter);
        tiffinDishAdapter.notifyDataSetChanged();

        tiffinlistviewLayout.setVisibility(View.INVISIBLE);

        tiffinIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

                if (tiffinlistviewLayout.getVisibility() == View.INVISIBLE) {

                    tiffinlistviewLayout.startAnimation(slideUp);
                    tiffinlistviewLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        dishnameTv.setText(tiffinnameStr);
        priceTv.setText(tiffinpriceStr + "/month");

        Picasso.with(this)
                .load(tiffinimgStr)
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(tiffinIv);

       /* tiffinDishAdapter=new TiffinDishAdapter(this,tdaychildList);
        tiffinlistview.setAdapter(tiffinDishAdapter);*/

        proceedpayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TiffinDiscriptionActivity.this, LoginActivity.class);
                i.putExtra("flag","3");
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(TiffinDiscriptionActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }


    public void GetTiffinChild() {
        tdaychildList=new ArrayList<TdayChild>();
        try {
            JSONArray childArray = tiffinObject.getJSONArray("dishDetails");
            for (int j = 0; j < childArray.length(); j++) {

                JSONObject childObj = childArray.getJSONObject(j);

                if (!(childArray.length() == 0)) {

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
                    TdayChild child;

                    child = new TdayChild();
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

                    tdaychildList.add(child);


                } else {
                    Toast.makeText(getApplicationContext(), "No Data in Child Items", Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {

        }

    }
}
