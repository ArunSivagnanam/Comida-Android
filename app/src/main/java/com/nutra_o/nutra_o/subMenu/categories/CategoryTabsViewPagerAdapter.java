package com.nutra_o.nutra_o.subMenu.categories;

/**
 * Created by ars on 02-04-2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.Category;

import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class CategoryTabsViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<CharSequence> Titles = new ArrayList<CharSequence>();
    ArrayList<Category> categoryList;
    // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    ApplicationModel model;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public CategoryTabsViewPagerAdapter(FragmentManager fm, ArrayList<CharSequence> Titles,ApplicationModel model, int mNumbOfTabsumb) {
        super(fm);

        this.Titles = Titles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.model = model;
        this.categoryList = model.categories;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

            CategoryTabFragment tab = new CategoryTabFragment();
            model.currentCategory = categoryList.get(position);
            return tab;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {

        return this.Titles.get(position);
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}