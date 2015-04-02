package com.nutra_o.nutra_o.fragments.hovedmenu;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.ShoppingList;
import com.nutra_o.nutra_o.service.InkoebsListTabViewPagerAdapter;
import com.nutra_o.nutra_o.tabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndkoebslisterFragment extends android.support.v4.app.Fragment implements Observer {

    ViewPager pager;
    InkoebsListTabViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    CharSequence Titles[]={"Aktive","Ikke købte","Købte"};
    int Numboftabs =3;

    ApplicationImpl application;
    ApplicationModel model;

    public IndkoebslisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_indkoebslister, container, false);

        adapter =  new InkoebsListTabViewPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.actionColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        application = (ApplicationImpl)getActivity().getApplication();
        model = application.getModel();
        model.addObserver(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ShoppingList> list =  application.shoppingListAccesor.getAllShoppingList(model.currentUser.ID);
                model.setShoppingListsList(list);
            }
        }).start();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
