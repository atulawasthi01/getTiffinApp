package com.yumtiff.mohitkumar.tiffin.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yumtiff.mohitkumar.tiffin.Adapter.PreOrderListAdapter;
import com.yumtiff.mohitkumar.tiffin.Adapter.PreorderAdapter;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.ProductClickListener;
import com.yumtiff.mohitkumar.tiffin.ProductDataSet;
import com.yumtiff.mohitkumar.tiffin.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PreOrderSelectListActivity extends AppCompatActivity  {
    TextView titleTv, timetxtTv;
    ImageView backIv;
    SwipeMenuListView preorderselListView;
    PreOrderListAdapter preOrderListAdapter;
    ArrayList<PreOrderData> preorderdataList;
    DialogFragment dialogfragment;
    TimePickerDialog timepickerdialog1;
    Button selecttimeBtn, proceedpayBtn;
    int Chour,Cminute;
    public static String tomorrowAsString,deliveryTimee,quantityStr,menuStr;
    int procorderPrice,totalproceedPrice;
    public static String predishIdStr,catidd;
    int totalprice=0;
    SharedPreferences dishpriceSp;
    public static final String dishpricePrefrence = "price";
    String tomorrowTime,tomorrowDate;
    Products products;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order_select_list);
        titleTv = (TextView) findViewById(R.id.title);
        backIv = (ImageView) findViewById(R.id.back);
        preorderselListView = (SwipeMenuListView) findViewById(R.id.preordersellist);
        timetxtTv = (TextView) findViewById(R.id.timetxt);
        selecttimeBtn = (Button) findViewById(R.id.selecttimeBtn);
        proceedpayBtn = (Button) findViewById(R.id.proceedpayBtn);
        titleTv.setText(getResources().getString(R.string.preorderlist));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PreOrderSelectListActivity.this, HomeActivity.class);
                startActivity(i);
                finish();

            }
        });

      /*  preorderdataList=new ArrayList<PreOrderData>();
        preorderdataList=

        Log.d("preorderlist", preorderdataList.toString());*/

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tomorrowAsString = dateFormat.format(tomorrow);
        Log.d("tomorrowAsString",tomorrowAsString);

        selecttimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogfragment = new TimePickerToday();

                dialogfragment.show(getFragmentManager(), "Time Picker with Theme 3");
            }
        });



        preOrderListAdapter = new PreOrderListAdapter(this, productClickListener,PreorderAdapter.newAL);
        preorderselListView.setAdapter(preOrderListAdapter);
        preOrderListAdapter.notifyDataSetChanged();

        initControls();




        for (int i=0;i<PreorderAdapter.newAL.size();i++) {
            //PreOrderData preOrderData=PreorderAdapter.newAL.get(i).getDishPrice();
            String preorderPrice=PreorderAdapter.newAL.get(i).getDishPrice();
            String predishId=PreorderAdapter.newAL.get(i).getDishId();
            String dishPrice=PreorderAdapter.newAL.get(i).getDishPrice();
            String quantity=PreorderAdapter.newAL.get(i).getQuanitity();
            String catId=PreorderAdapter.newAL.get(i).getCatId();
            String menuId=PreorderAdapter.newAL.get(i).getMenuId();
            Log.d("predishId",predishId);
            int dPrice= Integer.parseInt(dishPrice);
            totalprice=totalprice+dPrice;
            String prettlPrice= String.valueOf(totalprice);

            if (i==0) {
                predishIdStr=predishId;
                catidd=catId;
                quantityStr=quantity;
                menuStr=menuId;

            }
            else {
                predishIdStr = predishIdStr+","+predishId;
                catidd=catidd+","+catId;
                quantityStr=quantityStr+","+quantity;
                menuStr=menuStr+","+menuId;
            }

            Log.d("predishIdStrrrr",predishIdStr);
            Log.d("catidd",catidd);
            Log.d("dishPrice",dishPrice);
            Log.d("totalpriceeee",""+totalprice);
            procorderPrice= Integer.parseInt(preorderPrice);
            totalproceedPrice=totalproceedPrice+procorderPrice;



            dishpriceSp = getApplicationContext().getSharedPreferences(dishpricePrefrence, MODE_PRIVATE);
            SharedPreferences.Editor editorProfile = dishpriceSp.edit();
            editorProfile.putString("predishIdStr",predishIdStr);
            editorProfile.putString("catidd",catidd);
            editorProfile.putString("totalprice",prettlPrice);
            editorProfile.putString("quantity",quantityStr);
            editorProfile.putString("menuId",menuStr);
       /*   editorProfile.putString("deliveryDatePre",tomorrowAsString);
            editorProfile.putString("deliveryTimePre",deliveryTimee);*/
            editorProfile.commit();
            editorProfile.apply();

            //Toast.makeText(getApplicationContext(),preorderPrice,Toast.LENGTH_SHORT).show();

        }

        /*Log.d("preorderPrice",""+totalproceedPrice);
        Log.d("predishIdStr",predishIdStr);*/



        proceedpayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PreOrderSelectListActivity.this,PreCheckoutActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void initControls() {

        preorderselListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);



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
        preorderselListView.setMenuCreator(creator);

        preorderselListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {

                    case 0:
                        PreorderAdapter.newAL.remove(position);
                        preOrderListAdapter.notifyDataSetChanged();
                        Toast.makeText(PreOrderSelectListActivity.this,"Item deleted",Toast.LENGTH_SHORT).show();

                        break;
                }
                return true;
            }
        });

        //mListView

        preorderselListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {

            }

            @Override
            public void onMenuClose(int position) {

            }
        });

        preorderselListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    public final ProductClickListener productClickListener = new ProductClickListener() {
        @Override
        public void onMinusClick(PreOrderData preOrderData) {
            int i = PreorderAdapter.newAL.indexOf(preOrderData);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            PreOrderData updatedProduct = new PreOrderData((preOrderData.qunt - 1));
            PreorderAdapter.newAL.remove(preOrderData);
            PreorderAdapter.newAL.add(i, updatedProduct);
            preOrderListAdapter.notifyDataSetChanged();
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
            preOrderListAdapter.notifyDataSetChanged();
        }
    };




    @SuppressLint("validFragment")
    public class TimePickerToday extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {



            timepickerdialog1 = new TimePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_DARK, this, Chour, Cminute, false);

            return timepickerdialog1;
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            Calendar datetime = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            int hour = hourOfDay % 12;
            deliveryTimee=String.format("%02d:%02d%s", hour == 0 ? 12 : hour,
                    minute, hourOfDay < 12 ? "am" : "pm");

            Log.d("todaytime:",hourOfDay+":"+minute);
            timetxtTv.setText("Tomorrow Time:-"+tomorrowAsString+" "+deliveryTimee);
            //deliveryTimee=""+hourOfDay+":"+minute+"";


        }
    }

    private static class Products implements ProductDataSet {

        /*private final List<Product> productList;

        Products(List<PreOrderData> productList) {
            this.productList = productList;
        }*/

        @Override
        public int size() {
            return PreorderAdapter.newAL.size();
        }

        @Override
        public PreOrderData get(int position) {
            return PreorderAdapter.newAL.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }

        public void removeOneFrom(PreOrderData product) {
            int i = PreorderAdapter.newAL.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            PreOrderData updatedProduct = new PreOrderData((product.qunt - 1));
            PreorderAdapter.newAL.remove(product);
            PreorderAdapter.newAL.add(i, updatedProduct);
        }

        public void addOneTo(PreOrderData product) {
            int i = PreorderAdapter.newAL.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            PreOrderData updatedProduct = new PreOrderData((product.qunt + 1));
            PreorderAdapter.newAL.remove(product);
            PreorderAdapter.newAL.add(i, updatedProduct);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(PreOrderSelectListActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }
}
