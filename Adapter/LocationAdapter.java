package com.yumtiff.mohitkumar.tiffin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Model.UserLocation;
import com.yumtiff.mohitkumar.tiffin.R;

import java.util.List;

/**
 * Created by mohit.gupta on 7/25/2017.
 */

public class LocationAdapter extends BaseAdapter {
    Context context;
    List<UserLocation> userlocationList;
    RadioGroup radioGroup;
    View rootView;

    public LocationAdapter(Context context, List<UserLocation> userlocationList) {
        this.context=context;
        this.userlocationList=userlocationList;

    }

    @Override
    public int getCount() {
        return userlocationList.size();
    }

    @Override
    public Object getItem(int i) {
        return userlocationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        rootView=convertView;

        if (rootView==null) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.user_location_layout, null);
        }

        TextView locationTv = (TextView)rootView.findViewById(R.id.location);
       /* radioGroup = (RadioGroup)rootView.findViewById(R.id.rdGrp);
        RadioButton radioButton = (RadioButton) rootView.findViewById(R.id.radioButton);*/
        UserLocation userLocation=userlocationList.get(i);



       /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        String locationStr = ((RadioButton)rootView.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                        Log.d("locationStr",locationStr);
                        Toast.makeText(context,locationStr,Toast.LENGTH_SHORT).show();
//
                        break;

                }
            }
        });*/






        locationTv.setText(userLocation.getRegionName()+" , "+userLocation.getCityName()+" , "+userLocation.getStateName());



        return rootView;
    }
}
