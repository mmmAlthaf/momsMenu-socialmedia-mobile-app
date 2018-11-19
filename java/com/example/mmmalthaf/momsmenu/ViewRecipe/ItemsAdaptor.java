package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mmmalthaf.momsmenu.R;

import java.util.ArrayList;

public class ItemsAdaptor extends BaseAdapter{
    ArrayList<String> name;
    ArrayList<String> details;
//    Integer [] images;
    Context context;
    private static LayoutInflater inflater=null;
    public ItemsAdaptor(Context mainActivity, ArrayList<String> prgmNameList, ArrayList<String> prgmSubList) {
        // TODO Auto-generated constructor stub
        name =prgmNameList;
        details =prgmSubList;
        context=mainActivity;
//        images=imagesIn;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return name.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView stv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.singleitemview, null);
        holder.tv=(TextView) rowView.findViewById(R.id.ingr_name);
        holder.stv=(TextView) rowView.findViewById(R.id.ingr_details);
//        holder.img=(ImageView) rowView.findViewById(R.id.ingr_img);

        holder.tv.setText(name.get(position));
        holder.stv.setText(details.get(position));
//        holder.img.setImageResource(images[position]);
        return rowView;
    }

}