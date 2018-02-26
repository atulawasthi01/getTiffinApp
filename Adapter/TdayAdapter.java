package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
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

import com.yumtiff.mohitkumar.tiffin.Activity.Maincart;
import com.yumtiff.mohitkumar.tiffin.Fragment.TodaysMenu;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.Model.TdayGroup;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mohit.gupta on 4/6/2017.
 */

public class TdayAdapter extends BaseExpandableListAdapter {

    public Context context;
    Button savechangeBtn;
    LinearLayout containerLl;
    ArrayList<TdayGroup> ListTerbaru;
    ArrayList<ArrayList<TdayChild>> ListFoodChild;
    private int ii;
    boolean isClick=false;
    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    String[] multichildArr;
    ViewHolder holder = null;
    //public static List<PreOrderData> newAL= new ArrayList<PreOrderData>();




    public TdayAdapter(Context context, ArrayList<TdayGroup> ListTerbaru, ArrayList<ArrayList<TdayChild>> ListFoodChild) {
        this.context = context;
        this.ListTerbaru = ListTerbaru;
        this.ListFoodChild = ListFoodChild;


//      this.count=ListTerbaru.size();
//      this.count=ListFoodChild.size();
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

        TdayChild FoodChild = getChild(groupPosition, childPosition);


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
            holder.seemoreBtn  = (Button) convertView.findViewById(R.id.seemore);
            holder.checkselectChk = (CheckBox) convertView.findViewById(R.id.checkselect);
            holder.addBtn = (Button)convertView.findViewById(R.id.addBtn);
            holder.subBtn = (Button)convertView.findViewById(R.id.subBtn);
            holder.quantity = (TextView)convertView.findViewById(R.id.quntityTxt);
            //holder.checkselectChk.setVisibility(View.GONE);
            holder.seemoreBtn.setVisibility(View.GONE);

            final Pair<Long, Long> tag = new Pair<Long, Long>(getGroupId(groupPosition), getChildId(groupPosition, childPosition));
            holder.checkselectChk.setTag(tag);
            holder.checkselectChk.setChecked(mCheckedItems.contains(tag));



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

        holder.checkselectChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TdayChild tdayChild=getChild(groupPosition,childPosition);
                TdayGroup tdayGroup=getGroup(groupPosition);
                final CheckBox cb = (CheckBox) v;
                final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();
                if (cb.isChecked()) {
                    mCheckedItems.add(tag);
                    //HomeActivity.cartIv.setClickable(true);
                    //Toast.makeText(context,"Groupid"+groupPosition+"Childid"+childPosition,Toast.LENGTH_SHORT).show();
                    /*cb.setEnabled(false);
                    cb.setChecked(true);*/

                    String dishId=tdayChild.getId();
                    String dishname=tdayChild.getDishName();
                    String dishImg=tdayChild.getDishImage();
                    String dishPrice=tdayChild.getDishPrice();
                    String catId=tdayChild.getCategoryId();
                    String discountId=tdayChild.getDiscountId();
                    String menuId=tdayChild.getMenu();
                    String category=tdayChild.getCategory();
                    String dishDiscription=tdayChild.getDishDescription();
                    String startTime=tdayGroup.getStartTime();
                    String endTime=tdayGroup.getEndTime();

                    PreOrderData preOrderData=new PreOrderData();
                    preOrderData.setDishId(dishId);
                    preOrderData.setDishName(dishname);
                    preOrderData.setDishImage(dishImg);
                    preOrderData.setDishPrice(dishPrice);
                    preOrderData.setDishdiscription(dishDiscription);
                    preOrderData.setQuanitity("1");
                    //preOrderData.setQuanitity(quantity);
                    preOrderData.setCatId(catId);
                    preOrderData.setCategory(category);
                    preOrderData.setMenuId(menuId);
                    preOrderData.setStartTime(startTime);
                    preOrderData.setEndTime(endTime);

                    if (category.equalsIgnoreCase("Lunch")) {
                        Global.lunchList.add(preOrderData);
                        Global.newAL.add(preOrderData);
                        Log.d("lunchList",Global.lunchList.toString());
                    }
                    else if (category.equalsIgnoreCase("Dinner")) {
                        Global.dinnerList.add(preOrderData);
                        Global.newAL.add(preOrderData);
                        Log.d("dinnerList",Global.dinnerList.toString());
                    }
                    else if (category.equalsIgnoreCase("Mini Meals")) {
                        Global.minimealList.add(preOrderData);
                        Global.newAL.add(preOrderData);
                        Log.d("minimealList",Global.minimealList.toString());
                    }



                    //Global.newAL.add(preOrderData);


                    Log.d("newAL",""+Global.newAL.size());
                    int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size();
                    HomeActivity.countttTv.setText(""+listSize);





                    //Toast.makeText(context,multichildArr[i],Toast.LENGTH_SHORT).show();
                } else {
                    mCheckedItems.remove(tag);
                    Global.newAL.remove(tdayChild);
                    int newAlsize=Global.newAL.size()-1;
                    HomeActivity.countttTv.setText(""+newAlsize);
                    //Toast.makeText(context,"not select",Toast.LENGTH_SHORT).show();
                    int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size();
                    HomeActivity.countttTv.setText(""+listSize);
                }

            }
        });

       /* holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TodaysMenu.quantityArr.set(position,TodaysMenu.quantityArr.get(position) + 1);
                holder.quantity.setText(String.valueOf(TodaysMenu.quantityArr.get(position)));
                Log.d("plusclick",""+TodaysMenu.quantityArr.get(position));
                Log.d("plusposition",""+position);

            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TodaysMenu.quantityArr.set(position,TodaysMenu.quantityArr.get(position) - 1);
                holder.quantity.setText(String.valueOf(TodaysMenu.quantityArr.get(position)));

            }
        });*/



        /*Log.d("tdaylist",""+ListFoodChild.get(groupPosition).size());

        multichildArr = new String[ListFoodChild.get(groupPosition).size()];



        holder.checkselectChk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                TdayChild tdayChild=getChild(groupPosition,childPosition);
                final CheckBox cb = (CheckBox) v;
                final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();
                if (cb.isChecked()) {
                    mCheckedItems.add(tag);
                    //Toast.makeText(context,"Groupid"+groupPosition+"Childid"+childPosition,Toast.LENGTH_SHORT).show();

                        String childId=tdayChild.getId();
                        *//*multichildArr[ListFoodChild.get(groupPosition).size()]=childId;
                        Toast.makeText(context,childId,Toast.LENGTH_SHORT).show();
                        Log.d("multichildArr",multichildArr[ListFoodChild.get(groupPosition).size()].toString());*//*

                    //Toast.makeText(context,multichildArr[i],Toast.LENGTH_SHORT).show();

                } else {
                    mCheckedItems.remove(tag);
                    Toast.makeText(context,"not select",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*if ( holder.checkselectChk.isChecked()) {
            Toast.makeText(context,"selected",Toast.LENGTH_SHORT).show();
        }*/

      /*  holder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=true;
            }
        });

        if (isClick==true) {
            Toast.makeText(context,"selected",Toast.LENGTH_SHORT).show();
        }*/





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
        TextView dishNameTxt, titlefoodTv,discripfoodTv,pricefoodTv,starttimeTv,endtimeTv,quantity;
        RatingBar ratingBar;
        ImageView imggrpIv,imgchildimgIv;
        public static CheckBox checkselectChk;
        Button seemoreBtn,addBtn,subBtn;

    }
}
