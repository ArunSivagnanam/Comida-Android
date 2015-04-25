package com.nutra_o.nutra_o.subMenu.categories;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nutra_o.nutra_o.test.IndkoebsListEditActivity;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.test.ShoppingList;
import com.nutra_o.nutra_o.tabs.SlidingTabLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import com.nutra_o.nutra_o.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends android.support.v4.app.Fragment implements Observer, View.OnClickListener {

    ViewPager pager;
    CategoryTabsViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    ArrayList<CharSequence> titles = new ArrayList<CharSequence>();
    ArrayList<Integer> tabColors = new ArrayList<Integer>();

    ApplicationImpl application;
    ApplicationModel model;

    View fab;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        application = (ApplicationImpl)getActivity().getApplication();
        model = application.getModel();
        model.addObserver(this);

        for(int i = 0; i<model.categories.size(); i++){
            titles.add(model.categories.get(i).name);
            tabColors.add(model.categories.get(i).color);
        }

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        adapter =  new CategoryTabsViewPagerAdapter(getActivity().getSupportFragmentManager(), titles,model,titles.size());

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        int[] colros = new int[tabColors.size()];
        for (int i = 0; i<tabColors.size(); i++){
            colros[i] = tabColors.get(i);
        }
        tabs.setSelectedIndicatorColors(colros);

        /*
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.actionColor);
            }
        });
        */
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);



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
            startActivity(new Intent(getActivity(), IndkoebsListEditActivity.class));
        }

    }
}
