package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.squareup.picasso.Picasso;
import com.yumtiff.mohitkumar.tiffin.Adapter.TdayAdapter;
import com.yumtiff.mohitkumar.tiffin.Fragment.Alcarter;
import com.yumtiff.mohitkumar.tiffin.Fragment.TodaysMenu;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.Model.TdayGroup;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.app.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.yumtiff.mohitkumar.tiffin.Adapter.PreOrderListAdapter.discountId;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Activity context;
    Dialog dialog;
    int progress = 0;
    String timeStr;
    Toolbar toolbar;
    ImageView backIv;
    TextView titleTv, quntityTxt;
    public static SwipeMenuListView listView;
    public static CartAdapter listAdapter;
    public static LunchAdapter lunchAdapter;
    public static DinnerAdapter dinnerAdapter;
    public static MinimealAdapter miniAdapter;
    Button paymentproceedbtn, addBtn, subBtn;
    //Button preorderBtn;
    ImageView addmoreimg;
    LayoutInflater inflater;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId, dishIdd, menuIdd;
    ProgressDialog pDialog;
    public static String id;
    private LinearLayout llButtons;
    SwipeMenuListView lunchListview, dinnerListview, miniListview, alcarteListview;
    public static TextView lunchtxtTv, dinnertxtTv, minimealstxtTv, alkartetxtTv;
    public TextView lunchTv, dinnerTv, minimealTv, alkarteTv;
    RelativeLayout alkarteLayout, lunchLayout, dinnerLayout, minimealLayout;
    Button lunchdeliverytimeBtn, dinnerdeliverytimeBtn, minideliverytimeBtn, alkartedeliverytimeBtn, alkarteaddonsBtn;
    boolean isLunchtime = false;
    boolean isDinnertime = false;
    boolean isMinimealstime = false;
    boolean isAlkartetime = false;
    String startTime, endTime, category;
    String lunchStr, dinnerStr, minimealStr, alkarteStr;
    String[] starttimeArr, endtimeArr;
    int i;
    String finalTime="";
    public static String selectedtimeStr;
    public static int TotalPrice = 0;
    Global globalObj;
    public static String quanityArrStr,menuidArrStr,categoryArrStr,dishIdArrStr;


    // NEW CODE
    ArrayList<ArrayList<Integer>> QuantityArray = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<String>> TotalpriceArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> menuIdArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> categoryArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> dishidArray = new ArrayList<ArrayList<String>>();
    ArrayList<String> selecttimeArray = new ArrayList<String>();
    List<List<PreOrderData>> FilterdArraylist = new ArrayList<List<PreOrderData>>();
    List<String> selectedtimeList = new ArrayList<String>();
    //String[] selectedtimeArray=new String[4];

    Date dateStart = null;
    String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        listView = (SwipeMenuListView) findViewById(R.id.cartList);
        globalObj=new Global();
        InitializingArray();



        lunchListview = (SwipeMenuListView) findViewById(R.id.lunchListview);
        dinnerListview = (SwipeMenuListView) findViewById(R.id.dinnerListview);
        miniListview = (SwipeMenuListView) findViewById(R.id.miniListview);
        alcarteListview = (SwipeMenuListView) findViewById(R.id.alcarteListview);
        lunchtxtTv = (TextView) findViewById(R.id.lunchtxt);
        dinnertxtTv = (TextView) findViewById(R.id.dinnertxt);
        minimealstxtTv = (TextView) findViewById(R.id.minimealstxt);
        alkartetxtTv = (TextView) findViewById(R.id.alkartetxt);
        backIv = (ImageView) findViewById(R.id.back);
        titleTv = (TextView) findViewById(R.id.title);
        paymentproceedbtn = (Button) findViewById(R.id.paymentproceedbtn);
        alkarteLayout = (RelativeLayout) findViewById(R.id.alkarteLayout);
        lunchLayout = (RelativeLayout) findViewById(R.id.lunchLayout);
        dinnerLayout = (RelativeLayout) findViewById(R.id.dinnerLayout);
        minimealLayout = (RelativeLayout) findViewById(R.id.minimealLayout);
        lunchTv = (TextView) findViewById(R.id.lunchhtxt);
        dinnerTv = (TextView) findViewById(R.id.dinnerrtxt);
        minimealTv = (TextView) findViewById(R.id.minimealstxt);
        alkarteTv = (TextView) findViewById(R.id.alkarteetxt);
        lunchStr = lunchTv.getText().toString();
        dinnerStr = dinnerTv.getText().toString();
        minimealStr = minimealTv.getText().toString();
        alkarteStr = alkarteTv.getText().toString();
        backIv.setVisibility(View.GONE);

        lunchdeliverytimeBtn = (Button) findViewById(R.id.lunchdeliverytimeBtn);
        dinnerdeliverytimeBtn = (Button) findViewById(R.id.dinnerdeliverytimeBtn);
        minideliverytimeBtn = (Button) findViewById(R.id.minideliverytimeBtn);
        alkartedeliverytimeBtn = (Button) findViewById(R.id.deliverytimeBtn);
        alkarteaddonsBtn = (Button) findViewById(R.id.addonsBtn);


        Log.d("tdaygrpSize", "" + TodaysMenu.tdaygrpList.size());
        starttimeArr = new String[TodaysMenu.tdaygrpList.size()];
        endtimeArr = new String[TodaysMenu.tdaygrpList.size()];
        for (i = 0; i < TodaysMenu.tdaygrpList.size(); i++) {
            TdayGroup tdayGroup = TodaysMenu.tdaygrpList.get(i);
            startTime = tdayGroup.getStartTime();
            endTime = tdayGroup.getEndTime();
            starttimeArr[i] = startTime;
            endtimeArr[i] = endTime;

            category = tdayGroup.getCategory();
            Log.d("Time", "StartTime: " + startTime + " EndTime: " + endTime);
            Log.d("category", tdayGroup.getCategory());
        }

        lunchdeliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLunchtime = true;

                Log.d("lunchstartTime", starttimeArr[0]);
                Log.d("lunchendTime", endtimeArr[0]);

                selectTime(lunchdeliverytimeBtn, starttimeArr[0], endtimeArr[0]);


            }
        });

        dinnerdeliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDinnertime = true;
                //if (category.equals("Dinner")) {
                // Log.d("lunchstartTime",startTime);
                //Log.d("lunchendTime",endTime);
                Log.d("DinnerstartTime", starttimeArr[1]);
                Log.d("DinnerendTime", endtimeArr[1]);
                selectTime(dinnerdeliverytimeBtn, starttimeArr[1], endtimeArr[1]);


                //}
            }
        });
        minideliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMinimealstime = true;
                Log.d("MinistartTime", starttimeArr[2]);
                Log.d("MiniendTime", endtimeArr[2]);
                selectTime(minideliverytimeBtn, starttimeArr[2], endtimeArr[2]);

            }
        });
        alkartedeliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlkartetime = true;

                selectTime(alkartedeliverytimeBtn, Alcarter.alcartestartTime, Alcarter.alcarteendTime);

            }
        });

        alkarteaddonsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, AdonsActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");

        titleTv.setText(getResources().getString(R.string.viewcart));
       /* backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Intent i = new Intent(Maincart.this, HomeActivity.class);
                startActivity(i);
                finish();*//*
                finish();

            }
        });*/


        lunchAdapter = new LunchAdapter(this, Global.lunchList);
        lunchListview.setAdapter(lunchAdapter);
        dinnerAdapter = new DinnerAdapter(this, Global.dinnerList);
        dinnerListview.setAdapter(dinnerAdapter);
        miniAdapter = new MinimealAdapter(this, Global.minimealList);
        miniListview.setAdapter(miniAdapter);
        listAdapter = new CartAdapter(this, Global.alcarteList);
        alcarteListview.setAdapter(listAdapter);

        setDynamicHeight(lunchListview);
        setDynamicHeight(dinnerListview);
        setDynamicHeight(miniListview);
        setDynamicHeight(alcarteListview);


        if (Global.lunchList.size() == 0) {
            lunchtxtTv.setVisibility(View.GONE);
            lunchLayout.setVisibility(View.GONE);

        } else {
            lunchtxtTv.setVisibility(View.VISIBLE);
            lunchLayout.setVisibility(View.VISIBLE);

        }
        if (Global.dinnerList.size() == 0) {
            dinnertxtTv.setVisibility(View.GONE);
            dinnerLayout.setVisibility(View.GONE);
        } else {
            dinnertxtTv.setVisibility(View.VISIBLE);
            dinnerLayout.setVisibility(View.VISIBLE);

        }
        if (Global.minimealList.size() == 0) {
            minimealstxtTv.setVisibility(View.GONE);
            minimealLayout.setVisibility(View.GONE);

        } else {
            minimealstxtTv.setVisibility(View.VISIBLE);
            minimealLayout.setVisibility(View.VISIBLE);

        }
        if (Global.alcarteList.size() == 0) {
            alkartetxtTv.setVisibility(View.GONE);
            alkarteLayout.setVisibility(View.GONE);
        } else {
            alkartetxtTv.setVisibility(View.VISIBLE);
            alkarteLayout.setVisibility(View.VISIBLE);

        }

        paymentproceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (!isLunchtime==true && !isDinnertime==true && !isMinimealstime==true && !isAlkartetime==true) {
                    Toast.makeText(getApplicationContext(),"Please Enter Preorder Delivery Time",Toast.LENGTH_SHORT).show();
                }
                else if (!isLunchtime==true) {
                    Toast.makeText(getApplicationContext(),"Please Enter Lunch Time",Toast.LENGTH_SHORT).show();
                }
                else if (!isDinnertime==true) {
                    Toast.makeText(getApplicationContext(),"Please Enter Dinner Time",Toast.LENGTH_SHORT).show();
                }
                else if (!isMinimealstime==true) {
                    Toast.makeText(getApplicationContext(),"Please Enter Minimeal Time",Toast.LENGTH_SHORT).show();
                }
                else if (!isAlkartetime==true) {
                    Toast.makeText(getApplicationContext(),"Please Enter Alcarte Time",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i=new Intent(CartActivity.this,LoginActivity.class);
                    startActivity(i);
                    Log.d("CommaSep",Global.CreateStringArray(TotalpriceArray));
                    Log.d("quantityArr",Global.CreateQuantityArray(QuantityArray));
                    Log.d("menuArr",Global.CreateMenuArray(menuIdArray));
                    Log.d("categoryArr",Global.CreateMenuArray(categoryArray));
                }*/
                int decidingIntent=0;
                for (int i=0;i<selecttimeArray.size();i++) {
                    if(selecttimeArray.get(i).equals("Select Delivery Time")) {
                        Toast.makeText(getApplicationContext(),"Please Select Delivery Time",Toast.LENGTH_SHORT).show();
                        decidingIntent=1;
                        break;
                    }
                    else {
                        decidingIntent = 0;
                    }
                }
                if (decidingIntent!=1) {
                    quanityArrStr=Global.CreateQuantityArray(QuantityArray);
                    menuidArrStr=Global.CreateMenuArray(menuIdArray);
                    categoryArrStr=Global.CreateMenuArray(categoryArray);
                    dishIdArrStr=Global.CreateMenuArray(dishidArray);


                    Log.d("CommaSep",Global.CreateStringArray(TotalpriceArray));
                    Log.d("quantityArr",Global.CreateQuantityArray(QuantityArray));
                    Log.d("menuArr",Global.CreateMenuArray(menuIdArray));
                    Log.d("categoryArr",Global.CreateMenuArray(categoryArray));
                    Log.d("dishIdArr",Global.CreateMenuArray(dishidArray));
                    Intent i=new Intent(CartActivity.this,LoginActivity.class);
                    i.putExtra("flag","1");
                    startActivity(i);
                }




            }
        });


    }

    private void selectTime(final Button addonsBtn, String StartTime, String EndTime) {

        dialog = new Dialog(this);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lunch_timepicker_dialog);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.7f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
        dialog.getWindow().setAttributes(lp);
        final SeekBar timeSeek = (SeekBar) dialog.findViewById(R.id.timesel);
        final Button doneBtn = (Button) dialog.findViewById(R.id.doneBtn);
        final TextView starttimeTv = (TextView) dialog.findViewById(R.id.starttime);
        final TextView selecttimeTv = (TextView) dialog.findViewById(R.id.selecttime);
        final TextView selectmTv = (TextView)dialog.findViewById(R.id.selectedTm);
        Date startdate = convertStringtoDate(StartTime);
        Log.d("startdate", "" + startdate);
        starttimeTv.setText(StartTime);
        selectmTv.setText(StartTime);

        final int interval = 5;
        String dtStart = StartTime;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            dateStart = format.parse(dtStart);
            Date dateEnd = format.parse(EndTime);
            Log.d("okil", hoursDifference(dateEnd, dateStart) + "");

            timeSeek.setMax(hoursDifference(dateEnd, dateStart) * 4);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        timeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("rtyuiu", i + "");

                int MinutesInMillies = i * 15;

                Log.d("dated", addMinutesToDate(MinutesInMillies, dateStart) + "");
                selectedTime = "" + addMinutesToDate(MinutesInMillies, dateStart);
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                selectedTime = format.format(addMinutesToDate(MinutesInMillies, dateStart));
                selecttimeTv.setText(selectedTime);
                Log.d("selectedTime",selectedTime);
                selectmTv.setText(selectedTime);
               /* for (int ii=0;ii<4;ii++) {
                    selectedtimeArray[i]=selectedTime;
                }
                Log.d("selectedtimeArray",Arrays.toString(selectedtimeArray));*/
                finalTime=selectedTime;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //Toast.makeText(context,hours+" , "+minutes,Toast.LENGTH_SHORT).show();



            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addonsBtn.setText(selectedTime);
                selectedtimeList.add(selectedTime);
                Log.d("selectedtimeList",selectedtimeList.toString());
                selecttimeArray.set(selecttimeArray.indexOf("Select Delivery Time"),selectedTime);
                //String idList=selectedtimeList.toString();
               /* selectedtimeStr = TextUtils.join(",", selectedtimeList);
                Log.d("selectedtimeStr",selectedtimeStr);*/

                String separator = ", ";
                int total = selectedtimeList.size() * separator.length();
                for (String s : selectedtimeList) {
                    total += s.length();
                }

                StringBuilder sb = new StringBuilder(total);
                for (String s : selectedtimeList) {
                    sb.append(separator).append(s);
                }

                selectedtimeStr = sb.substring(separator.length());
                Log.d("selectedtimeStr",selectedtimeStr);
                dialog.dismiss();
            }
        });
    }

    private static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public Date convertStringtoDate(String startTime) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:a");
        try {
            date = format.parse(startTime);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Global.newAL.clear();
        TdayAdapter.ViewHolder.checkselectChk.setChecked(false);
    }*/

    /* public static void removeDynamicHeight(ListView listView,int cartlistIndex) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = cartlistIndex; i > adapter.getCount(); i--) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height -= listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height - (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }*/


    public class CartAdapter extends ArrayAdapter<PreOrderData> {
        Activity context;
        Dialog dialog;
        public int pos;
        SharedPreferences loginSp;
        public static final String loginPrefrence = "loginSp";
        String loginId;
        String dishId, catId;
        ProgressDialog pDialog;
        String dishPrice, quantity = "1";
        public String totalAmountStr;
        int totalamount, dishpriceInt, quantityInt;
        int ttlAmountSum = 0, i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int totalAmounttt;

        public CartAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL == null) {
                return 0;
            } else {
                Log.d("newAlcount", "" + newAL.size());
                return newAL.size();

            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos = position;
            Log.d("listsizeee", "" + newAL.size());
          /*  if (position == 0) {
                if (convertView == null) {

                    LayoutInflater headerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = headerInflater.inflate(R.layout.cart_header_layout, null);
                    preOrderData = newAL.get(position);
                    listAdapter.insert(preOrderData, 0);
                }
            } else {*/
            if (convertView == null) {

                LayoutInflater mainInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mainInflater.inflate(R.layout.cart_row_layout, null);
                final EditText quantityet = (EditText) convertView.findViewById(R.id.quntityTxt);
                quantityet.setShowSoftInputOnFocus(false);
                quantityet.setText("" + 1);
                Log.d("quantityet", quantityet.getText().toString());
                TextView titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
                TextView discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
                TextView pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
                ImageView foodImg = (ImageView) convertView.findViewById(R.id.catoneimg);
                //TextView quantityTv = (TextView) convertView.findViewById(R.id.quantitytxt);
                final TextView totalpriceTv = (TextView) convertView.findViewById(R.id.totalprice);
                Button plus = (Button) convertView.findViewById(R.id.addBtn);
                Button minus = (Button) convertView.findViewById(R.id.subBtn);
                ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.deleteBtn);

                preOrderData = newAL.get(position);

                dishId = preOrderData.getDishId();
                catId = preOrderData.getCatId();
                discountId = preOrderData.getDiscountId();
                dishPrice = preOrderData.getDishPrice();
                quantity = preOrderData.getQuanitity();

                Log.d("quantity", quantity);

                quantityInt = Integer.parseInt(quantity);
                dishpriceInt = Integer.parseInt(dishPrice);
                totalamount = dishpriceInt * quantityInt;
                totalAmountStr = String.valueOf(totalamount);


                Log.d("cartItemdname", preOrderData.getDishName());

                titlefoodTv.setText(preOrderData.getDishName());
                discripfoodTv.setText(preOrderData.getDishdiscription());
                pricefoodTv.setText("₹" + preOrderData.getDishPrice());
                totalpriceTv.setText("₹" + totalAmountStr);


                Picasso.with(context)
                        .load(preOrderData.getDishImage())
                        .placeholder(R.drawable.thali) // optional
                        .error(R.drawable.thali)         // optional
                        .into(foodImg);

                //quantityet.addTextChangedListener(new MyTextWatcher(convertView));


                plus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                      /*  if(counter<10){
                            counter++;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("",""+counter);
                            notifyDataSetChanged();
                            Log.d("abc",""+abc);
                        }*/
                            /*if (item_counter[position] < 10) {
                                item_counter[position]++;
                                Log.d("item_counter", "" + item_counter[position]);
                                String abc = "" + item_counter[position];
                                Log.d("abc", "" + abc);
                                quantityet.setText(abc);
                                Log.d("quantityyet", "" + quantityet.getText().toString());
                                preOrderData = newAL.get(position);
                                dishPrice = preOrderData.getDishPrice();
                                int totalAmount = Integer.parseInt(dishPrice);
                                totalAmounttt = totalAmount * item_counter[position];
                                totalpriceTv.setText("₹" + totalAmounttt);
                                Log.d("totalAmounttt", "" + totalAmounttt);
                                notifyDataSetChanged();
                            }*/

                        preOrderData = newAL.get(position);
                        dishPrice = preOrderData.getDishPrice();
                        int totalAmount = Integer.parseInt(dishPrice);

                        QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.alcarteList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.alcarteList)));


                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    /*    if(counter>1){
                            counter--;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("abc",""+abc);
                        }*/
                           /* if (item_counter[position] > 1) {
                                item_counter[position]--;
                                Log.d("item_counterminus", "" + item_counter[position]);
                                String abc = "" + item_counter[position];
                                Log.d("abc", "" + abc);
                                quantityet.setText(abc);
                                preOrderData = newAL.get(position);
                                dishPrice = preOrderData.getDishPrice();
                                int totalAmount = Integer.parseInt(dishPrice);
                                totalAmounttt = totalAmount * item_counter[position];
                                totalpriceTv.setText("₹" + totalAmounttt);
                                Log.d("totalAmounttt", "" + totalAmounttt);
                                notifyDataSetChanged();
                            }*/
                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.alcarteList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!Global.alcarteList.isEmpty()) {


                            PreOrderData preOrderData = Global.alcarteList.get(position);
                            newAL.remove(preOrderData);
                            Log.d("deletedetaillistclick", Global.alcarteList.toString());
                            listAdapter = new CartAdapter(CartActivity.this, Global.alcarteList);
                            alcarteListview.setAdapter(listAdapter);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.alcarteList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.alcarteList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.alcarteList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.alcarteList)).remove(position);
                            dishidArray.get(FilterdArraylist.indexOf(Global.alcarteList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();;
                            HomeActivity.countttTv.setText(""+listSize);


                            if (Global.alcarteList.size() == 0) {
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.alcarteList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.alcarteList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.alcarteList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.alcarteList));
                                dishidArray.remove(FilterdArraylist.indexOf(Global.alcarteList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.alcarteList));
                                alkarteLayout.setVisibility(View.GONE);
                                alkartetxtTv.setVisibility(View.GONE);
                            }

                        } else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll", "" + newAL.size());
                        }

                    }
                });
            }
            //   }

            return convertView;

        }

    }

    public class LunchAdapter extends ArrayAdapter<PreOrderData> {
        Activity context;
        Dialog dialog;
        public int pos;
        SharedPreferences loginSp;
        public static final String loginPrefrence = "loginSp";
        String loginId;
        String dishId, catId;
        ProgressDialog pDialog;
        String dishPrice, quantity = "1";
        public String totalAmountStr;
        int totalamount, dishpriceInt, quantityInt;
        int ttlAmountSum = 0, i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int totalAmounttt;

        public LunchAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL == null) {

                return 0;
            } else {
                return newAL.size();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos = position;
          /*  if (position==0) {
                if (convertView==null) {
                    LayoutInflater headerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = headerInflater.inflate(R.layout.cart_header_layout, null);
                }
            }
            else {*/
            if (convertView == null) {

                LayoutInflater mainInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mainInflater.inflate(R.layout.cart_row_layout, null);
                final EditText quantityet = (EditText) convertView.findViewById(R.id.quntityTxt);
                quantityet.setShowSoftInputOnFocus(false);
                quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position));
                //quantityet.setText("" + 1);
                Log.d("quantityet", quantityet.getText().toString());
                TextView titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
                TextView discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
                TextView pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
                ImageView foodImg = (ImageView) convertView.findViewById(R.id.catoneimg);
                final TextView totalpriceTv = (TextView) convertView.findViewById(R.id.totalprice);
                Button plus = (Button) convertView.findViewById(R.id.addBtn);
                Button minus = (Button) convertView.findViewById(R.id.subBtn);
                ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.deleteBtn);


                preOrderData = newAL.get(position);

                dishId = preOrderData.getDishId();
                catId = preOrderData.getCatId();
                discountId = preOrderData.getDiscountId();
                dishPrice = preOrderData.getDishPrice();
                quantity = preOrderData.getQuanitity();

                Log.d("quantity", quantity);

                quantityInt = Integer.parseInt(quantity);
                dishpriceInt = Integer.parseInt(dishPrice);
                totalamount = dishpriceInt * quantityInt;
                totalAmountStr = String.valueOf(totalamount);


                Log.d("cartItemdname", preOrderData.getDishName());

                titlefoodTv.setText(preOrderData.getDishName());
                discripfoodTv.setText(preOrderData.getDishdiscription());
                pricefoodTv.setText("₹" + preOrderData.getDishPrice());
                totalpriceTv.setText("₹" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position));


                Picasso.with(context)
                        .load(preOrderData.getDishImage())
                        .placeholder(R.drawable.thali) // optional
                        .error(R.drawable.thali)         // optional
                        .into(foodImg);

                //quantityet.addTextChangedListener(new MyTextWatcher(convertView));


                plus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                      /*  if(counter<10){
                            counter++;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("",""+counter);
                            notifyDataSetChanged();
                            Log.d("abc",""+abc);
                        }*/
//                        if (item_counter[position] < 10) {
//                            item_counter[position]++;
//                            Log.d("item_counter", "" + item_counter[position]);
//                            String abc = "" + item_counter[position];
//                            Log.d("abc", "" + abc);
//
//                            Log.d("quantityyet", "" + quantityet.getText().toString());
//                            preOrderData = newAL.get(position);
//
//                            int totalAmount = Integer.parseInt(dishPrice);
//                            totalAmounttt = totalAmount * item_counter[position];
//                            totalpriceTv.setText("₹" + totalAmounttt);
//                            Log.d("totalAmounttt", "" + totalAmounttt);
//
//
//                        }
                        preOrderData = newAL.get(position);
                        dishPrice = preOrderData.getDishPrice();
                        int totalAmount = Integer.parseInt(dishPrice);

                        QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)));


                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    /*    if(counter>1){
                            counter--;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("abc",""+abc);
                        }*/
//                        if (item_counter[position] > 1) {
//                            item_counter[position]--;
//                            Log.d("item_counterminus", "" + item_counter[position]);
//                            String abc = "" + item_counter[position];
//                            Log.d("abc", "" + abc);
//                            quantityet.setText(abc);
//                            preOrderData = newAL.get(position);
//                            dishPrice = preOrderData.getDishPrice();
//                            int totalAmount = Integer.parseInt(dishPrice);
//                            totalAmounttt = totalAmount * item_counter[position];
//                            totalpriceTv.setText("₹" + totalAmounttt);
//                            Log.d("totalAmounttt", "" + totalAmounttt);
//
//                        }
                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!Global.lunchList.isEmpty()) {

                            PreOrderData preOrderData = newAL.get(position);
                            Global.lunchList.remove(preOrderData);
                            Log.d("deletedetaillistclick", newAL.toString());
                            lunchAdapter = new LunchAdapter(CartActivity.this, Global.lunchList);
                            lunchListview.setAdapter(lunchAdapter);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.lunchList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.lunchList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.lunchList)).remove(position);
                            dishidArray.get(FilterdArraylist.indexOf(Global.lunchList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();;
                            HomeActivity.countttTv.setText(""+listSize);


                            if (Global.lunchList.size() == 0) {
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.lunchList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.lunchList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.lunchList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.lunchList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.lunchList));
                                lunchLayout.setVisibility(View.GONE);
                                lunchtxtTv.setVisibility(View.GONE);
                            }

                        } else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll", "" + Global.lunchList.size());
                        }

                    }
                });
            }

            return convertView;

        }
    }

    public class DinnerAdapter extends ArrayAdapter<PreOrderData> {
        Activity context;
        Dialog dialog;
        public int pos;
        SharedPreferences loginSp;
        public static final String loginPrefrence = "loginSp";
        String loginId;
        String dishId, catId;
        ProgressDialog pDialog;
        String dishPrice, quantity = "1";
        public String totalAmountStr;
        int totalamount, dishpriceInt, quantityInt;
        int ttlAmountSum = 0, i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int totalAmounttt;

        public DinnerAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL == null) {

                return 0;
            } else {
                return newAL.size();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos = position;
          /*  if (position==0) {
                if (convertView==null) {
                    LayoutInflater headerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = headerInflater.inflate(R.layout.cart_header_layout, null);
                }
            }
            else {*/
            if (convertView == null) {

                LayoutInflater mainInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mainInflater.inflate(R.layout.cart_row_layout, null);
                final EditText quantityet = (EditText) convertView.findViewById(R.id.quntityTxt);
                quantityet.setShowSoftInputOnFocus(false);
                quantityet.setText("" + 1);
                Log.d("quantityet", quantityet.getText().toString());
                TextView titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
                TextView discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
                TextView pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
                ImageView foodImg = (ImageView) convertView.findViewById(R.id.catoneimg);
                final TextView totalpriceTv = (TextView) convertView.findViewById(R.id.totalprice);
                Button plus = (Button) convertView.findViewById(R.id.addBtn);
                Button minus = (Button) convertView.findViewById(R.id.subBtn);
                ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.deleteBtn);

                preOrderData = newAL.get(position);

                dishId = preOrderData.getDishId();
                catId = preOrderData.getCatId();
                discountId = preOrderData.getDiscountId();
                dishPrice = preOrderData.getDishPrice();
                quantity = preOrderData.getQuanitity();

                Log.d("quantity", quantity);

                quantityInt = Integer.parseInt(quantity);
                dishpriceInt = Integer.parseInt(dishPrice);
                totalamount = dishpriceInt * quantityInt;
                totalAmountStr = String.valueOf(totalamount);


                Log.d("cartItemdname", preOrderData.getDishName());

                titlefoodTv.setText(preOrderData.getDishName());
                discripfoodTv.setText(preOrderData.getDishdiscription());
                pricefoodTv.setText("₹" + preOrderData.getDishPrice());
                totalpriceTv.setText("₹" + totalAmountStr);


                Picasso.with(context)
                        .load(preOrderData.getDishImage())
                        .placeholder(R.drawable.thali) // optional
                        .error(R.drawable.thali)         // optional
                        .into(foodImg);

                //quantityet.addTextChangedListener(new MyTextWatcher(convertView));


                plus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                      /*  if(counter<10){
                            counter++;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("",""+counter);
                            notifyDataSetChanged();
                            Log.d("abc",""+abc);
                        }*/
                     /*   if (item_counter[position] < 10) {
                            item_counter[position]++;
                            Log.d("item_counter", "" + item_counter[position]);
                            String abc = "" + item_counter[position];
                            Log.d("abc", "" + abc);
                            quantityet.setText(abc);
                            Log.d("quantityyet", "" + quantityet.getText().toString());
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            totalAmounttt = totalAmount * item_counter[position];
                            totalpriceTv.setText("₹" + totalAmounttt);
                            Log.d("totalAmounttt", "" + totalAmounttt);
                            notifyDataSetChanged();
                        }*/

                        preOrderData = newAL.get(position);
                        dishPrice = preOrderData.getDishPrice();
                        int totalAmount = Integer.parseInt(dishPrice);

                        QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.dinnerList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.dinnerList)));
                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    /*    if(counter>1){
                            counter--;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("abc",""+abc);
                        }*/
                       /* if (item_counter[position] > 1) {
                            item_counter[position]--;
                            Log.d("item_counterminus", "" + item_counter[position]);
                            String abc = "" + item_counter[position];
                            Log.d("abc", "" + abc);
                            quantityet.setText(abc);
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            totalAmounttt = totalAmount * item_counter[position];
                            totalpriceTv.setText("₹" + totalAmounttt);
                            Log.d("totalAmounttt", "" + totalAmounttt);
                            notifyDataSetChanged();
                        }*/
                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.dinnerList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!Global.dinnerList.isEmpty()) {

                            PreOrderData preOrderData = Global.dinnerList.get(position);
                            Global.dinnerList.remove(preOrderData);
                            Log.d("deletedetaillistclick", Global.dinnerList.toString());
                            dinnerAdapter = new DinnerAdapter(CartActivity.this, Global.dinnerList);
                            dinnerListview.setAdapter(dinnerAdapter);
                            Log.d("newAllllll", "" + newAL.size());


                            // DEcreasing
                            QuantityArray.get(FilterdArraylist.indexOf(Global.dinnerList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.dinnerList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.dinnerList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.dinnerList)).remove(position);
                            dishidArray.get(FilterdArraylist.indexOf(Global.dinnerList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();;
                            HomeActivity.countttTv.setText(""+listSize);




                            if (Global.dinnerList.size() == 0) {
                                Log.d("listEmpty", "" + Global.dinnerList.size());
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.dinnerList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.dinnerList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.dinnerList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.dinnerList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.dinnerList));
                                dinnerLayout.setVisibility(View.GONE);
                                dinnertxtTv.setVisibility(View.GONE);
                            }

                        } else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll", "" + Global.dinnerList.size());
                        }

                    }
                });
            }

            return convertView;

        }
    }

    public class MinimealAdapter extends ArrayAdapter<PreOrderData> {
        Activity context;
        Dialog dialog;
        public int pos;
        SharedPreferences loginSp;
        public static final String loginPrefrence = "loginSp";
        String loginId;
        String dishId, catId;
        ProgressDialog pDialog;
        String dishPrice, quantity = "1";
        public String totalAmountStr;
        int totalamount, dishpriceInt, quantityInt;
        int ttlAmountSum = 0, i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int totalAmounttt;

        public MinimealAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL == null) {

                return 0;
            } else {
                return newAL.size();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos = position;
          /*  if (position==0) {
                if (convertView==null) {
                    LayoutInflater headerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = headerInflater.inflate(R.layout.cart_header_layout, null);
                }
            }
            else {*/
            if (convertView == null) {

                LayoutInflater mainInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mainInflater.inflate(R.layout.cart_row_layout, null);
                final EditText quantityet = (EditText) convertView.findViewById(R.id.quntityTxt);
                quantityet.setShowSoftInputOnFocus(false);
                quantityet.setText("" + 1);
                Log.d("quantityet", quantityet.getText().toString());
                TextView titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
                TextView discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
                TextView pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
                ImageView foodImg = (ImageView) convertView.findViewById(R.id.catoneimg);
                final TextView totalpriceTv = (TextView) convertView.findViewById(R.id.totalprice);
                Button plus = (Button) convertView.findViewById(R.id.addBtn);
                Button minus = (Button) convertView.findViewById(R.id.subBtn);
                ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.deleteBtn);

                preOrderData = newAL.get(position);

                dishId = preOrderData.getDishId();
                catId = preOrderData.getCatId();
                discountId = preOrderData.getDiscountId();
                dishPrice = preOrderData.getDishPrice();
                quantity = preOrderData.getQuanitity();

                Log.d("quantity", quantity);

                quantityInt = Integer.parseInt(quantity);
                dishpriceInt = Integer.parseInt(dishPrice);
                totalamount = dishpriceInt * quantityInt;
                totalAmountStr = String.valueOf(totalamount);


                Log.d("cartItemdname", preOrderData.getDishName());

                titlefoodTv.setText(preOrderData.getDishName());
                discripfoodTv.setText(preOrderData.getDishdiscription());
                pricefoodTv.setText("₹" + preOrderData.getDishPrice());
                totalpriceTv.setText("₹" + totalAmountStr);


                Picasso.with(context)
                        .load(preOrderData.getDishImage())
                        .placeholder(R.drawable.thali) // optional
                        .error(R.drawable.thali)         // optional
                        .into(foodImg);

                //quantityet.addTextChangedListener(new MyTextWatcher(convertView));


                plus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                      /*  if(counter<10){
                            counter++;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("",""+counter);
                            notifyDataSetChanged();
                            Log.d("abc",""+abc);
                        }*/
                     /*   if (item_counter[position] < 10) {
                            item_counter[position]++;
                            Log.d("item_counter", "" + item_counter[position]);
                            String abc = "" + item_counter[position];
                            Log.d("abc", "" + abc);
                            quantityet.setText(abc);
                            Log.d("quantityyet", "" + quantityet.getText().toString());
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            totalAmounttt = totalAmount * item_counter[position];
                            totalpriceTv.setText("₹" + totalAmounttt);
                            Log.d("totalAmounttt", "" + totalAmounttt);
                            notifyDataSetChanged();
                        }*/

                        preOrderData = newAL.get(position);
                        dishPrice = preOrderData.getDishPrice();
                        int totalAmount = Integer.parseInt(dishPrice);

                        QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.lunchList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.minimealList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.minimealList)));
                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    /*    if(counter>1){
                            counter--;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("abc",""+abc);
                        }*/
                       /* if (item_counter[position] > 1) {
                            item_counter[position]--;
                            Log.d("item_counterminus", "" + item_counter[position]);
                            String abc = "" + item_counter[position];
                            Log.d("abc", "" + abc);
                            quantityet.setText(abc);
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            totalAmounttt = totalAmount * item_counter[position];
                            totalpriceTv.setText("₹" + totalAmounttt);
                            Log.d("totalAmounttt", "" + totalAmounttt);
                            notifyDataSetChanged();
                        }*/
                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.minimealList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!Global.minimealList.isEmpty()) {

                            PreOrderData preOrderData = Global.minimealList.get(position);
                            Global.minimealList.remove(preOrderData);
                            Log.d("deletedetaillistclick", Global.minimealList.toString());
                            miniAdapter = new MinimealAdapter(CartActivity.this, Global.minimealList);
                            miniListview.setAdapter(miniAdapter);
                            HomeActivity.countttTv.setText("" + Global.minimealList.size());
                            Log.d("newAllllll", "" + newAL.size());
                            QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.minimealList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.minimealList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.minimealList)).remove(position);
                            dishidArray.get(FilterdArraylist.indexOf(Global.minimealList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                            HomeActivity.countttTv.setText(""+listSize);


                            if (newAL.size() == 0) {
                                Log.d("listEmpty", "" + Global.minimealList);
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.minimealList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.minimealList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.minimealList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.minimealList));
                                dishidArray.remove(FilterdArraylist.indexOf(Global.minimealList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.minimealList));
                                minimealLayout.setVisibility(View.GONE);
                                minimealstxtTv.setVisibility(View.GONE);

                            }

                        } else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll", "" + newAL.size());
                        }

                    }
                });
            }

            return convertView;

        }
    }

    private static int hoursDifference(Date date1, Date date2) {

        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    public void InitializingArray() {
        int CountListNull = 0;
        if (Global.lunchList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.lunchList);
            selecttimeArray.add("Select Delivery Time");
        }

        if (Global.dinnerList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.dinnerList);
            selecttimeArray.add("Select Delivery Time");
        }
        if (Global.minimealList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.minimealList);
            selecttimeArray.add("Select Delivery Time");
        }
        if (Global.alcarteList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.alcarteList);
            selecttimeArray.add("Select Delivery Time");
        }
        for (int i = 0; i < FilterdArraylist.size(); i++) {
            ArrayList<Integer> Quant = new ArrayList<Integer>();
            ArrayList<String> Price = new ArrayList<String>();
            ArrayList<String> menuId = new ArrayList<String>();
            ArrayList<String> Category = new ArrayList<String>();
            ArrayList<String> DishId = new ArrayList<String>();
            for (int k = 0; k < FilterdArraylist.get(i).size(); k++) {

                Quant.add(1);
                Price.add(FilterdArraylist.get(i).get(k).getDishPrice());
                menuId.add(FilterdArraylist.get(i).get(k).getMenuId());
                Category.add(FilterdArraylist.get(i).get(k).getCategory());
                DishId.add(FilterdArraylist.get(i).get(k).getDishId());
            }
            QuantityArray.add(Quant);
            TotalpriceArray.add(Price);
            menuIdArray.add(menuId);
            categoryArray.add(Category);
            dishidArray.add(DishId);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Please Select Proceed Payment",Toast.LENGTH_SHORT).show();
    }
}

