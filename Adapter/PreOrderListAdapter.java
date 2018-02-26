package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Model.PreOrderData;
import com.yumtiff.mohitkumar.tiffin.ProductClickListener;
import com.yumtiff.mohitkumar.tiffin.R;
import com.squareup.picasso.Picasso;
import com.yumtiff.mohitkumar.tiffin.Utility.Network;

import java.util.ArrayList;

/**
 * Created by mohit.gupta on 4/11/2017.
 */

public class PreOrderListAdapter extends ArrayAdapter<PreOrderData> {

    Dialog dialog;
    Activity context;
    ArrayList<PreOrderData> newAL;
    PreOrderData preOrderData;
    private final ProductClickListener productClickListener;
    Holder holder;
    LayoutInflater inflater;
    public int pos;
    String dishId;
    public static String catId,discountId;
    int deleteindexpos,quantityindexpos;
    int[] quntArr = {1,2,3,4};
    int[] qunttvArr;
    ProgressDialog pDialog;
    int getDeleteindexpos;
    int quantity=1;
    int qunt,qunttt;
    int cnt=1;
    int globPos;


    public PreOrderListAdapter(Activity context, ProductClickListener productClickListener,ArrayList<PreOrderData> newAL) {
        super(context, 0, newAL);
        this.newAL = newAL;
        this.context = context;
        this.productClickListener=productClickListener;

    }

    @Override
    public int getCount() {
        return newAL.size();
    }

    @NonNull

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        preOrderData = getItem(position);
        pos=position;

        if (convertView == null) {
            //  Toast.makeText(context,""+breakFastList.size(),Toast.LENGTH_LONG).show();

            inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.cart_row_layout, parent, false);
            holder = new Holder();
            //holder.codefoodTv = (TextView)convertView.findViewById(R.id.codefood);
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);
            //holder.deleteBtn = (Button)convertView.findViewById(R.id.deleteBtn);
           /* holder.quantityTv = (TextView)convertView.findViewById(R.id.quantitytxt);
            holder.qunttxt = (TextView)convertView.findViewById(R.id.quantityy);*/
            holder.addBtn = (Button)convertView.findViewById(R.id.addBtn);
            holder.subBtn = (Button)convertView.findViewById(R.id.subBtn);
            holder.total = (TextView)convertView.findViewById(R.id.total);
            holder.totalpriceTv = (TextView)convertView.findViewById(R.id.totalprice);

            //holder.quntity.setVisibility(View.INVISIBLE);
            //holder.qunttxt.setVisibility(View.INVISIBLE);
            holder.total.setVisibility(View.INVISIBLE);
            holder.totalpriceTv.setVisibility(View.INVISIBLE);
           // holder.quntity.setVisibility(View.INVISIBLE);
            holder.addBtn.setVisibility(View.INVISIBLE);
            holder.subBtn.setVisibility(View.INVISIBLE);




            convertView.setTag(holder);











           /* qunttvArr=new int[newAL.size()];

            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder=(Holder) view.getTag();
                    quantityindexpos=position;

                    if(quntArr[quantityindexpos]<1000) {
                        quntArr[quantityindexpos] = quntArr[quantityindexpos] + 1;
                    }
                    holder.quantityTv.setText("" +quntArr[quantityindexpos]);
                    //Toast.makeText(context, "" +  quntArr[quantityindexpos], Toast.LENGTH_SHORT).show();

                }
            });*/




        } else {
            holder = (Holder) convertView.getTag();
        }


        preOrderData = newAL.get(position);
        holder.titlefoodTv.setText(preOrderData.getDishName());
        holder.pricefoodTv.setText("₹"+preOrderData.getDishPrice());
        holder.discripfoodTv.setText(preOrderData.getDishdiscription());
        //holder.quantityTv.setText(preOrderData.getQuanitity());


        Picasso.with(context)
                .load(preOrderData.getDishImage())
                .placeholder(R.drawable.thali) // optional
                .error(R.drawable.thali)         // optional
                .into(holder.foodImg);


        /*holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preOrderData=newAL.get(position);
                getDeleteindexpos=position;

                dialog = new Dialog(context);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delete_dialog_layout);
                dialog.show();
                Button cancelBtn = (Button) dialog.findViewById(R.id.cancel);
                Button okBtn = (Button) dialog.findViewById(R.id.ok);
                TextView txtdialogTv = (TextView) dialog.findViewById(R.id.txtdialog);
                txtdialogTv.setText("Delete Dish");
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newAL.remove(getDeleteindexpos);
                        notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
            }
        });*/



        qunttvArr=new int[newAL.size()];






        /*holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity=newAL.get(position).getQuanitity();
                qunt= Integer.parseInt(quantity);
                qunt++;
                String quntStr= String.valueOf(qunt);
                holder.quantityTv.setText(quntStr);
               *//* //PreOrderData preOrderData=newAL.get(position);
                //quantity++;
                quantityindexpos=position;
                qunttvArr[quantityindexpos]++;
                holder.quantityTv.setText("" + qunttvArr[quantityindexpos]);
                Log.d("counterAdd",""+quantity);*//*

                *//*qunt++;
                holder.quantityTv.setText("" + qunt);
                Log.d("counterAdd",""+qunt);*//*

            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity=newAL.get(position).getQuanitity();
                qunt= Integer.parseInt(quantity);
                if (qunt>1) {
                    qunt--;
                    notifyDataSetChanged();
                    for (int i=0;i<newAL.size();i++) {
                        //qunttt=preOrderData.getQuanitity(i);
                    }
                }

               *//* quantityindexpos=position;
                qunttvArr[quantityindexpos]--;
                //quantity--;
                holder.quantityTv.setText("" + qunttvArr[quantityindexpos]);
                Log.d("counterSub",""+quantity);*//*
                *//*qunt--;

                holder.quantityTv.setText("" + qunt);
                Log.d("counterSub",""+qunt);*//*
            }
        });*/

        return convertView;

    }


    public static class Holder {
        TextView codefoodTv,titlefoodTv, discripfoodTv, pricefoodTv,quantityTv,quntity,total,totalpriceTv,qunttxt;
        ImageView foodImg;
        Button deleteBtn,addBtn,subBtn;
    }


}
