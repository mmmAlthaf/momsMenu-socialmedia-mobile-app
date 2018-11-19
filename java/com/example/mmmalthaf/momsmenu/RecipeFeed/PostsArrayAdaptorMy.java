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

public class PostsArrayAdaptorMy extends ArrayAdapter<String>{
    ArrayList<String> result,dPic,ratingg;

    String proUrll,subresult;
    Activity context;


    public PostsArrayAdaptorMy(Activity mainActivity, ArrayList<String> prgmNameList, String prgmSubList,
                             String profPic,ArrayList<String> dispPic,ArrayList<String> rating) {
        super(mainActivity, R.layout.singlepostview,prgmNameList);
        result=prgmNameList;
        proUrll = profPic;
        ratingg = rating;
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
        stv.setText(subresult);
        rTxt.setText(ratingg.get(position));
//        Log.e("pic",dPic.get(position));

        Glide.with(context).load(dPic.get(position)).into(img);
        Glide.with(context).load(proUrll).into(prpic);
//
        return rowView;
    }

}