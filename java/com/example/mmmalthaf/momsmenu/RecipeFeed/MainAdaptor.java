package com.example.mmmalthaf.momsmenu.RecipeFeed;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by M.M.M Althaf on 3/14/2018.
 */


public class MainAdaptor extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MainAdaptor(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                recipeFeedTabAll tab1 = new recipeFeedTabAll();
                return tab1;
            case 1:
                recipeFeedTabMy tab2 = new recipeFeedTabMy();
                return tab2;
            case 2:
                profileTab tab3 = new profileTab();
                return tab3;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
