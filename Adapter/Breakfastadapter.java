package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.utils.L;
import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rajat.gupta on 2/16/2017.
 */
public class Breakfastadapter extends ArrayAdapter<TdayChild> {
    Activity context;
    List<TdayChild> breakFastList;
    TdayChild tdayChild;
    Holder holder;
    LayoutInflater inflater;
    //public static ArrayList<PreOrderData> newAL= new ArrayList<PreOrderData>();
    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    int indexPos;
    PreOrderData preOrderData;


    public Breakfastadapter(Activity context, List<TdayChild> breakFastList) {
        super(context, 0, breakFastList);
        this.breakFastList = breakFastList;
        this.context = context;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return breakFastList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        tdayChild = getItem(position);

        if (convertView == null) {
          //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_layout, parent, false);
            holder = new Holder();
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);




            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        tdayChild = breakFastList.get(position);

        /*Log.d("dishnamerow",breakFast.getDishname());
        Log.d("dishdiscriptionrow",breakFast.getDishdescription());
        Log.d("dishpricefoodrow",breakFast.getPrice());*/

        holder.titlefoodTv.setText(tdayChild.getDishName());
        holder.discripfoodTv.setText(tdayChild.getDishDescription());
        holder.pricefoodTv.setText("₹"+tdayChild.getDishPrice());



        Picasso.with(context)
                .load(tdayChild.getDishImage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.foodImg);

        /*final Pair<Long, Long> tag = new Pair<Long, Long>(getGroupId(groupPosition), getChildId(groupPosition, childPosition));
        holder.checkbox.setTag(tag);
        holder.checkbox.setChecked(mCheckedItems.contains(tag));*/






       /* holder.checkbox.setTag(position);

        if (position==selected_pos) {
            holder.checkbox.setChecked(true);
        }
        else {
            holder.checkbox.setChecked(false);
        }*/

       /* Global.alcarteList.add(preOrderData);
        Log.d("alcarteListtttt",Global.alcarteList.toString());*/

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexPos=position;
                TdayChild tdayChild = breakFastList.get(indexPos);

                final CheckBox cb = (CheckBox) v;
                final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();
                if (cb.isChecked()) {
                    mCheckedItems.add(tag);
                    //HomeActivity.cartIv.setClickable(true);
                    //Toast.makeText(context,"Groupid"+groupPosition+"Childid"+childPosition,Toast.LENGTH_SHORT).show();
                    //holder.checkbox.setTag(position);

                    /*cb.setEnabled(false);
                    cb.setChecked(true);*/
                    //sba.put(position, true);

                    String dishId = tdayChild.getId();
                    String dishname = tdayChild.getDishName();
                    String dishImg = tdayChild.getDishImage();
                    String dishPrice = tdayChild.getDishPrice();
                    String catId = tdayChild.getCategoryId();
                    String menuId = tdayChild.getMenu();
                    String dishDiscription = tdayChild.getDishDescription();
                    String menu= tdayChild.getMenu();

                    preOrderData = new PreOrderData();
                    preOrderData.setDishId(dishId);
                    preOrderData.setDishName(dishname);
                    preOrderData.setDishImage(dishImg);
                    preOrderData.setDishPrice(dishPrice);
                    preOrderData.setDishdiscription(dishDiscription);
                    preOrderData.setQuanitity("1");
                    //preOrderData.setQuanitity(quantity);
                    preOrderData.setCategory(menu);
                    preOrderData.setCatId(catId);
                    preOrderData.setMenuId(menuId);
                    //multichildArr[ListFoodChild.get(groupPosition).size()]=dishname;


                    // Log.d("multichildArr",multichildArr[ListFoodChild.get(groupPosition).size()].toString());


                    Global.alcarteList.add(preOrderData);

                    /*if (menu.equalsIgnoreCase("alkarte")) {
                        Global.alcarteList.add(preOrderData);
                        Log.d("alcarteList",Global.alcarteList.toString());
                    }
                    else if (menu.equalsIgnoreCase("addons")) {
                        Global.addonsList.add(preOrderData);
                        Log.d("addonsList",Global.addonsList.toString());
                    }*/


                    Global.newAL.add(preOrderData);
                    int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size();
                    HomeActivity.countttTv.setText(""+listSize);





                    //Toast.makeText(context,multichildArr[i],Toast.LENGTH_SHORT).show();

                } else  {
                    mCheckedItems.remove(tag);
                    Global.newAL.remove(tdayChild);

                    int listSize = Global.lunchList.size()+Global.dinnerList.size()+Global.minimealList.size()+Global.alcarteList.size();
                    HomeActivity.countttTv.setText(""+listSize);


                }

                notifyDataSetChanged();
            }
        });


        return convertView;

    }

    public static class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;
        CheckBox checkbox;
    }
}