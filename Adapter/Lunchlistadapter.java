package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;


import com.yumtiff.mohitkumar.tiffin.Model.Lunch;
import com.yumtiff.mohitkumar.tiffin.R;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by rajat.gupta on 2/16/2017.
 */
public class Lunchlistadapter extends ArrayAdapter<Lunch> {
    Activity context;
    List<Lunch> lunchList;
    Lunch lunch;
    Holder holder;
    LayoutInflater inflater;
    com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    //com.example.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    ImageLoader loader;

    public Lunchlistadapter(Activity context, List<Lunch> lunchList) {
        super(context, 0, lunchList);
        this.lunchList = lunchList;
        this.context = context;
        imageLoader= new com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader(context);
        this.loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return lunchList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        lunch = getItem(position);

        if (convertView == null) {
            //Toast.makeText(context,""+lunchList.size(),Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_layout, parent, false);
            holder = new Holder();
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        lunch = lunchList.get(position);


        holder.titlefoodTv.setText(lunch.getDishname());
        holder.discripfoodTv.setText(lunch.getDishdescription());
        holder.pricefoodTv.setText("₹"+lunch.getPrice());

        ImageLoader.getInstance().displayImage(lunch.getDishimage(), holder.foodImg);

       /* Picasso.with(context)
                .load(breakFast.getDishimage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.foodImg);*/


        return convertView;

    }

    public static class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;
    }
}