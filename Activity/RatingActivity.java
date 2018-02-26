package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.R;

/**
 * Created by rajat.gupta on 2/20/2017.
 */

public class RatingActivity extends AppCompatActivity {
ListView listView;
    Button button;
    RatingBar ratingBar;
    String[] catonetitleArr;
    String[] catonepriceArr;
    String[] catonedisArr;
    String[] cattwotitleArr;
    String[] cattwopriceArr;
    String[] cattwodisArr;
    String[] catthreetitleArr;
    String[] catthreepriceArr;
    String[] catthreedisArr;

    ProgressDialog pDialog;
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

    private int[] images = {R.drawable.burger, R.drawable.chicken, R.drawable.rice,
            R.drawable.resetetbg, R.drawable.cart, R.drawable.food, R.drawable.thali};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingscreen);
         listView =(ListView)findViewById(R.id.catoneList);
        ratingBar =(RatingBar)findViewById(R.id.ratingBar);
        button =(Button)findViewById(R.id.rating_btn);
        View view =(View)findViewById(R.id.viewbelow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                String totalStars = "Total Stars:: "+ratingBar.getNumStars();
                String rating = "Rating :: "+ratingBar.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + rating, Toast.LENGTH_LONG).show();
            }
        });

        catonetitleArr = getResources().getStringArray(R.array.foodtitle);
        catonepriceArr = getResources().getStringArray(R.array.foodprice);
        catonedisArr = getResources().getStringArray(R.array.fooddiscription);
        cattwotitleArr = getResources().getStringArray(R.array.foodtitletwo);
        cattwopriceArr = getResources().getStringArray(R.array.foodpricetwo);
        cattwodisArr = getResources().getStringArray(R.array.fooddiscription);
        catthreetitleArr = getResources().getStringArray(R.array.foodtitlethree);
        catthreepriceArr = getResources().getStringArray(R.array.foodpricethree);
        catthreedisArr = getResources().getStringArray(R.array.fooddiscription);

    }


    }
