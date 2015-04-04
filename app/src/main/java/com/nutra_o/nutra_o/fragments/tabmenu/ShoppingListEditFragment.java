package com.nutra_o.nutra_o.fragments.tabmenu;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nutra_o.nutra_o.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListEditFragment extends android.support.v4.app.Fragment {


    public ShoppingListEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shopping_list_edit, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.home){

            NavUtils.navigateUpFromSameTask(getActivity());
        }

        return super.onOptionsItemSelected(item);
    }
}
