package com.example.mmmalthaf.momsmenu.RecipeFeed;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mmmalthaf.momsmenu.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostsArrayAdaptor extends ArrayAdapter<String>{
    ArrayList<String> result;
    Integer [] proUrll,dPic;
    ArrayList<String> subresult;
    ArrayList<String> ratingg;
    Activity context;


    public PostsArrayAdaptor(Activity mainActivity, ArrayList<String> prgmNameList, ArrayList<String> prgmSubList,
                             Integer [] profPic,Integer [] dispPic,ArrayList<String> rating) {
        super(mainActivity, R.layout.singlepostview,prgmNameList);
        result=prgmNameList;
        ratingg = rating;
        proUrll = profPic;
        dPic = dispPic;
        subresult=prgmSubList;
        context=mainActivity;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singlepostview,null,true);
        TextView tv=(TextView) rowView.findViewById(R.id.textView1);
        TextView stv=(TextView) rowView.findViewById(R.id.contact_details);
        ImageView img = (ImageView)rowView.findViewById(R.id.imageView);
        ImageView prpic=(ImageView)rowView.findViewById(R.id.profile_image);
        TextView rTxt=(TextView)rowView.findViewById(R.id.likeTxt);


        tv.setText(result.get(position));
        stv.setText(subresult.get(position));
        rTxt.setText(ratingg.get(position));
//        Log.e("pic",dPic.get(position));

        img.setImageResource(dPic[position]);
        prpic.setImageResource(proUrll[position]);

//
        return rowView;
    }

}