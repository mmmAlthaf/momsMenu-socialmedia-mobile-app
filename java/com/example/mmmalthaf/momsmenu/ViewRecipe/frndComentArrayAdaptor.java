package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mmmalthaf.momsmenu.R;

import java.util.ArrayList;

public class frndComentArrayAdaptor extends ArrayAdapter<String>{
    ArrayList<String> result = new ArrayList<>();
    ArrayList<String> coment = new ArrayList<>();
    ArrayList<String> dPic = new ArrayList<>();


    String proUrll,subresult;
    Activity context;


    public frndComentArrayAdaptor(Activity mainActivity, ArrayList<String> name, ArrayList<String> comment,
                               ArrayList<String> dispPic) {
        super(mainActivity, R.layout.singlefrcommentview,name);
        result=name;
        coment = comment;
        dPic = dispPic;
        context=mainActivity;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singlefrcommentview,null,true);
        TextView tv=(TextView) rowView.findViewById(R.id.textViewName);
        TextView stv=(TextView) rowView.findViewById(R.id.textViewComent);
        ImageView img = (ImageView)rowView.findViewById(R.id.profile_image);


        tv.setText(result.get(position));
        stv.setText(coment.get(position));

//        Log.e("pic",dPic.get(position));

        Glide.with(context).load(dPic.get(position)).into(img);
//
        return rowView;
    }
}