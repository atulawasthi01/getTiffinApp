package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mohit.gupta on 4/8/2017.
 */

public class TiffinDishAdapter extends ArrayAdapter<TdayChild> {
    Activity context;
    List<TdayChild> breakFastList;
    TdayChild tdayChild;
    Breakfastadapter.Holder holder;
    LayoutInflater inflater;


    public TiffinDishAdapter(Activity context, List<TdayChild> breakFastList) {
        super(context, 0, breakFastList);
        this.breakFastList = breakFastList;
        this.context = context;

    }



    @Override
    public int getCount() {
        return breakFastList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tdayChild = getItem(position);

        if (convertView == null) {
            //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_layout, parent, false);
            holder = new Breakfastadapter.Holder();
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);
            holder.checkbox = (CheckBox)convertView.findViewById(R.id.checkbox);
            holder.checkbox.setVisibility(View.GONE);


            convertView.setTag(holder);
        } else {
            holder = (Breakfastadapter.Holder) convertView.getTag();
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


        return convertView;

    }

    public static class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;
        CheckBox checkbox;
    }
}
