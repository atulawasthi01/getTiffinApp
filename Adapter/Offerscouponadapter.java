package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.OfferCoupon;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajat.gupta on 2/25/2017.
 */

public class Offerscouponadapter extends ArrayAdapter<OfferCoupon> {
    Activity context;
    List<OfferCoupon> offerCouponList;
    OfferCoupon  offerCoupon;
    Offerscouponadapter.Holder holder;
    LayoutInflater inflater;


    public Offerscouponadapter(Activity context, List<OfferCoupon> offerCouponList) {
        super(context, 0, offerCouponList);
        this.offerCouponList = offerCouponList;
        this.context = context;

    }
    @Override
    public int getCount() {
        return offerCouponList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        offerCoupon = getItem(position);

        if (convertView == null) {
            //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.offercoupon_row_layout, parent, false);
            holder = new Offerscouponadapter.Holder();

          /*  holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);*/
            holder.foodImg = (ImageView)convertView.findViewById(R.id.ofcopImg);
            holder.couponnameTv = (TextView)convertView.findViewById(R.id.couponname);
            holder.couponcodeTv = (TextView)convertView.findViewById(R.id.couponcode);
            holder.amountTv = (TextView)convertView.findViewById(R.id.amount);
            holder.startdateTv = (TextView)convertView.findViewById(R.id.startdate);
            holder.enddateTv = (TextView)convertView.findViewById(R.id.enddate);
            holder.discriptionTv = (TextView)convertView.findViewById(R.id.discription);



            convertView.setTag(holder);
        } else {
            holder = (Offerscouponadapter.Holder) convertView.getTag();
        }

        offerCoupon = offerCouponList.get(position);



        //ImageLoader.getInstance().displayImage(offerCoupon.getImage(), holder.foodImg);

        Picasso.with(context)
                .load(offerCoupon.getImage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.foodImg);

        holder.couponnameTv.setText(offerCoupon.getTitle());
        holder.couponcodeTv.setText(offerCoupon.getCouponCode());
        holder.amountTv.setText(offerCoupon.getAmount());
        holder.startdateTv.setText(offerCoupon.getStartDate());
        holder.enddateTv.setText(offerCoupon.getValidDate());
        holder.discriptionTv.setText(offerCoupon.getDescription());




        return convertView;

    }

    public static class Holder {
        TextView couponnameTv,couponcodeTv,amountTv,startdateTv,enddateTv,discriptionTv;
        ImageView foodImg;
    }






}
