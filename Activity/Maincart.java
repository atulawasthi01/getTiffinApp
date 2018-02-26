package com.yumtiff.mohitkumar.tiffin.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yumtiff.mohitkumar.tiffin.Adapter.SeparatedListAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.app.MainActivity;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rajat.gupta on 3/25/2017.
 */

public class Maincart extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    ImageView backIv;
    TextView titleTv, quntityTxt;
    public static SwipeMenuListView listView;
    Button paymentproceedbtn, addBtn, subBtn;
    //Button preorderBtn;
    ImageView addmoreimg;
    SharedPreferences loginSp;
    public static final String loginPrefrence = "loginSp";
    String loginId, dishIdd, menuIdd;
    ProgressDialog pDialog;
    public static String id;
    DialogFragment dialogfragment;
    TimePickerDialog timepickerdialog1;
    JSONArray jsonArray;
    int Chour, Cminute;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    int totalDishPrice = 0;
    String displaymsg;
    SharedPreferences dishpriceSp;
    public static final String dishpricePrefrence = "dishprice";
    public static String deltimeStr, deldatetimeStr;
    public static String delivaryTime;
    public static String disStr, catId, menuidStr;
    int currentHour, currentMint, countQuntity = 0;
    String quntityy = "0";
    String qunt;
    public static String quantityStr;
    String[] totalamuntArr;
    int ttlAmountSum = 0, i;
    int priceee, quntt, totalpriceee;
    int ttlpriceSum = 0;
    String qnt;
    String quantity = "1";
    public static ArrayList<Integer> QuantiyArray = new ArrayList<Integer>();
    public DecimalFormat df;
    int quantityyy;
    PreOrderData preOrderData;
    int ii,j,k,l;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listView = (SwipeMenuListView) findViewById(R.id.cartList);
        backIv = (ImageView) findViewById(R.id.back);
        titleTv = (TextView) findViewById(R.id.title);
        quntityTxt = (TextView) findViewById(R.id.quntityTxt);
        //addmoreimg = (ImageView) findViewById(R.id.addmoreimg);
        paymentproceedbtn = (Button) findViewById(R.id.proceedBtn);
        //preorderBtn = (Button) findViewById(R.id.preorderBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        subBtn = (Button) findViewById(R.id.subBtn);
        loginSp = getSharedPreferences(loginPrefrence, MODE_PRIVATE);
        loginId = loginSp.getString("loginId", "");
        titleTv.setText(getResources().getString(R.string.viewcart));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Maincart.this, HomeActivity.class);
                startActivity(i);
                finish();*/
                finish();
                /*Alcarter alcarter = new Alcarter();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_cart, alcarter).commit();*/

            }
        });


        Log.d("newALlll", Global.newAL.toString());


       /* SeparatedListAdapter separatedListAdapter=new SeparatedListAdapter(this);
        separatedListAdapter.addSection("Lunch",new CartAdapter(this,Global.lunchList));
        separatedListAdapter.addSection("Dinner",new CartAdapter(this,Global.dinnerList));
        separatedListAdapter.addSection("Mini Meals",new CartAdapter(this,Global.minimealList));
        separatedListAdapter.addSection("Alacarte",new CartAdapter(this,Global.alcarteList));
        //separatedListAdapter.addSection("Addons",new CartAdapter(this,Global.addonsList));
        listView.setAdapter(separatedListAdapter);
        separatedListAdapter.notifyDataSetChanged();*/





            /*listAdapter = new CartAdapter(this, Global.newAL);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();*/





        initControls();

        final Calendar c = Calendar.getInstance();

        Chour = c.get(Calendar.HOUR_OF_DAY);

        Cminute = c.get(Calendar.MINUTE);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

      /*  if (Global.isCartAdd==true) {

            Toast.makeText(getApplicationContext(), "Database add", Toast.LENGTH_LONG).show();

            getcartdata();

            listAdapter = new CartAdapter(Maincart.this, Global.feedItems);
            listView.setAdapter(listAdapter);

        }*/

        /*if (Network.isNetworkCheck(getApplicationContext())) {
            getcartList();

        } else {
            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }*/


       /* addmoreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Maincart.this, AdonsActivity.class);
                startActivity(i);
                finish();
            }
        });*/

        paymentproceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogfragment = new TimePickerToday();
                dialogfragment.show(getFragmentManager(), "Time Picker with Theme 3");

            }
        });

       /* totalamuntArr=new String[Global.feedItems.size()];

        for (i=0;i<Global.feedItems.size();i++) {
            totalamuntArr[i]=CartAdapter.totalAmountStr;
            int ttlamtt= Integer.parseInt(totalamuntArr[i]);
            ttlAmountSum=ttlAmountSum+ttlamtt;

        }*/
        Log.d("ttlAmountSum", "" + ttlAmountSum);

       /* preorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .build()
                        .show();

            }
        });*/

    }

  /*  public final ProductClickListener productClickListener = new ProductClickListener() {
        @Override
        public void onMinusClick(PreOrderData preOrderData) {
            int i = PreorderAdapter.newAL.indexOf(preOrderData);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            PreOrderData updatedProduct = new PreOrderData((preOrderData.qunt - 1));
            PreorderAdapter.newAL.remove(preOrderData);
            PreorderAdapter.newAL.add(i, updatedProduct);
            listAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPlusClick(PreOrderData preOrderData) {
            int i = PreorderAdapter.newAL.indexOf(preOrderData);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            PreOrderData updatedProduct = new PreOrderData((preOrderData.qunt + 1));
            PreorderAdapter.newAL.remove(preOrderData);
            PreorderAdapter.newAL.add(i, updatedProduct);
            listAdapter.notifyDataSetChanged();
        }
    };*/

    private void initControls() {

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(80));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_discard);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {


                    case 0:
                        /*Global.newAL.remove(position);
                        listAdapter.notifyDataSetChanged();*/



                        break;
                }
                return true;
            }
        });

        //mListView

        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {

            }

            @Override
            public void onMenuClose(int position) {

            }
        });

        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        /*super.onBackPressed();
        Intent i = new Intent(Maincart.this, HomeActivity.class);
        startActivity(i);*/
        finish();
    }


/*
    private class CartAdapter extends ArrayAdapter<PreOrderData> {
        Activity context;
        Dialog dialog;
        List<CartItem> feedItems;
        CartItem cartItem;
        //Holder holder;
        LayoutInflater inflater;
        public int pos;
        SharedPreferences loginSp;
        public static final String loginPrefrence = "loginSp";
        String loginId;
        SharedPreferences foodIdSp;
        public static final String foodIdPrefrence = "foodid";
        String dishId;
        int deleteindexpos,quantityindexpos,counterAdd=0,counterSub;
        int[] quntArr = {1,2,3,4};
        int[] qunttvArr;
        ProgressDialog pDialog;
        String quantityStr;
        int presenrIntValue;
        String dishPrice,quantity="1";
        public String totalAmountStr;
        int totalamount,dishpriceInt,quantityInt;
        String[] totalamuntArr;
        int ttlAmountSum=0,i;
        List<PreOrderData> newAL;
        PreOrderData preOrderData;

        int getDeleteindexpos;
        private int[] item_counter = {1, 1, 1, 1,1,1,1,1,1,1,1,1,1,1,1,1};
        int totalAmounttt;
        int qunt;
        int count=0,globcount;

        public CartAdapter(Activity context, List<PreOrderData> newAL) {
            super(context, 0, newAL);
            this.newAL = newAL;
            this.context = context;
            this.newAL.addAll(newAL);

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
            for (int i=0;i<listAdapter.getCount();i++){
                listAdapter.getItem(i);
                Log.d("countt",""+i);
            }
            preOrderData = getItem(position);
            */
/*preOrderData = getItem(position);
            pos=position;*//*

            df = new DecimalFormat("0.00##");
            */
/*preOrderData = newAL.get(position);
            newAL.toString();
            Log.d("productList",""+newAL.toString());*//*


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.cart_row_layout, null);
                final EditText quantityet = (EditText) convertView.findViewById(R.id.quntityTxt);
                quantityet.setText(""+1);
                Log.d("quantityet",quantityet.getText().toString());
                TextView titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
                TextView discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
                TextView pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
                ImageView foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);
                //holder.deleteBtn = (Button)convertView.findViewById(R.id.deleteBtn);
                TextView quantityTv = (TextView)convertView.findViewById(R.id.quantitytxt);
                TextView totalpriceTv = (TextView)convertView.findViewById(R.id.totalprice);
                Button plus=(Button) convertView.findViewById(R.id.addBtn);
                Button minus=(Button) convertView.findViewById(R.id.subBtn);

                preOrderData = newAL.get(position);

                dishId = preOrderData.getDishId();
                catId = preOrderData.getCatId();
                discountId = preOrderData.getDiscountId();
                dishPrice=preOrderData.getDishPrice();



                quantity=preOrderData.getQuanitity();
                Log.d("quantity",quantity);
                quantityInt= Integer.parseInt(quantity);
                dishpriceInt= Integer.parseInt(dishPrice);
                totalamount=dishpriceInt*quantityInt;
                totalAmountStr= String.valueOf(totalamount);

                Log.d("cartItemdname",preOrderData.getDishName());

                titlefoodTv.setText(preOrderData.getDishName());
                //holder.discripfoodTv.setText(preOrderData.getDishDescription());
                pricefoodTv.setText("₹"+preOrderData.getDishPrice());
                quantityTv.setText(quantity);
                totalpriceTv.setText(totalAmountStr);



                Picasso.with(context)
                        .load(preOrderData.getDishImage())
                        .placeholder(R.drawable.thali) // optional
                        .error(R.drawable.thali)         // optional
                        .into(foodImg);

                //quantityet.addTextChangedListener(new MyTextWatcher(convertView));

                plus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                      */
/*  if(counter<10){
                            counter++;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("",""+counter);
                            notifyDataSetChanged();
                            Log.d("abc",""+abc);
                        }*//*

                        if (item_counter[position] < 10) {
                            item_counter[position]++;
                            Log.d("item_counter", "" + item_counter[position]);
                            String abc = "" + item_counter[position];
                            Log.d("abc", "" + abc);
                            quantityet.setText(abc);
                            Log.d("quantityyet",""+quantityet.getText().toString());
                            notifyDataSetChanged();
                        }
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    */
/*    if(counter>1){
                            counter--;
                            String abc=""+counter;
                            quantityet.setText(abc);
                            Log.d("abc",""+abc);
                        }*//*

                        if (item_counter[position] > 1) {
                            item_counter[position]--;
                            Log.d("item_counterminus", "" + item_counter[position]);
                            String abc = "" + item_counter[position];
                            Log.d("abc", "" + abc);
                            quantityet.setText(abc);
                            notifyDataSetChanged();
                        }
                    }
                });
            }

           */
/* if (convertView == null) {
                //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

                inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.cart_row_layout, parent, false);
                holder = new Holder();
                //holder.codefoodTv = (TextView)convertView.findViewById(R.id.codefood);
                holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
                holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
                holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
                holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);
                //holder.deleteBtn = (Button)convertView.findViewById(R.id.deleteBtn);
                holder.quantityTv = (TextView)convertView.findViewById(R.id.quantitytxt);
                holder.totalpriceTv = (TextView)convertView.findViewById(R.id.totalprice);
                holder.addBtn = (Button)convertView.findViewById(R.id.addBtn);
                holder.subBtn = (Button)convertView.findViewById(R.id.subBtn);
                holder.quantity = (TextView)convertView.findViewById(R.id.quntityTxt);
            *//*
*/
/*holder.addBtn.setVisibility(View.INVISIBLE);
            holder.subBtn.setVisibility(View.INVISIBLE);
            holder.quantity.setVisibility(View.INVISIBLE);*//*
*/
/*



                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            loginSp = context.getSharedPreferences(loginPrefrence, context.MODE_PRIVATE);
            loginId = loginSp.getString("loginId", "");



            preOrderData = newAL.get(position);

            //dishId = cartItem.getId();
            //catId = cartItem.getCategoryId();
            //discountId = cartItem.getDiscounId();
            dishPrice=preOrderData.getDishPrice();



            quantity=preOrderData.getQuanitity();
            Log.d("quantity",quantity);
            quantityInt= Integer.parseInt(quantity);
            dishpriceInt= Integer.parseInt(dishPrice);
            totalamount=dishpriceInt*quantityInt;
            totalAmountStr= String.valueOf(totalamount);

            Log.d("cartItemdname",preOrderData.getDishName());

            holder.titlefoodTv.setText(preOrderData.getDishName());
            //holder.discripfoodTv.setText(preOrderData.getDishDescription());
            holder.pricefoodTv.setText("₹"+preOrderData.getDishPrice());
            holder.quantityTv.setText(quantity);
            holder.totalpriceTv.setText(totalAmountStr);



            Picasso.with(context)
                    .load(preOrderData.getDishImage())
                    .placeholder(R.drawable.thali) // optional
                    .error(R.drawable.thali)         // optional
                    .into(holder.foodImg);



        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item_counter[position] < 10) {
                    Log.d("pos",""+position);
                    item_counter[position]++;
                    Log.d("item_counter", "" + item_counter[position]);
                    String abc = "" + item_counter[position];
                    Log.d("abc", "" + abc);
                    holder.quantity.setText(abc);
                    Log.d("quantityyet",""+holder.quantity.getText().toString());
                    notifyDataSetChanged();
                    int totalAmount= Integer.parseInt(totalAmountStr);
                    totalAmounttt=totalAmount*item_counter[position];
                    holder.totalpriceTv.setText(""+totalAmounttt);
                    Log.d("totalAmounttt",""+totalAmounttt);
                }
            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item_counter[position] > 1) {
                    item_counter[position]--;
                    Log.d("item_counterminus", "" + item_counter[position]);
                    String abc = "" + item_counter[position];
                    Log.d("abc", "" + abc);
                    holder.quantity.setText(abc);
                    notifyDataSetChanged();
                    int totalAmount= Integer.parseInt(totalAmountStr);
                    totalAmounttt=totalAmount*item_counter[position];
                    holder.totalpriceTv.setText(""+totalAmounttt);
                    Log.d("totalAmounttt",""+totalAmounttt);
                }

            }
        });*//*



           */
/* holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(v.getTag()==null)
                        count=0;
                    else {
                        count=Integer.parseInt(v.getTag().toString());
                    }
                    count++;
                    ((TextView)v).setText(""+count);
                    holder.quantity.setTag(v);
                    holder.quantity.getTag();
                    holder.quantity.setText(""+count);
                    int totalAmount= Integer.parseInt(totalAmountStr);
                    totalAmounttt=totalAmount*count;
                    holder.totalpriceTv.setText(""+totalAmounttt);
                    Log.d("count",""+count);
                    v.setTag(count);






               *//*
*/
/* Maincart.QuantiyArray.set(position,Maincart.QuantiyArray.get(position) + 1);
                holder.quantity.setText(String.valueOf(Maincart.QuantiyArray.get(position)));
                Log.d("plusclick",""+Maincart.QuantiyArray.get(position));
                Log.d("plusposition",""+position);
                int totalAmount= Integer.parseInt(totalAmountStr);
                totalAmounttt=totalAmount*Maincart.QuantiyArray.get(position);
                holder.totalpriceTv.setText(""+totalAmounttt);*//*
*/
/*

                }
            });

            holder.subBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    globcount=count;
                    if(v.getTag()==null)
                        globcount=count;
                    else{
                        globcount=Integer.parseInt(v.getTag().toString());
                    }
                    globcount--;
                    ((TextView)v).setText(""+globcount);
                    holder.quantity.setTag(v);
                    holder.quantity.getTag();
                    holder.quantity.setText(""+globcount);

                    int totalAmount= Integer.parseInt(totalAmountStr);
                    totalAmounttt=totalAmount*globcount;
                    holder.totalpriceTv.setText(""+totalAmounttt);
                    Log.d("globcount",""+globcount);

                    v.setTag(globcount);

              *//*
*/
/*  Maincart.QuantiyArray.set(position,Maincart.QuantiyArray.get(position) - 1);
                holder.quantity.setText(String.valueOf(Maincart.QuantiyArray.get(position)));
                Log.d("plusclick",""+Maincart.QuantiyArray.get(position));
                Log.d("plusposition",""+position);
                int totalAmount= Integer.parseInt(totalAmountStr);
                totalAmounttt=totalAmount*Maincart.QuantiyArray.get(position);
                holder.totalpriceTv.setText(""+totalAmounttt);*//*
*/
/*


                }
            });*//*



            return convertView;

        }


       */
/* public class Holder {
            TextView titlefoodTv, discripfoodTv, pricefoodTv,quantityTv,totalpriceTv,quantity;
            ImageView foodImg;
            Button addBtn,subBtn;
        }*//*

    }
*/

   /* public class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            DecimalFormat df = new DecimalFormat("0.00##");
            String qtyString = s.toString().trim();
            quantityyy = qtyString.equals("") ? 0: Integer.valueOf(qtyString);

            EditText qtyView = (EditText) view.findViewById(R.id.quntityTxt);
            preOrderData = (PreOrderData) qtyView.getTag();

            if(preOrderData.getQuanitity() != quantityyy){

                currPrice = product.getExt();
                extPrice = quantityyy * product.getProductPrice();
                Log.d("extprice",""+extPrice);
                priceDiff = Double.valueOf(df.format(extPrice - currPrice));
                product.setUserQty(quantityyy);
                product.setExt(extPrice);

                TextView ext = (TextView) view.findViewById(R.id.ext);
                if(product.getUserQty() != 0){
                    ext.setText("" + df.format(product.getExt()));
                }
                else {
                    ext.setText("");
                }
                if(product.getUserQty() != 0){
                    qtyView.setText(String.valueOf(product.getUserQty()));
                }
                else {
                    qtyView.setText("");
                }
                orderTotal += priceDiff;
                Log.d("pricedifff",""+priceDiff);
                Log.d("orderTotal",""+orderTotal);

                cartTotal.setText(df.format(orderTotal));
                clicktotal.setText(df.format(orderTotal));
                AppConstantVariables.totalsumamt=clicktotal.getText().toString();
                subtotalamt=cartTotal.getText().toString().trim();
                Log.d("subtotalamt",subtotalamt);


            }
        }
        public void afterTextChanged(Editable s) {
            return;
        }
    }*/



    @SuppressLint("validFragment")
    public class TimePickerToday extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            timepickerdialog1 = new TimePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_DARK,this,Chour,Cminute,false);

            return timepickerdialog1;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



            /*Log.d("todaytime:",hourOfDay+":"+minute);
            delivaryTime=hourOfDay+ ":" +minute;*/

            hourOfDay=view.getCurrentHour();
            minute=view.getCurrentMinute();

            Log.d("chour",""+hourOfDay);
            Log.d("ctime",""+minute);

            Calendar datetime = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);


            if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                //it's after current
                int hour = hourOfDay % 12;
                //Toast.makeText(getApplicationContext(),String.format("%02d:%02d%s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm"),Toast.LENGTH_LONG).show();
                delivaryTime=String.format("%02d:%02d%s", hour == 0 ? 12 : hour,
                        minute, hourOfDay < 12 ? "am" : "pm");

                Log.d("delivaryTimeee",delivaryTime);



                Intent i=new Intent(Maincart.this,CheckoutActivity.class);
                startActivity(i);
                finish();
                /*btnPickStartTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                        minute, hourOfDay < 12 ? "am" : "pm"));*/
            } else {
                //it's before current'
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalidtime), Toast.LENGTH_LONG).show();
            }


           /* Calendar datetime = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);
            if (datetime.getTimeInMillis() < c.getTimeInMillis()) {
                Intent i=new Intent(Maincart.this,CheckoutActivity.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Please enter after current time",Toast.LENGTH_LONG).show();
            }*/
        }
    }

    /*private SlideDateTimeListener listener = new SlideDateTimeListener() {
        int difference;

        @Override
        public void onDateTimeSet(Date date) {



            Toast.makeText(Maincart.this, mFormatter.format(date), Toast.LENGTH_SHORT).show();
            Log.d("time",mFormatter.format(date));
            delivaryTime=mFormatter.format(date);


            Intent i=new Intent(Maincart.this,CheckoutActivity.class);
            startActivity(i);
            finish();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(Maincart.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };*/

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
