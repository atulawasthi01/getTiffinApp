package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yumtiff.mohitkumar.tiffin.Model.TdayChild;
import com.yumtiff.mohitkumar.tiffin.Model.TdayGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mohit.gupta on 4/11/2017.
 */

public class TiffinChildAdapter extends BaseExpandableListAdapter {

    public Context context;
    Button savechangeBtn;
    LinearLayout containerLl;
    ArrayList<TdayGroup> ListTerbaru;
    ArrayList<ArrayList<TdayChild>> ListFoodChild;
    private int ii;
    boolean isClick=false;
    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    String[] multichildArr;
    int i;




    public TiffinChildAdapter(Context context, ArrayList<TdayGroup> ListTerbaru, ArrayList<ArrayList<TdayChild>> ListFoodChild) {
        this.context = context;
        this.ListTerbaru = ListTerbaru;
        this.ListFoodChild = ListFoodChild;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }


    @Override
    public TdayChild getChild(int groupPosition, int childPosition) {
        return ListFoodChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TdayChild FoodChild = getChild(groupPosition, childPosition);
        TdayAdapter.ViewHolder holder = null;





        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        /*if (ListFoodChild.size()==0) {
            Toast.makeText(context,"Child Items Empty",Toast.LENGTH_LONG).show();
            return 0;
        }
        else {
            Toast.makeText(context,"Child Items not Empty",Toast.LENGTH_LONG).show();
            Log.d("ListFoodChild", ""+ListFoodChild.size());
            return ListFoodChild.get(groupPosition).size();
        }*/

        try {
            ii=ListFoodChild.get(groupPosition).size();
        }
        catch (IndexOutOfBoundsException e) {

            e.printStackTrace();

        }

        return ii;
    }

    @Override
    public TdayGroup getGroup(int groupPosition) {
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



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }




}
