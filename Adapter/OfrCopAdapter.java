package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.yumtiff.mohitkumar.tiffin.Model.OfferCoupon;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.List;

/**
 * Created by mohit.gupta on 2/8/2017.
 */

public class OfrCopAdapter extends ArrayAdapter<OfferCoupon> {
    Context context;
    List<OfferCoupon> offerCouponList;
    LayoutInflater inflater;
    OfferCoupon offerCoupon;

    public OfrCopAdapter(Context context, List<OfferCoupon> offerCouponList) {
        super(context,0,offerCouponList);
        this.context=context;
        this.offerCouponList=offerCouponList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return offerCouponList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView==null) {
            convertView = inflater.inflate(R.layout.offercoupon_row_layout, null);
            holder.ofcopImg = (ImageView)convertView.findViewById(R.id.ofcopImg);

            convertView.setTag(holder);
        }

        else  {

            holder = (Holder) convertView.getTag();

        }

        offerCoupon = offerCouponList.get(position);
        holder.ofcopImg.setImageResource(offerCoupon.getOffercoupon());

        return convertView;

    }

    class Holder {
        ImageView ofcopImg;
    }
}
