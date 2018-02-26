package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.Notificationn;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.List;

/**
 * Created by mohit.gupta on 5/11/2017.
 */

public class NotiAdapter extends ArrayAdapter<Notificationn> {
    Activity context;
    List<Notificationn> notiList;
    Holder holder;
    LayoutInflater inflater;
    Notificationn notificationn;

    public NotiAdapter(Activity context, List<Notificationn> notiList) {
        super(context, 0, notiList);
        this.notiList = notiList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notiList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        notificationn = getItem(position);


        if (convertView == null) {
            //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

            inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.noti_row_layout, parent, false);
            holder = new Holder();

            holder.notificationTv = (TextView)convertView.findViewById(R.id.notificationtxt);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }


        notificationn = notiList.get(position);

        holder.notificationTv.setText(notificationn.getNotification());


        return convertView;

    }

    public static class Holder {
        TextView notificationTv;

    }
}
