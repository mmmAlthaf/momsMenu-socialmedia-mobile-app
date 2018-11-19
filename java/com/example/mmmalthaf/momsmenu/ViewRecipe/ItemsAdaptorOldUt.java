package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mmmalthaf.momsmenu.R;

public class ItemsAdaptorOldUt extends BaseAdapter{
    String[] name;
    String[] details;
    //    Integer [] images;
    Context context;
    private static LayoutInflater inflater=null;
    public ItemsAdaptorOldUt(Context mainActivity, String[] prgmNameList, String[] prgmSubList) {
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
        return name.length;
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
//        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.singleitemviewut, null);
        holder.tv=(TextView) rowView.findViewById(R.id.ingr_name);
        holder.stv=(TextView) rowView.findViewById(R.id.ingr_details);
//        holder.img=(ImageView) rowView.findViewById(R.id.ingr_img);

        holder.tv.setText(name[position]);
        holder.stv.setText(details[position]);
//        holder.img.setImageResource(images[position]);
        return rowView;
    }

}