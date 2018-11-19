package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mmmalthaf.momsmenu.R;

import java.util.ArrayList;

public class Tab2 extends Fragment {


    ListView lv;

    public static String [] ingNames ;
    public static String [] ingDetails ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_items_view, container, false);

        ingNames = ((RecipeDetailActivity)getActivity()).ingredients.split("~");
        ingDetails = ((RecipeDetailActivity)getActivity()).ingredientsAmnt.split("~");


        lv=(ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(new ItemsAdaptorOld(this.getActivity(), ingNames, ingDetails));

        return rootView;

    }


}
