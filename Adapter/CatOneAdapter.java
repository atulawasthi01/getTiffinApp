package com.yumtiff.mohitkumar.tiffin.Adapter;

/**
 * Created by mohit.gupta on 1/28/2017.
 */

public class CatOneAdapter {/*extends ArrayAdapter<CatOne> {
    Context context;
    List<CatOne> catOneList;
    LayoutInflater inflater;
    CatOne catOne;



    @Override
    public int getCount() {
        return catOneList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView==null) {
            convertView = inflater.inflate(R.layout.category_layout, null);
            holder.titlefoodTv = (TextView)convertView.findViewById(R.id.titlefood);
            holder.discripfoodTv = (TextView)convertView.findViewById(R.id.discripfood);
            holder.pricefoodTv = (TextView)convertView.findViewById(R.id.pricefood);
            holder.foodImg = (ImageView)convertView.findViewById(R.id.catoneimg);

            convertView.setTag(holder);
        }
        else {

            holder = (Holder) convertView.getTag();

        }

        catOne = catOneList.get(position);

        holder.titlefoodTv.setText(catOne.getTitle());
        holder.discripfoodTv.setText(catOne.getDiscription());
        holder.pricefoodTv.setText("₹"+catOne.getPrice());
        holder.foodImg.setImageResource(catOne.getImg());



        return convertView;
    }

    class Holder {
        TextView titlefoodTv, discripfoodTv, pricefoodTv;
        ImageView foodImg;
    }*/
}
