package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.TdayGroup;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mohit.gupta on 4/8/2017.
 */

public class TiffinAdapter extends ArrayAdapter<TdayGroup> {

    List<TdayGroup> tiffingrpList;
    Activity context;
    LayoutInflater inflater;
    TdayGroup tdayGroup;
    Holder holder;

    public TiffinAdapter(Activity context, List<TdayGroup> tiffingrpList) {
        super(context, 0, tiffingrpList);
        this.tiffingrpList = tiffingrpList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return tiffingrpList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tdayGroup = getItem(position);

        if (convertView == null) {
            //Toast.makeText(context, "" + dinnerList.size(), Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_cell, parent, false);
            holder = new Holder();
            holder.tiffinTv = (TextView) convertView.findViewById(R.id._imageName);
            holder.tiffinImg = (ImageView) convertView.findViewById(R.id._image);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        tdayGroup = tiffingrpList.get(position);

        holder.tiffinTv.setText(tdayGroup.getCategory());

        Picasso.with(context)
                .load(tdayGroup.getDishImage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.tiffinImg);




        return convertView;

    }

    public static class Holder {
        TextView tiffinTv;
        ImageView tiffinImg;
    }
}
