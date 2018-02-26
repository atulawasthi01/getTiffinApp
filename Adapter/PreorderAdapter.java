package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.yumtiff.mohitkumar.tiffin.Activity.TiffinDiscriptionActivity;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.Model.TdayGroup;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mohit.gupta on 2/9/2017.
 */

public class PreorderAdapter extends BaseExpandableListAdapter {
    public Context context;
    Button savechangeBtn;
    LinearLayout containerLl;
    ViewHolder holder = null;
    ArrayList<TdayGroup> ListTerbaru;
    ArrayList<ArrayList<TdayChild>> ListFoodChild;
    private int ii;
    boolean isClick=false;
    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    String[] multichildArr;
    int i;
    ArrayList<String> arrayList;
    String[] arr=new String[10];
    String quantity="0";
    TdayChild FoodChild;
    int cnt=1;
    int globPos;

    public static ArrayList<PreOrderData> newAL= new ArrayList<PreOrderData>();
    public static ArrayList<JSONObject> ChildListTiffin=new ArrayList<JSONObject>();
    public static ArrayList<ArrayList<TdayChild>> tdaylist;


    public PreorderAdapter(Context context, ArrayList<TdayGroup> ListTerbaru, ArrayList<ArrayList<TdayChild>> ListFoodChild) {
        this.context = context;
        this.ListTerbaru = ListTerbaru;
        this.ListFoodChild = ListFoodChild;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }


    @Override
    public TdayChild getChild(int groupPosition, int childPosition) {
        return ListFoodChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        FoodChild = getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pre_child_layout, null);

            holder = new ViewHolder();
            holder.titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.imgchildimgIv = (ImageView) convertView.findViewById(R.id.imgchildimg);
            //holder.selectBtn = (Button) convertView.findViewById(R.id.selectbtn);
            holder.seemoreBtn = (Button) convertView.findViewById(R.id.seemore);
            holder.checkselectChk = (CheckBox) convertView.findViewById(R.id.checkselect);
            //holder.checkselectChk.setVisibility(View.GONE);
            /*holder.quntity = (TextView)convertView.findViewById(R.id.quntityTxt);
            holder.addBtn = (Button)convertView.findViewById(R.id.addBtn);
            holder.subBtn = (Button)convertView.findViewById(R.id.subBtn);*/

            final Pair<Long, Long> tag = new Pair<Long, Long>(getGroupId(groupPosition), getChildId(groupPosition, childPosition));
            holder.checkselectChk.setTag(tag);
            holder.checkselectChk.setChecked(mCheckedItems.contains(tag));


           /* holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    globPos=childPosition;

                        Button btn = (Button) view;
                        FoodChild = (TdayChild) btn.getTag();


                        cnt++;
                        Toast.makeText(context,""+cnt,Toast.LENGTH_SHORT).show();
                        Log.d("cntt", "" + cnt);
                        holder.quntity.setText("" + cnt);
                        *//*FoodChild.setQunt(cnt);
                        Log.d("qut", "" + FoodChild.getQunt());
                        holder.quntity.setText("" + FoodChild.getQunt());*//*
                    }

            });
            holder.subBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Button btn = (Button) view;
                        FoodChild = (TdayChild) btn.getTag();
                        //cnt = Integer.valueOf(holder.quntity.getText().toString());
                        cnt--;
                        Toast.makeText(context,""+cnt,Toast.LENGTH_SHORT).show();
                        Log.d("cntt", "" + cnt);
                    holder.quntity.setText("" + cnt);
                        *//*if (cnt >= 0) {
                            FoodChild.setQunt(cnt);
                            holder.quntity.setText("" + FoodChild.getQunt());
                        } else {
                            FoodChild.setQunt(0);
                            holder.quntity.setText("0");
                        }*//*
                    }

            });*/

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titlefoodTv.setText(FoodChild.getDishName());
        holder.discripfoodTv.setText(FoodChild.getDishDescription());
        holder.pricefoodTv.setText("₹"+FoodChild.getDishPrice());

        Picasso.with(context)
                .load(FoodChild.getDishImage())
                .placeholder(R.drawable.thali)
                .error(R.drawable.thali)
                .into(holder.imgchildimgIv );

        Log.d("tdaylist",""+ListFoodChild.get(groupPosition).size());
        multichildArr = new String[ListFoodChild.get(groupPosition).size()];

        arrayList = new ArrayList<String>();
        Log.d("ListTerbaru",""+ListTerbaru.size());


        if (groupPosition==ListTerbaru.size()-1) {
            holder.seemoreBtn.setVisibility(View.VISIBLE);
            holder.checkselectChk.setVisibility(View.INVISIBLE);
        }
        else {
            holder.seemoreBtn.setVisibility(View.INVISIBLE);
            holder.checkselectChk.setVisibility(View.VISIBLE);
        }





        holder.seemoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                /*TdayChild FoodChild = getChild(groupPosition, childPosition);


                String dishimgStr,dishnameStr,dishprcStr;

                dishnameStr=FoodChild.getDishName();
                dishprcStr=FoodChild.getDishPrice();
                dishimgStr=FoodChild.getDishImage();




                Intent i1=new Intent(context, TiffinDiscriptionActivity.class);
                i1.putExtra("dishName",dishnameStr);
                i1.putExtra("dishImage",dishimgStr);
                i1.putExtra("dishPrice",dishprcStr);
                //i1.putExtra("tiffinlist",ChildListTiffin);
                context.startActivity(i1);*/

                TdayChild FoodChild = getChild(groupPosition, childPosition);

                String dishimgStr,dishnameStr,dishprcStr;
                dishnameStr=FoodChild.getDishName();
                dishprcStr=FoodChild.getDishPrice();
                dishimgStr=FoodChild.getDishImage();

                Intent i1=new Intent(context, TiffinDiscriptionActivity.class);
                i1.putExtra("dishName",dishnameStr);
                i1.putExtra("dishImage",dishimgStr);
                i1.putExtra("dishPrice",dishprcStr);
                context.startActivity(i1);

                TiffinDiscriptionActivity.tiffinObject = ChildListTiffin.get(childPosition);


            }
        });

        holder.checkselectChk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                TdayChild tdayChild=getChild(groupPosition,childPosition);
                final CheckBox cb = (CheckBox) v;
                final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();
                if (cb.isChecked()) {
                    mCheckedItems.add(tag);
                    //Toast.makeText(context,"Groupid"+groupPosition+"Childid"+childPosition,Toast.LENGTH_SHORT).show();
                    //HomeActivity.cartIv.setClickable(false);

                    String dishId=tdayChild.getId();
                    String dishname=tdayChild.getDishName();
                    String dishImg=tdayChild.getDishImage();
                    String dishPrice=tdayChild.getDishPrice();
                    String catId=tdayChild.getCategoryId();
                    String category=tdayChild.getCategory();
                    String menuId=tdayChild.getMenu();
                    String dishDiscription=tdayChild.getDishDescription();
                    Log.d("categoryyyy",category);
                   /* dishNm=dishname;
                    dishidd=dishId;
                    dishimgg=dishImg;
                    dishPricee=dishPrice;
                    catIdd=catId;
                    menuIdd=menuId;*/
                    //Toast.makeText(context,dishNm,Toast.LENGTH_SHORT).show();
                    PreOrderData preOrderData=new PreOrderData();
                    preOrderData.setDishId(dishId);
                    preOrderData.setDishName(dishname);
                    preOrderData.setDishImage(dishImg);
                    preOrderData.setDishPrice(dishPrice);
                    preOrderData.setDishdiscription(dishDiscription);
                    preOrderData.setQuanitity("1");
                    //preOrderData.setQuanitity(quantity);
                    preOrderData.setCategory(category);
                    preOrderData.setCatId(catId);
                    preOrderData.setMenuId(menuId);
                        //multichildArr[ListFoodChild.get(groupPosition).size()]=dishname;


                       // Log.d("multichildArr",multichildArr[ListFoodChild.get(groupPosition).size()].toString());

                    if (category.equalsIgnoreCase("Breakfast")) {
                        Global.prebreakfastList.add(preOrderData);
                        newAL.add(preOrderData);
                        Log.d("prebreakfastList",Global.prebreakfastList.toString());
                    }
                    else if (category.equalsIgnoreCase("Lunch")) {
                        Global.prelunchList.add(preOrderData);
                        newAL.add(preOrderData);
                        Log.d("prelunchList",Global.prelunchList.toString());
                    }
                    else if (category.equalsIgnoreCase("Dinner")) {
                        Global.predinnerList.add(preOrderData);
                        newAL.add(preOrderData);
                        Log.d("predinnerList",Global.predinnerList.toString());

                    }
                    else if (category.equalsIgnoreCase("Mini Meals")) {
                        Global.preminimealList.add(preOrderData);
                        newAL.add(preOrderData);
                        Log.d("preminimealList",Global.preminimealList.toString());
                    }






                    /*newAL.add(dishNm);
                    newAL.add(dishidd);
                    newAL.add(dishimgg);*/
                    Log.d("newAL",newAL.toString());
                    /*int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                            Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                    HomeActivity.countttTv.setText(""+listSize);*/





                    //Toast.makeText(context,multichildArr[i],Toast.LENGTH_SHORT).show();

                } else {
                    mCheckedItems.remove(tag);
                    newAL.remove(tdayChild);
                    /*int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size()+
                            Global.prebreakfastList.size()+Global.prelunchList.size()+Global.predinnerList.size()+Global.preminimealList.size();
                    HomeActivity.countttTv.setText(""+listSize);*/
                }
            }
        });




        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        /*if (ListFoodChild.size()==0) {
            Toast.makeText(context,"Child Items Empty",Toast.LENGTH_LONG).show();
            return 0;
        }
        else {
            Toast.makeText(context,"Child Items not Empty",Toast.LENGTH_LONG).show();
            Log.d("ListFoodChild", ""+ListFoodChild.size());
            return ListFoodChild.get(groupPosition).size();
        }*/

        try {
            ii=ListFoodChild.get(groupPosition).size();
        }
        catch (IndexOutOfBoundsException e) {

            e.printStackTrace();

        }

        return ii;
    }

    @Override
    public TdayGroup getGroup(int groupPosition) {
        return ListTerbaru.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return ListTerbaru.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ListTerbaru.get(groupPosition).hashCode();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        TdayGroup FoodGroup = (TdayGroup) getGroup(groupPosition);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pre_group_layout, null);

            holder = new ViewHolder();
            holder.mainLayout = (RelativeLayout)convertView.findViewById(R.id.mainLayout);
            holder.dishNameTxt = (TextView) convertView.findViewById(R.id.dishNameTxt);
            holder.starttimeTv = (TextView) convertView.findViewById(R.id.starttime);
            holder.endtimeTv = (TextView) convertView.findViewById(R.id.endtime);
            holder.starttimetxt = (TextView) convertView.findViewById(R.id.starttimetxt);
            holder.endtimetxt = (TextView) convertView.findViewById(R.id.endtimetxt);
            holder.imggrpIv = (ImageView) convertView.findViewById(R.id.imggrp);


            //holder.selectBtn.setVisibility(View.VISIBLE);
           /* holder.checkselectChk = (CheckBox) convertView.findViewById(R.id.checkselect);
            holder.checkselectChk.setVisibility(View.GONE);*/



            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mainLayout.setAlpha(.70f);

        holder.dishNameTxt.setText(FoodGroup.getCategory());
        holder.starttimeTv.setText(FoodGroup.getStartTime());
        holder.endtimeTv.setText(FoodGroup.getEndTime());

        Picasso.with(context)
                .load(FoodGroup.getDishImage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.imggrpIv);

        if (groupPosition==ListTerbaru.size()-1) {
            holder.starttimeTv.setVisibility(View.INVISIBLE);
            holder.endtimeTv.setVisibility(View.INVISIBLE);
            holder.starttimetxt.setVisibility(View.INVISIBLE);
            holder.endtimetxt.setVisibility(View.INVISIBLE);
        }
        else {
            holder.starttimeTv.setVisibility(View.VISIBLE);
            holder.endtimeTv.setVisibility(View.VISIBLE);
            holder.starttimetxt.setVisibility(View.VISIBLE);
            holder.endtimetxt.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }



    public static class ViewHolder {
        RelativeLayout mainLayout;
        TextView dishNameTxt, titlefoodTv,discripfoodTv,pricefoodTv,starttimeTv,endtimeTv,starttimetxt,endtimetxt;
        RatingBar ratingBar;
        ImageView imggrpIv,imgchildimgIv;
        public  CheckBox checkselectChk;
        Button addBtn,subBtn,seemoreBtn;

    }
}

