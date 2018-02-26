package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.Dinner;
import com.yumtiff.mohitkumar.tiffin.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by rajat.gupta on 2/16/2017.
 */
public class Dinnerlistadapter extends ArrayAdapter<Dinner> {
    Activity context;
    List<Dinner> dinnerList;
    Dinner dinner;
    Lunchlistadapter.Holder holder;
    LayoutInflater inflater;
    com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    //com.example.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    ImageLoader loader;

    public Dinnerlistadapter(Activity context, List<Dinner> dinnerList) {
        super(context, 0, dinnerList);
        this.dinnerList = dinnerList;
        this.context = context;
        imageLoader = new com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader(context);
        this.loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return dinnerList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dinner = getItem(position);

        if (convertView == null) {
            //Toast.makeText(context, "" + dinnerList.size(), Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_layout, parent, false);
            holder = new Lunchlistadapter.Holder();
            holder.titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView) convertView.findViewById(R.id.catoneimg);


            convertView.setTag(holder);
        } else {
            holder = (Lunchlistadapter.Holder) convertView.getTag();
        }
        dinner = dinnerList.get(position);



        holder.titlefoodTv.setText(dinner.getDishname());
        holder.discripfoodTv.setText(dinner.getDishdescription());
        holder.pricefoodTv.setText("₹"+dinner.getPrice());

        ImageLoader.getInstance().displayImage(dinner.getDishimage(), holder.foodImg);

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