package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mmmalthaf.momsmenu.R;

public class Tab4 extends Fragment {


    ListView lv;

    public static String [] stpNames ={"Sugar","Berries","Gram Sugar","Gelatine","Egg","Flour"};
    public static String [] stpDetails ={"1/2 Cup","200 gram","","50 gram","5 gram","One","2 Cup"};
//    public static Integer [] ingImages={R.drawable.sugar};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_items_view_stp, container, false);

        stpNames = ((RecipeDetailActivity)getActivity()).steps.split("~");
        stpDetails = ((RecipeDetailActivity)getActivity()).stepsDtl.split("~");


        lv=(ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(new ItemsAdaptorOldStp(this.getActivity(), stpNames, stpDetails));

        return rootView;

    }


}
