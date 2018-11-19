package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mmmalthaf.momsmenu.R;

public class Tab1 extends Fragment {

    TextView title,introText;
    ImageView titleImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab1, container, false);

        title = (TextView)rootView.findViewById(R.id.titleID);
        introText = (TextView)rootView.findViewById(R.id.introText);
        titleImg = (ImageView)rootView.findViewById(R.id.titleImg);


        titleImg.setImageResource(((RecipeDetailActivity)getActivity()).dispPic);
        introText.setText(((RecipeDetailActivity)getActivity()).introText);
        title.setText(((RecipeDetailActivity)getActivity()).recipeNamee);

        return rootView;
    }



}