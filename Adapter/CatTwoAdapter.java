package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.CatTwo;
import com.yumtiff.mohitkumar.tiffin.Model.Lunch;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.List;

/**
 * Created by mohit.gupta on 1/28/2017.
 */

public class CatTwoAdapter extends BaseAdapter {
    Context context;
    List<Lunch> catTwoList;
    LayoutInflater inflater;
    Lunch catTwo;

    public CatTwoAdapter(Context context, List<CatTwo> catTwoList) {
        this.context=context;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return catTwoList.size();
    }

    @Override
    public Object getItem(int position) {
        return catTwoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         Holder holder = new Holder();

        if (convertView==null) {
            convertView = inflater.inflate(R.layout.category_layout, null);
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);

            convertView.setTag(holder);
        }
        else {

            holder = (Holder) convertView.getTag();
        }

        catTwo = catTwoList.get(position);

        holder.titlefoodTv.setText(catTwo.getTitle());
        holder.discripfoodTv.setText(catTwo.getDiscription());
        holder.pricefoodTv.setText("₹"+catTwo.getPrice());
        holder.foodImg.setImageResource(catTwo.getImg());


        return convertView;
    }



    class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;
    }
}
