package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mmmalthaf.momsmenu.R;

public class Tab3 extends Fragment {


    ListView lv;

    public static String [] utNames ={"Rolling Pan","Bowl","Spoons","Oven","Boiling Pan","Mould"};
    public static String [] utDetails ={"","","","","",""};
    public static Integer [] utImages={R.drawable.rollingpan,R.drawable.bowl,R.drawable.spoons,R.drawable.oven,R.drawable.boilingpan,R.drawable.mould,};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_items_view_ut, container, false);
        lv=(ListView) rootView.findViewById(R.id.listView);

        utNames = ((RecipeDetailActivity)getActivity()).utensils.split("~");
        utDetails = ((RecipeDetailActivity)getActivity()).utensilsAmnt.split("~");

        lv.setAdapter(new ItemsAdaptorOldUt(this.getActivity(), utNames, utDetails));

        return rootView;

    }


}
