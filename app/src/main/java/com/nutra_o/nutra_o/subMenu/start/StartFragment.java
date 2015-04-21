package com.nutra_o.nutra_o.subMenu.start;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.subMenu.categories.CategoryTabsViewPagerAdapter;
import com.nutra_o.nutra_o.tabs.SlidingTabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends android.support.v4.app.Fragment {


    ViewPager pager;
    CategoryTabsViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    CharSequence Titles[]={"Home","Events"};
    int Numboftabs =2;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_start, container, false);
        // Inflate the layout for this fragment




        return v;
    }


}
