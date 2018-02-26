package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.Veg;
import com.yumtiff.mohitkumar.tiffin.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajat.gupta on 2/17/2017.
 */

public class VegListadapter extends ArrayAdapter<Veg> {
    Activity context;
    List<Veg> veglist;
    Veg veg;
    VegListadapter.Holder holder;
    LayoutInflater inflater;
    com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    //com.example.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    ImageLoader loader;

    public VegListadapter(Activity context, List<Veg> veglist) {
        super(context, 0, veglist);
        this.veglist = veglist;
        this.context = context;
        imageLoader = new com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader(context);
        this.loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return veglist.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        veg = getItem(position);

        if (convertView == null) {
            //Toast.makeText(context,""+lunchList.size(),Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_layout, parent, false);
            holder = new Holder();
            holder.titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView) convertView.findViewById(R.id.catoneimg);


            convertView.setTag(holder);
        } else {
            holder = (VegListadapter.Holder) convertView.getTag();
        }
        veg = veglist.get(position);


        holder.titlefoodTv.setText(veg.getDishname());
        holder.discripfoodTv.setText(veg.getDishdescription());
        holder.pricefoodTv.setText(veg.getPrice());

        ImageLoader.getInstance().displayImage(veg.getDishimage(), holder.foodImg);

        Picasso.with(context)
                .load(veg.getDishimage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.foodImg);


        return convertView;

    }

    public static class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;
    }
}