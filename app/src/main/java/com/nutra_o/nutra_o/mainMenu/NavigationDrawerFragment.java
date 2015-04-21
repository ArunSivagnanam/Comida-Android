package com.nutra_o.nutra_o.mainMenu;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.mainMenu.MainMenuRecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {

    Toolbar toolbar;
    DrawerLayout drawer;

    String TITLES[] = {"Tasks","Projects","Calender","Clip board","Categories", "Settings","Log out"};
    int ICONS[] = {R.drawable.ic_action_task,R.drawable.ic_action_prj,R.drawable.ic_action_calender,R.drawable.ic_action_clip,R.drawable.ic_action_cat,R.drawable.ic_action_set,R.drawable.ic_action_log};

    String NAME;
    String EMAIL;
    int PROFILE = R.drawable.profile;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;                    // Declaring Action Bar Drawer Toggle


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        Activity act = getActivity();
        ApplicationImpl actApplication = ((ApplicationImpl)act.getApplication());
        NAME = actApplication.getModel().currentUser.FirstName+" "+ actApplication.getModel().currentUser.LastName;
        EMAIL = actApplication.getModel().currentUser.Email;

        mAdapter = new MainMenuRecycleAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,act);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(getActivity());                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        return v;
    }


    public void setUpDrawerFragment(Toolbar toolbar, DrawerLayout drawer) {
        this.toolbar = toolbar;
        this.drawer = drawer;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        }; // Drawer Toggle Object Made

        drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State



    }
}
