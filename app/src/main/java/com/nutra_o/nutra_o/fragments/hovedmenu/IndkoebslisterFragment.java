package com.nutra_o.nutra_o.fragments.hovedmenu;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nutra_o.nutra_o.fragments.tabmenu.ShoppingListEditFragment;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.ShoppingList;
import com.nutra_o.nutra_o.adapters.InkoebsListTab1ViewPagerAdapter;
import com.nutra_o.nutra_o.tabs.SlidingTabLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import com.nutra_o.nutra_o.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class IndkoebslisterFragment extends android.support.v4.app.Fragment implements Observer, View.OnClickListener {

    ViewPager pager;
    InkoebsListTab1ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    CharSequence Titles[]={"Aktive","Ikke købte","Købte"};
    int Numboftabs =3;

    ApplicationImpl application;
    ApplicationModel model;

    View fab;

    public IndkoebslisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_indkoebslister, container, false);
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        adapter =  new InkoebsListTab1ViewPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Numboftabs);

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



    @Override
    public void onClick(View v) {

        // haandtere fab click
        if(v == fab){

            android.support.v4.app.Fragment f = new ShoppingListEditFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(R.id.root_frag, f).addToBackStack("FRAG");
            transaction.commit();


        }

    }
}
