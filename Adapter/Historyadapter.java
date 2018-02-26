package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.yumtiff.mohitkumar.tiffin.Model.History;
import com.yumtiff.mohitkumar.tiffin.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.List;




public class Historyadapter extends ArrayAdapter<History> {


    Activity context;
    List<History> historyList;
    History history;
    Historyadapter.Holder holder;
    LayoutInflater inflater;
    com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    //com.example.mohitkumar.tiffin.ImageLoader.ImageLoader imageLoader;
    ImageLoader loader;

    public Historyadapter(Response.Listener<String> context, List<History> historyList) {
        super((Context) context, 0, historyList);
        this.historyList = historyList;
        this.context = (Activity) context;
        imageLoader= new com.yumtiff.mohitkumar.tiffin.ImageLoader.ImageLoader((Context) context);
        this.loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault((Context) context));
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        history = getItem(position);

        if (convertView == null) {
            //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_layout, parent, false);
            holder = new Historyadapter.Holder();
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);


            convertView.setTag(holder);
        } else {
            holder = (Historyadapter.Holder) convertView.getTag();
        }

        history = historyList.get(position);

 Log.d("dishnamerow",history.getDishname());
        Log.d("dishdiscriptionrow",history.getDishdescription());
        Log.d("dishpricefoodrow",history.getDishprice());


        holder.titlefoodTv.setText(history.getDishname());
        holder.discripfoodTv.setText(history.getDishdescription());
        holder.pricefoodTv.setText(history.getDishprice());

        ImageLoader.getInstance().displayImage(history.getDishimage(), holder.foodImg);

 Picasso.with(context)
                .load(history.getDishimage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.foodImg);



        return convertView;

    }

    public static class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;


    }}
