package com.nutra_o.nutra_o.fragments.tabmenu;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.service.IndkoebslisterCardListRecycleAdapter;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndkoebslisterTab1Fragment extends android.support.v4.app.Fragment implements Observer {

    ApplicationImpl application;
    ApplicationModel model;

    RecyclerView cardList;
    LinearLayoutManager layoutManager;
    IndkoebslisterCardListRecycleAdapter adapter;

    public IndkoebslisterTab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_indkoebslister_tab1, container, false);

        application = (ApplicationImpl)getActivity().getApplication();
        model = application.getModel();
        model.addObserver(this);

        cardList = (RecyclerView) v.findViewById(R.id.Tab1Cards_RecyclerView);
        cardList.setHasFixedSize(true);
        // set adapter
        adapter = new IndkoebslisterCardListRecycleAdapter(model.getListOfShoppingList(),this);
        cardList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(model.getListOfShoppingList() != null){ // called first time before parrent fetches shoppinglists
            adapter = new IndkoebslisterCardListRecycleAdapter(model.getListOfShoppingList(),this);
            cardList.setAdapter(adapter);
        }
    }

    @Override
    public void update(Observable observable, Object data) {

        // render view
        adapter = new IndkoebslisterCardListRecycleAdapter(model.getListOfShoppingList(),this);
        cardList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.deleteObserver(this);
    }
}
