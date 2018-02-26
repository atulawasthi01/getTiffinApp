package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Model.Child;
import com.yumtiff.mohitkumar.tiffin.Model.Group;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mohit.gupta on 3/31/2017.
 */

public class HistAdapter extends BaseExpandableListAdapter {
    Context context;
    Button savechangeBtn;
    LinearLayout containerLl;



    ArrayList<Group> ListTerbaru;
    ArrayList<ArrayList<Child>> ListFoodChild;
    private int ii;




    public HistAdapter(Context context, ArrayList<Group> ListTerbaru, ArrayList<ArrayList<Child>> ListFoodChild) {
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
    public Child getChild(int groupPosition, int childPosition) {
        return ListFoodChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Child FoodChild = getChild(groupPosition, childPosition);
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pre_child_layout, null);

            holder = new ViewHolder();
            holder.titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.imgchildimgIv = (ImageView) convertView.findViewById(R.id.imgchildimg);
            /*holder.checkselectChk = (CheckBox) convertView.findViewById(R.id.checkselect);
            holder.checkselectChk.setVisibility(View.GONE);*/



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







        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (ListFoodChild.size()==0) {
            Toast.makeText(context,"Child Items Empty",Toast.LENGTH_LONG).show();
            return 0;
        }
        else {
            Toast.makeText(context,"Child Items not Empty",Toast.LENGTH_LONG).show();
            return ListFoodChild.get(groupPosition).size();
        }
           //return ListFoodChild.get(groupPosition).size();
        /*try {
            ii=ListFoodChild.get(groupPosition).size();
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return ii;*/
    }

    @Override
    public Group getGroup(int groupPosition) {
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

        Group FoodGroup = (Group) getGroup(groupPosition);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pre_group_layout, null);

            holder = new ViewHolder();
            holder.ordernotv = (TextView) convertView.findViewById(R.id.orderno);
            holder.orderdatetv = (TextView) convertView.findViewById(R.id.orderdate);
            holder.deliverydatetv = (TextView) convertView.findViewById(R.id.deldate);
            holder.totalamounttv = (TextView) convertView.findViewById(R.id.totalamunt);
            holder.imggrpIv = (ImageView) convertView.findViewById(R.id.imggrp);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ordernotv.setText(FoodGroup.getOrderSequenceNumber());
        holder.orderdatetv.setText(FoodGroup.getDeliveryDate());
        holder.deliverydatetv.setText(FoodGroup.getDeliveryDate());


        Picasso.with(context)
                .load(FoodGroup.getId())
                .placeholder(R.drawable.thali)
                .error(R.drawable.thali)
                .into(holder.imgchildimgIv );





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


    static class ViewHolder {
        TextView ordernotv, orderdatetv, deliverydatetv, totalamounttv, titlefoodTv,discripfoodTv,pricefoodTv;
        RatingBar ratingBar;
        ImageView imggrpIv,imgchildimgIv;
        CheckBox checkselectChk;


    }
}