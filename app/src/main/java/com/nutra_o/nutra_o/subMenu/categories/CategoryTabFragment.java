package com.nutra_o.nutra_o.subMenu.categories;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.activitys.IndkoebsListEditActivity;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.Task;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryTabFragment extends android.support.v4.app.Fragment implements Observer {

    ApplicationImpl application;
    public ApplicationModel model;

    CategoriesFragment mainFragment;

    RecyclerView cardList;
    LinearLayoutManager layoutManager;
    CategoriesTaskListRecycleAdapter adapter;


    // inden fragmentet bliver lavet skal current category saettes i modellen
    public CategoryTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_tab, container, false);

        application = (ApplicationImpl)getActivity().getApplication();
        model = application.getModel();
        model.addObserver(this);

        cardList = (RecyclerView) v.findViewById(R.id.Tab1Cards_RecyclerView);
        cardList.setHasFixedSize(true);
        // set adapter
        adapter = new CategoriesTaskListRecycleAdapter(sortListByCategory(model.getTaskList()),this);
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

            adapter = new CategoriesTaskListRecycleAdapter(sortListByCategory(model.getTaskList()),this);
            cardList.setAdapter(adapter);
        }
    }

    @Override
    public void update(Observable observable, Object data) {

        // render view
        adapter = new CategoriesTaskListRecycleAdapter(sortListByCategory(model.getTaskList()),this);
        cardList.setAdapter(adapter);
    }

    public void onCardClick(View v){

        startActivity(new Intent(getActivity(), IndkoebsListEditActivity.class));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.deleteObserver(this);
    }

    public ArrayList<Task> sortListByCategory(ArrayList<Task> taskList){
        ArrayList<Task> result = new ArrayList<Task>();
        for(Task t : taskList){
            if(t.category.id == model.currentCategory.id){
                result.add(t);
            }
        }
        return result;
    }


}
