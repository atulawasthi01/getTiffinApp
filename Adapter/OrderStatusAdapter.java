package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yumtiff.mohitkumar.tiffin.Activity.MyOrderActivity;
import com.yumtiff.mohitkumar.tiffin.Model.StatusChild;
import com.yumtiff.mohitkumar.tiffin.Model.StatusGroup;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mohit.gupta on 5/4/2017.
 */

public class OrderStatusAdapter extends BaseExpandableListAdapter {
    Context context;
    Button savechangeBtn;
    LinearLayout containerLl;
    StatusGroup FoodGroup;
    String displyMessage;
    ProgressDialog pDialog;
    LayoutInflater infalInflater;
    int i=1;
    int p;
    StatusChild FoodChild;
    View rootview;
    public  ImageView orderprocessimg,inprocessimg,dispatchimg,deliverImg;
    ViewHolder holder = null;



    ArrayList<StatusGroup> ListTerbaru;
    ArrayList<ArrayList<StatusChild>> ListFoodChild;
    private int ii;




    public OrderStatusAdapter(Context context, ArrayList<StatusGroup> ListTerbaru, ArrayList<ArrayList<StatusChild>> ListFoodChild) {
        this.context = context;
        this.ListTerbaru = ListTerbaru;
        this.ListFoodChild = ListFoodChild;


//      this.count=ListTerbaru.size();
//      this.count=ListFoodChild.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }



    @Override
    public StatusChild getChild(int groupPosition, int childPosition) {

        return ListFoodChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        FoodGroup = (StatusGroup) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //the first row is used as header
        /*if(childPosition ==0)
        {
            convertView = inflater.inflate(R.layout.order_status_layout, null);

        }*/

        //Here is the ListView of the ChildView
        if(childPosition>=0 && childPosition<getChildrenCount(groupPosition)-1) {
            FoodChild = getChild(groupPosition, childPosition);
            convertView = inflater.inflate(R.layout.status_child_layout,null);
            TextView titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
            TextView discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
            TextView pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
            RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            ImageView imgchildimgIv = (ImageView) convertView.findViewById(R.id.catoneimg);

            titlefoodTv.setText(FoodChild.getDishName());
            discripfoodTv.setText(FoodChild.getDishDescription());
            pricefoodTv.setText("₹" + FoodChild.getDishPrice());

            Picasso.with(context)
                    .load(FoodChild.getDishImage())
                    .placeholder(R.drawable.thali)
                    .error(R.drawable.thali)
                    .into(imgchildimgIv);
        }
        //the last row is used as footer
        if(childPosition == getChildrenCount(groupPosition)-1)
        {
            convertView = inflater.inflate(R.layout.order_status_layout,null);
            orderprocessimg = (ImageView)convertView.findViewById(R.id.orderprocessimg);
            inprocessimg = (ImageView)convertView.findViewById(R.id.inprocessimg);
            dispatchimg = (ImageView)convertView.findViewById(R.id.dispatchimg);
            deliverImg = (ImageView)convertView.findViewById(R.id.deliverImg);
            Log.d("trackingidddd",MyOrderActivity.trackingid);

            if (MyOrderActivity.trackingid.equals("1")) {
                //Toast.makeText(context,MyOrderActivity.trackingid,Toast.LENGTH_SHORT).show();
                orderprocessimg.setImageDrawable(null);
                orderprocessimg.setBackground(context.getResources().getDrawable(R.drawable.ordersiconsgreen));
                //orderprocessimg.setColorFilter(ContextCompat.getColor(context,R.color.green));
                //orderprocessimg.setBackgroundResource(R.drawable.ordersiconsgreen);
                inprocessimg.setBackground(context.getResources().getDrawable(R.mipmap.inprocess));
                dispatchimg.setBackground(context.getResources().getDrawable(R.mipmap.dispatched));
                deliverImg.setBackground(context.getResources().getDrawable(R.mipmap.delivered));

            }
            else if (MyOrderActivity.trackingid.equals("2")) {
                inprocessimg.setImageDrawable(null);
                orderprocessimg.setBackground(context.getResources().getDrawable(R.mipmap.orderplace));
                inprocessimg.setBackground(context.getResources().getDrawable(R.drawable.inprocessgreen));
                dispatchimg.setBackground(context.getResources().getDrawable(R.mipmap.dispatched));
                deliverImg.setBackground(context.getResources().getDrawable(R.mipmap.delivered));
            }

            else if (MyOrderActivity.trackingid.equals("3")) {
                dispatchimg.setImageDrawable(null);
                orderprocessimg.setBackground(context.getResources().getDrawable(R.mipmap.orderplace));
                inprocessimg.setBackground(context.getResources().getDrawable(R.mipmap.inprocess));
                dispatchimg.setBackground(context.getResources().getDrawable(R.drawable.dispatchedgreen));
                deliverImg.setBackground(context.getResources().getDrawable(R.mipmap.delivered));
            }
            else if (MyOrderActivity.trackingid.equals("4")) {
                deliverImg.setImageDrawable(null);
                orderprocessimg.setBackground(context.getResources().getDrawable(R.mipmap.orderplace));
                inprocessimg.setBackground(context.getResources().getDrawable(R.mipmap.inprocess));
                dispatchimg.setBackground(context.getResources().getDrawable(R.mipmap.dispatched));
                deliverImg.setBackground(context.getResources().getDrawable(R.drawable.deliveredgreen));
            }
        }



       /* Log.d("Threecalled","" + childPosition);
        Log.d("pppp",""+p);

        if (childPosition >= 0 && childPosition < getChildrenCount(groupPosition)-1) {
            ViewHolder holder = null;
            if (convertView == null) {

                FoodChild = getChild(groupPosition, childPosition);
                infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.status_child_layout, null);


                holder = new ViewHolder();
                holder.titlefoodTv = (TextView) convertView.findViewById(R.id.titlefood);
                holder.discripfoodTv = (TextView) convertView.findViewById(R.id.discripfood);
                holder.pricefoodTv = (TextView) convertView.findViewById(R.id.pricefood);
                holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
                holder.imgchildimgIv = (ImageView) convertView.findViewById(R.id.catoneimg);


                convertView.setTag(holder);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }

           *//* holder.titlefoodTv.setText(FoodChild.getDishName());
            holder.discripfoodTv.setText(FoodChild.getDishDescription());
            holder.pricefoodTv.setText("₹" + FoodChild.getDishPrice());

            Picasso.with(context)
                    .load(FoodChild.getDishImage())
                    .placeholder(R.drawable.thali)
                    .error(R.drawable.thali)
                    .into(holder.imgchildimgIv);*//*

        }

        if (childPosition == getChildrenCount(groupPosition)-1) {
            convertView = infalInflater.inflate(R.layout.order_status_layout, null);
        }*/


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       // Log.d("addlisttt","list");
        Log.d("hhhh",""+groupPosition);
        if (ListFoodChild.size()==0) {
            //Toast.makeText(context,"Child Items Empty",Toast.LENGTH_LONG).show();
            //Log.d("addlisttt","list");
            return 0;
        }
        else {
           // i=i+ListFoodChild.get(groupPosition).size();

            //Toast.makeText(context,""+i,Toast.LENGTH_LONG).show();
            //Log.d("listtt",""+ListFoodChild.get(groupPosition).size());
            p = ListFoodChild.get(groupPosition).size()+1;
            Log.d("addlisttt",""+ListFoodChild.get(groupPosition).size()+1);
            //Log.d("childlistttt",""+ListFoodChild.get(groupPosition).size()+1);
            return ListFoodChild.get(groupPosition).size()+1;


        }
        //return ListFoodChild.get(groupPosition).size();
        /*try {
            ii=ListFoodChild.get(groupPosition).size();
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return ii;*/
    }

    @Override
    public StatusGroup getGroup(int groupPosition) {
        return ListTerbaru.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return ListTerbaru.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ListTerbaru.get(groupPosition).hashCode();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        FoodGroup = (StatusGroup) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.status_group_layout, null);
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);

            holder = new ViewHolder();
            holder.ordernotv = (TextView) convertView.findViewById(R.id.orderno);
            holder.orderdatetv = (TextView) convertView.findViewById(R.id.orderdate);
            holder.deliverydatetv = (TextView) convertView.findViewById(R.id.deldate);
            holder.totalamounttv = (TextView) convertView.findViewById(R.id.ttlamount);
            holder.imggrpIv = (ImageView) convertView.findViewById(R.id.imggrp);
            holder.cancelorderBtn = (Button) convertView.findViewById(R.id.cancelorderBtn);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ordernotv.setText(FoodGroup.getOrdersequenceno());
        holder.orderdatetv.setText(FoodGroup.getDelieverydate());
        holder.deliverydatetv.setText(FoodGroup.getDelieverydate());
        holder.totalamounttv.setText(FoodGroup.getTotalamount());


        Picasso.with(context)
                .load(FoodGroup.getId())
                .placeholder(R.drawable.thali)
                .error(R.drawable.thali)
                .into(holder.imggrpIv);

        holder.cancelorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (!Network.isNetworkCheck(context)) {
                    if ((MyOrderActivity.trackingid.equals("3")) || MyOrderActivity.trackingid.equals("4")) {
                        //Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                        //holder.cancelorderBtn.setBackgroundColor(context.getResources().getColor(R.color.gpsbtn));
                        holder.cancelorderBtn.setClickable(false);
                        Toast.makeText(context, context.getResources().getString(R.string.ordernotcancel), Toast.LENGTH_LONG).show();
                    }

                    /*Toast.makeText(context, context.getResources().getString(R.string.internet_check), Toast.LENGTH_SHORT).show();
                }*/

                else {
                    Toast.makeText(context, "Order Cancel", Toast.LENGTH_SHORT).show();
                    //getcancelorder();
                }

            }
        });

        //Log.d("grpTrackingidd",MyOrderActivity.trackingid);

       /* if (MyOrderActivity.trackingid.equals("3") && MyOrderActivity.trackingid.equals("4")) {
            holder.cancelorderBtn.setClickable(false);
            Toast.makeText(context,context.getResources().getString(R.string.ordernotcancel),Toast.LENGTH_LONG).show();
        }*/





        return convertView;
    }

    private void getcancelorder() {
        String ordercancel_url=Utility.Base_Url+"&action=cancel_order&orderSequenceNumber="+FoodGroup.getOrdersequenceno()+"&status=2";
        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ordercancel_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("loginresponseeee",response.toString());
                        JSONObject jobj= null;
                        try {
                            jobj = new JSONObject(response);
                            boolean status=jobj.getBoolean("status");
                            if (status==true) {
                                 displyMessage=jobj.getString("displyMessage");
                                notifyDataSetChanged();
                                Toast.makeText(context,"Order is Cancel",Toast.LENGTH_LONG).show();
                                holder.cancelorderBtn.setText("Canceled Order");


                            }
                            else if (status==false) {
                                displyMessage=jobj.getString("displyMessage");
                                Toast.makeText(context,displyMessage,Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(context,context.getResources().getString(R.string.internet_check),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Log.d("error",error.toString());
                    }
                }) {
           /* @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Utility.KEY_MOBILENO, mobileStr);
                params.put(Utility.KEY_PASSWORD, pwdStr);
                return params;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        //* SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        //SharedPreferences.Editor editor = loginSp.edit();


//**************** Storing data as KEY/VALUE pair *******************//**//*

        /*editor.putBoolean(" KEY_MOBILENO", true);           // Saving boolean - true/false
        editor.putInt("key_name2", Integer.parseInt("int value"));        // Saving integer
        editor.putFloat("KEY_PASSWORD", Float.parseFloat("float value"));    // Saving float*/



    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }


    static class ViewHolder {
        TextView ordernotv, orderdatetv, deliverydatetv, totalamounttv, titlefoodTv,discripfoodTv,pricefoodTv;
        RatingBar ratingBar;
        ImageView imggrpIv,imgchildimgIv;
        Button cancelorderBtn;


    }
}