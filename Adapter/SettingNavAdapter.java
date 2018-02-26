package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yumtiff.mohitkumar.tiffin.Model.SettingDrawerItem;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.ArrayList;

/**
 * Created by mohit.gupta on 2/4/2017.
 */

public class SettingNavAdapter  extends BaseAdapter {

    private Context _context;
    private ArrayList<SettingDrawerItem> _items;



    public SettingNavAdapter(Context _context, ArrayList<SettingDrawerItem> items) {
        this._context=_context;
        this._items=items;

    }

    private class ViewHolder {
        TextView itemName;

    }

    @Override
    public int getCount() {

        return _items.size();
    }

    @Override
    public Object getItem(int position) {

        return _items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.itemName = (TextView) convertView.findViewById(R.id.item_name_txtview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SettingDrawerItem item = (SettingDrawerItem) getItem(position);
        holder.itemName.setText(item.getName());


        return convertView;
    }
}
