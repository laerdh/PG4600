package com.westerdals.dako.pokemon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Map", "Catched", "Catch!"};


    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MapsFragment tab1 = new MapsFragment();
                return tab1;
            case 1:
                ListFragment tab2 = new ListFragment();
                return tab2;
            case 2:
                CatchFragment tab3 = new CatchFragment();
                return tab3;
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
