package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mmmalthaf.momsmenu.R;
import com.example.mmmalthaf.momsmenu.RecipeFeed.PostTabActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    public  String recipeNamee = "" ;
    public  Integer chefPic;
    public String chefName = "";
    public  String introText = "" ;
    public  String ingredients = "";
    public String ingredientsAmnt = "" ;
    public  String utensils = "" ;
    public  String utensilsAmnt ="" ;
    public String steps = "" ;
    public String stepsDtl = "";
    public Integer dispPic;
    public String ID = "";
    public String rating = "";
    public String postUserID = "";
    public Boolean VEGCHECK = false;
    public String vg = "Veg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_view);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null) {
            VEGCHECK = (Boolean)b.get("VEGCHECK");
            chefName = (String)b.get("CfName");
            postUserID = (String)b.get("postUserId");
            chefPic = (Integer) b.get("proPic") ;
            recipeNamee = (String)b.get("Title");
            rating = (String)b.get("Rating");
            ID = (String)b.get("ID");
            introText = (String)b.get("Introduction");
            ingredients = (String)b.get("Ings");
            ingredientsAmnt = (String) b.get("IngsAmnt");
            utensils = (String)b.get("Uts");
            utensilsAmnt = (String)b.get("UtsAmnt");
            steps = (String)b.get("Steps");
            stepsDtl = (String)b.get("StepsDtl");
            dispPic = (Integer)b.get("IntroPic");
        }
        if(VEGCHECK == true)
            vg = "Veg";
        else if(VEGCHECK == false)
            vg = "NonVeg";




        //setting tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("Introduction"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Utensils"));
        tabLayout.addTab(tabLayout.newTab().setText("Steps"));
//        tabLayout.addTab(tabLayout.newTab().setText("Finishing Touches"));
        tabLayout.addTab(tabLayout.newTab().setText("Comments"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final RecipeAdaptor a = new RecipeAdaptor(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(a);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),PostTabActivity.class));
    }

    public void newPost(View view) {


    }
}
