package com.yumtiff.mohitkumar.tiffin.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.squareup.picasso.Picasso;
import com.yumtiff.mohitkumar.tiffin.Fragment.PreOrderFrag;
import com.yumtiff.mohitkumar.tiffin.Fragment.TodaysMenu;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.Model.TdayGroup;
import com.yumtiff.mohitkumar.tiffin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yumtiff.mohitkumar.tiffin.Adapter.PreOrderListAdapter.discountId;

public class PreActivity extends AppCompatActivity {
    Activity context;
    Dialog dialog;
    SwipeMenuListView breakfastListview,lunchListview,dinnerListview,miniListview;
    public static TextView breakfasttxtTv,lunchtxtTv,dinnertxtTv,minimealstxtTv;
    public TextView breakfastTv,lunchTv,dinnerTv,minimealTv;
    RelativeLayout breakfastLayout,lunchLayout,dinnerLayout,minimealLayout;
    Button breakfastdeliverytimeBtn,lunchdeliverytimeBtn,dinnerdeliverytimeBtn,minideliverytimeBtn,paymentproceedbtn;
    Toolbar toolbar;
    ImageView backIv;
    TextView titleTv;
    public static LunchAdapter lunchAdapter;
    public static DinnerAdapter dinnerAdapter;
    public static MinimealAdapter miniAdapter;
    public static BreakfastAdapter breakfastAdapter;
    boolean isLunchtime=false;
    boolean isDinnertime=false;
    boolean isMinimealtime=false;
    boolean isBreakfasttime=false;
    String[] starttimeArr,endtimeArr;
    int i;
    String startTime,endTime;
    Date dateStart = null;
    String selectedTime="Select Delivery Time";
    List<List<PreOrderData>> FilterdArraylist = new ArrayList<List<PreOrderData>>();
    ArrayList<ArrayList<Integer>> QuantityArray = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<String>> TotalpriceArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> menuIdArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> categoryArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> DishidArray = new ArrayList<ArrayList<String>>();
    ArrayList<String> selecttimeArray = new ArrayList<String>();
    List<String> selectedtimeList = new ArrayList<String>();
    public static String selectedtimeStr;
    public static int TotalPrice = 0;
    Global globalObj;
    public static String quanityArrStr,menuidArrStr,categoryArrStr,dishIdArrStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);
        globalObj=new Global();

        lunchListview = (SwipeMenuListView)findViewById(R.id.lunchListview);
        dinnerListview = (SwipeMenuListView)findViewById(R.id.dinnerListview);
        miniListview = (SwipeMenuListView)findViewById(R.id.miniListview);
        breakfastListview = (SwipeMenuListView)findViewById(R.id.breakfastListview);
        lunchtxtTv = (TextView)findViewById(R.id.lunchtxt);
        dinnertxtTv = (TextView)findViewById(R.id.dinnertxt);
        minimealstxtTv = (TextView)findViewById(R.id.minimealstxt);
        breakfasttxtTv = (TextView)findViewById(R.id.breakfasttxt);
        backIv = (ImageView) findViewById(R.id.back);
        titleTv = (TextView) findViewById(R.id.title);
        paymentproceedbtn = (Button) findViewById(R.id.paymentproceedbtn);
        breakfastLayout = (RelativeLayout) findViewById(R.id.breakfastLayout);
        lunchLayout = (RelativeLayout) findViewById(R.id.lunchLayout);
        dinnerLayout = (RelativeLayout) findViewById(R.id.dinnerLayout);
        minimealLayout = (RelativeLayout) findViewById(R.id.minimealLayout);
        lunchTv = (TextView) findViewById(R.id.lunchhtxt);
        dinnerTv = (TextView) findViewById(R.id.dinnerrtxt);
        minimealTv = (TextView) findViewById(R.id.minimealstxt);
        breakfastTv = (TextView) findViewById(R.id.breakfastttxt);

        lunchdeliverytimeBtn = (Button)findViewById(R.id.lunchdeliverytimeBtn);
        dinnerdeliverytimeBtn = (Button)findViewById(R.id.dinnerdeliverytimeBtn);
        minideliverytimeBtn = (Button)findViewById(R.id.minideliverytimeBtn);
        breakfastdeliverytimeBtn = (Button)findViewById(R.id.breakfastdeliverytimeBtn);
        Log.d("prelunchListttt",Global.prelunchList.toString());
        Log.d("predinnerList",Global.predinnerList.toString());
        Log.d("preminimealList",Global.preminimealList.toString());
        Log.d("prebreakfastList",Global.prebreakfastList.toString());
        backIv.setVisibility(View.GONE);
        InitializingArray();


        titleTv.setText(getResources().getString(R.string.previewcart));
       /* backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Intent i = new Intent(Maincart.this, HomeActivity.class);
                startActivity(i);
                finish();*//*
                finish();

            }
        });*/

        starttimeArr=new String[PreOrderFrag.tdaygrpList.size()];
        endtimeArr=new String[PreOrderFrag.tdaygrpList.size()];
        for (i = 0; i< PreOrderFrag.tdaygrpList.size(); i++) {
            TdayGroup tdayGroup=PreOrderFrag.tdaygrpList.get(i);
            startTime = tdayGroup.getStartTime();
            endTime = tdayGroup.getEndTime();
            starttimeArr[i]=startTime;
            endtimeArr[i]=endTime;

            Log.d("preTime","StartTime: "+startTime+" EndTime: "+endTime);
            Log.d("category",tdayGroup.getCategory());
        }

        lunchAdapter = new LunchAdapter(this, Global.prelunchList);
        lunchListview.setAdapter(lunchAdapter);
        dinnerAdapter = new DinnerAdapter(this, Global.predinnerList);
        dinnerListview.setAdapter(dinnerAdapter);
        miniAdapter = new MinimealAdapter(this, Global.preminimealList);
        miniListview.setAdapter(miniAdapter);
        breakfastAdapter = new BreakfastAdapter(this, Global.prebreakfastList);
        breakfastListview.setAdapter(breakfastAdapter);

        setDynamicHeight(lunchListview);
        setDynamicHeight(dinnerListview);
        setDynamicHeight(miniListview);
        setDynamicHeight(breakfastListview);






        if (Global.prelunchList.size()==0) {
            lunchtxtTv.setVisibility(View.GONE);
            lunchLayout.setVisibility(View.GONE);

        }
        else {
            lunchtxtTv.setVisibility(View.VISIBLE);
            lunchLayout.setVisibility(View.VISIBLE);

        }
        if (Global.predinnerList.size()==0) {
            dinnertxtTv.setVisibility(View.GONE);
            dinnerLayout.setVisibility(View.GONE);
        }
        else {
            dinnertxtTv.setVisibility(View.VISIBLE);
            dinnerLayout.setVisibility(View.VISIBLE);

        }
        if (Global.preminimealList.size()==0) {
            minimealstxtTv.setVisibility(View.GONE);
            minimealLayout.setVisibility(View.GONE);

        }
        else {
            minimealstxtTv.setVisibility(View.VISIBLE);
            minimealLayout.setVisibility(View.VISIBLE);

        }
        if (Global.prebreakfastList.size()==0) {
            breakfasttxtTv.setVisibility(View.GONE);
            breakfastLayout.setVisibility(View.GONE);
        }
        else {
            breakfasttxtTv.setVisibility(View.VISIBLE);
            breakfastLayout.setVisibility(View.VISIBLE);

        }

        breakfastdeliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBreakfasttime=true;
                Log.d("BreakfaststartTime", starttimeArr[0]);
                Log.d("BreakfastendTime", endtimeArr[0]);
                selectTime(breakfastdeliverytimeBtn,starttimeArr[0],endtimeArr[0]);
            }
        });

        lunchdeliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLunchtime=true;
                Log.d("lunchstartTime", starttimeArr[1]);
                Log.d("lunchendTime", endtimeArr[1]);
                selectTime(lunchdeliverytimeBtn,starttimeArr[1],endtimeArr[1]);

            }
        });
        dinnerdeliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDinnertime=true;
                Log.d("DinnerstartTime", starttimeArr[2]);
                Log.d("DinnerendTime", endtimeArr[2]);
                selectTime(dinnerdeliverytimeBtn,starttimeArr[2],endtimeArr[2]);
            }
        });
        minideliverytimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMinimealtime=true;
                Log.d("MinistartTime",  starttimeArr[3]);
                Log.d("MiniendTime", endtimeArr[3]);
                selectTime(minideliverytimeBtn,starttimeArr[3],endtimeArr[3]);

            }
        });

     /*   CreateStringArray(TotalpriceArray);
        CreateQuantityArray(QuantityArray);
        CreateMenuArray(menuIdArray);
        CreateMenuArray(categoryArray);
        Log.d("pretotalprice",CreateStringArray(TotalpriceArray));
        Log.d("prequantityArr",CreateQuantityArray(QuantityArray));
        Log.d("premenuArr",CreateMenuArray(menuIdArray));
        Log.d("precategoryArr",CreateMenuArray(categoryArray));*/

        paymentproceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (!isBreakfasttime==true && !isLunchtime==true && !isDinnertime==true && !isMinimealtime==true) {
                    Toast.makeText(getApplicationContext(),"Please Enter Delivery Time",Toast.LENGTH_SHORT).show();
                }
                else if (!isBreakfasttime==true) {
                    if (Global.prebreakfastList.size()>0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Breakfast Time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Breaklist not empty", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (!isLunchtime==true) {
                    if (Global.prelunchList.size()>0) {
                        Toast.makeText(getApplicationContext(),"Please Enter Lunch Time",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Lunchlist not empty", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (!isDinnertime==true) {
                    if (Global.predinnerList.size()>0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Dinner Time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Dinnerlist not empty", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (!isMinimealtime==true) {
                    if (Global.preminimealList.size()>0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Minimeals Time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Minimeallist not empty", Toast.LENGTH_SHORT).show();
                    }

                }

                else {
                    Intent i = new Intent(PreActivity.this, LoginActivity.class);
                    startActivity(i);
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
                dishIdArrStr=Global.CreateMenuArray(DishidArray);



                Log.d("pretotalprice",Global.CreateStringArray(TotalpriceArray));
                Log.d("prequantityArr",Global.CreateQuantityArray(QuantityArray));
                Log.d("premenuArr",Global.CreateMenuArray(menuIdArray));
                Log.d("precategoryArr",Global.CreateMenuArray(categoryArray));
                Log.d("precategoryArr",Global.CreateMenuArray(DishidArray));
                Intent i = new Intent(PreActivity.this, LoginActivity.class);
                i.putExtra("flag","2");
                startActivity(i);
                }




            }
        });

    }

    private void selectTime(final Button addonsBtn,String StartTime,String EndTime) {

        dialog = new Dialog(this);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lunch_timepicker_dialog);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.7f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
        dialog.getWindow().setAttributes(lp);
        final SeekBar timeSeek = (SeekBar)dialog.findViewById(R.id.timesel);
        final Button doneBtn = (Button)dialog.findViewById(R.id.doneBtn);
        final TextView starttimeTv = (TextView)dialog.findViewById(R.id.starttime);
        final TextView selecttimeTv = (TextView)dialog.findViewById(R.id.selecttime);
        final TextView selectmTv = (TextView)dialog.findViewById(R.id.selectedTm);
        Date startdate=convertStringtoDate(StartTime);
        starttimeTv.setText(StartTime);
        selectmTv.setText(StartTime);

        final int interval = 5;
        String dtStart = StartTime;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            dateStart = format.parse(dtStart);
            Date dateEnd = format.parse(EndTime);
            Log.d("okil",hoursDifference(dateEnd,dateStart)+"");

            timeSeek.setMax(hoursDifference(dateEnd,dateStart)*4);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        timeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("rtyuiu",i+"");

                int MinutesInMillies = i*15 ;

                Log.d("dated",addMinutesToDate(MinutesInMillies,dateStart)+"");
                selectedTime=""+addMinutesToDate(MinutesInMillies,dateStart);
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                selectedTime=format.format(addMinutesToDate(MinutesInMillies,dateStart));
                selecttimeTv.setText(selectedTime);
                selectmTv.setText(selectedTime);


                //Toast.makeText(context, "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
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

    private static Date addMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public Date convertStringtoDate(String startTime) {
        Date date=null;
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

    private static int hoursDifference(Date date1, Date date2) {

        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
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





    public class BreakfastAdapter extends ArrayAdapter<PreOrderData> {
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

        public BreakfastAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL == null) {
                return 0;
            } else {
                Log.d("newAlcount",""+newAL.size());
                return newAL.size();

            }
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos = position;
            Log.d("listsizeee",""+newAL.size());
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

                      /*  if (item_counter[position] < 10) {
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

                        QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)));

                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


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

                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!newAL.isEmpty()) {


                            PreOrderData preOrderData = newAL.get(position);
                            newAL.remove(preOrderData);
                            Log.d("deletedetaillistclick", newAL.toString());
                            breakfastAdapter = new BreakfastAdapter(PreActivity.this, newAL);
                            breakfastListview.setAdapter(breakfastAdapter);
                            Log.d("newAllllll", "" + newAL.size());
                            QuantityArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).remove(position);
                            DishidArray.get(FilterdArraylist.indexOf(Global.prebreakfastList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                            HomeActivity.countttTv.setText(""+listSize);

                            if(newAL.size()==0) {
                                Log.d("listEmpty",""+newAL.size());
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.prebreakfastList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.prebreakfastList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.prebreakfastList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.prebreakfastList));
                                DishidArray.remove(FilterdArraylist.indexOf(Global.prebreakfastList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.prebreakfastList));
                                breakfastLayout.setVisibility(View.GONE);
                                breakfasttxtTv.setVisibility(View.GONE);
                            }

                        }
                        else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll",""+newAL.size());
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
        String dishId,catId;
        ProgressDialog pDialog;
        String dishPrice,quantity="1";
        public String totalAmountStr;
        int totalamount,dishpriceInt,quantityInt;
        int ttlAmountSum=0,i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1, 1, 1, 1,1,1,1,1,1,1,1,1,1,1,1,1};
        int totalAmounttt;

        public LunchAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL==null) {

                return 0;
            }
            else {
                return newAL.size();
            }
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos=position;
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

                      /*  if (item_counter[position] < 10) {
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

                        QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.prelunchList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.prelunchList)));
                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


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

                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.prelunchList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!newAL.isEmpty()) {
                            PreOrderData preOrderData = newAL.get(position);
                            newAL.remove(preOrderData);
                            Log.d("deletedetaillistclick", newAL.toString());
                            lunchAdapter = new LunchAdapter(PreActivity.this, newAL);
                            lunchListview.setAdapter(lunchAdapter);
                            Log.d("newAllllll", "" + newAL.size());
                            QuantityArray.get(FilterdArraylist.indexOf(Global.prelunchList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.prelunchList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.prelunchList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.prelunchList)).remove(position);
                            DishidArray.get(FilterdArraylist.indexOf(Global.prelunchList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                            HomeActivity.countttTv.setText(""+listSize);

                            if(newAL.size()==0){
                                Log.d("listEmpty",""+newAL.size());
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.prelunchList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.prelunchList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.prelunchList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.prelunchList));
                                DishidArray.remove(FilterdArraylist.indexOf(Global.prelunchList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.prelunchList));
                                lunchLayout.setVisibility(View.GONE);
                                lunchtxtTv.setVisibility(View.GONE);
                            }

                        }
                        else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll",""+newAL.size());
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
        String dishId,catId;
        ProgressDialog pDialog;
        String dishPrice,quantity="1";
        public String totalAmountStr;
        int totalamount,dishpriceInt,quantityInt;
        int ttlAmountSum=0,i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int totalAmounttt;

        public DinnerAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL==null) {

                return 0;
            }
            else {
                return newAL.size();
            }
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos=position;
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

                      /*  if (item_counter[position] < 10) {
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

                        QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.predinnerList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.predinnerList)));

                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    /*    if (item_counter[position] > 1) {
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

                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.predinnerList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!newAL.isEmpty()) {

                            PreOrderData preOrderData= newAL.get(position);
                            newAL.remove(preOrderData);
                            Log.d("deletedetaillistclick", newAL.toString());
                            dinnerAdapter = new DinnerAdapter(PreActivity.this,  newAL);
                            dinnerListview.setAdapter(dinnerAdapter);
                            Log.d("newAllllll", "" + newAL.size());
                            QuantityArray.get(FilterdArraylist.indexOf(Global.predinnerList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.predinnerList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.predinnerList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.predinnerList)).remove(position);
                            DishidArray.get(FilterdArraylist.indexOf(Global.predinnerList)).remove(position);
                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                            HomeActivity.countttTv.setText(""+listSize);

                            if(newAL.size()==0){
                                Log.d("listEmpty",""+newAL.size());
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.predinnerList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.predinnerList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.predinnerList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.predinnerList));
                                DishidArray.remove(FilterdArraylist.indexOf(Global.predinnerList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.predinnerList));
                                dinnerLayout.setVisibility(View.GONE);
                                dinnertxtTv.setVisibility(View.GONE);
                            }

                        }
                        else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll",""+newAL.size());
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
        String dishId,catId;
        ProgressDialog pDialog;
        String dishPrice,quantity="1";
        public String totalAmountStr;
        int totalamount,dishpriceInt,quantityInt;
        int ttlAmountSum=0,i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;
        private int[] item_counter = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int totalAmounttt;

        public MinimealAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (newAL==null) {

                return 0;
            }
            else {
                return newAL.size();
            }
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            pos=position;
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

                       /* if (item_counter[position] < 10) {
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

                        QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position);
                        QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position) + 1);
                        quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position));
                        totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position) * totalAmount);
                        Integer.parseInt(TotalpriceArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position));
                        TotalpriceArray.get(FilterdArraylist.indexOf(Global.preminimealList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position)));
                        notifyDataSetChanged();
                        Log.d("uijkfqwe", "" + TotalpriceArray.get(FilterdArraylist.indexOf(Global.preminimealList)));

                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                     /*   if (item_counter[position] > 1) {
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

                        if (QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position) > 1) {
                            preOrderData = newAL.get(position);
                            dishPrice = preOrderData.getDishPrice();
                            int totalAmount = Integer.parseInt(dishPrice);
                            QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).set(position, QuantityArray.get(FilterdArraylist.indexOf(Global.minimealList)).get(position) - 1);
                            quantityet.setText("" + QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position));
                            totalpriceTv.setText("₹" + QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position) * totalAmount);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.preminimealList)).set(position, String.valueOf(totalAmount * QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).get(position)));
                        }
                        notifyDataSetChanged();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        if (!newAL.isEmpty()) {

                            PreOrderData preOrderData= newAL.get(position);
                            newAL.remove(preOrderData);
                            Log.d("deletedetaillistclick", newAL.toString());
                            miniAdapter = new MinimealAdapter(PreActivity.this,  newAL);
                            miniListview.setAdapter(miniAdapter);
                            HomeActivity.countttTv.setText(""+Global.newAL.size());
                            Log.d("newAllllll", "" + newAL.size());
                            QuantityArray.get(FilterdArraylist.indexOf(Global.preminimealList)).remove(position);
                            TotalpriceArray.get(FilterdArraylist.indexOf(Global.preminimealList)).remove(position);
                            menuIdArray.get(FilterdArraylist.indexOf(Global.preminimealList)).remove(position);
                            categoryArray.get(FilterdArraylist.indexOf(Global.preminimealList)).remove(position);
                            DishidArray.get(FilterdArraylist.indexOf(Global.preminimealList)).remove(position);

                            int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                                    Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                            HomeActivity.countttTv.setText(""+listSize);

                            if(newAL.size()==0) {
                                Log.d("listEmpty",""+newAL.size());
                                //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                                QuantityArray.remove(FilterdArraylist.indexOf(Global.preminimealList));
                                TotalpriceArray.remove(FilterdArraylist.indexOf(Global.preminimealList));
                                menuIdArray.remove(FilterdArraylist.indexOf(Global.preminimealList));
                                categoryArray.remove(FilterdArraylist.indexOf(Global.preminimealList));
                                DishidArray.remove(FilterdArraylist.indexOf(Global.preminimealList));
                                FilterdArraylist.remove(FilterdArraylist.indexOf(Global.preminimealList));

                                minimealLayout.setVisibility(View.GONE);
                                minimealstxtTv.setVisibility(View.GONE);
                            }

                        }
                        else {

                            //Toast.makeText(getApplicationContext(),"list null",Toast.LENGTH_SHORT).show();
                            Log.d("newAllllll",""+newAL.size());
                        }

                    }
                });
            }

            return convertView;

        }
    }

    public void InitializingArray() {
        int CountListNull = 0;
        if (Global.prebreakfastList.size() !=0) {
            CountListNull++;
            FilterdArraylist.add(Global.prebreakfastList);
            selecttimeArray.add("Select Delivery Time");
        }
        if (Global.prelunchList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.prelunchList);
            selecttimeArray.add("Select Delivery Time");
        }

        if (Global.predinnerList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.predinnerList);
            selecttimeArray.add("Select Delivery Time");
        }
        if (Global.preminimealList.size() != 0) {
            CountListNull++;
            FilterdArraylist.add(Global.preminimealList);
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
            DishidArray.add(DishId);
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Please Select Proceed Payment",Toast.LENGTH_SHORT).show();
    }




}
