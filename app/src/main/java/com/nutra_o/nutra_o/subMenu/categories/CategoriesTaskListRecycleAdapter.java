package com.nutra_o.nutra_o.subMenu.categories;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nutra_o.nutra_o.models.Task;
import com.nutra_o.nutra_o.R;

import java.util.ArrayList;

public class CategoriesTaskListRecycleAdapter extends RecyclerView.Adapter<CategoriesTaskListRecycleAdapter.ViewHolder> {

    private CategoryTabFragment currentFragment;
    private ArrayList<Task> taskLists;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CategoryTabFragment currentFragment;
        TextView taskName;
        TextView creationData;
        RelativeLayout layout;
        RelativeLayout header;



        public ViewHolder(View itemView, CategoryTabFragment currentFragment) {
            super(itemView);
            this.currentFragment = currentFragment;
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            layout = (RelativeLayout) itemView.findViewById(R.id.handle);
            layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            // call method on menu fragment
            currentFragment.onCardClick(v);

        }
    }

    public CategoriesTaskListRecycleAdapter(ArrayList<Task> taskList, CategoryTabFragment fragment){

        currentFragment = fragment;
        this.taskLists = taskList;

    }

    @Override
    public CategoriesTaskListRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(currentFragment.getActivity()).inflate(R.layout.task_card, parent, false);

        return new ViewHolder(itemView,currentFragment);

    }

    // ligger indjoebslist model oplysninger i viewholder
    @Override
    public void onBindViewHolder(CategoriesTaskListRecycleAdapter.ViewHolder holder, int position) {

        Task t = taskLists.get(position);
        holder.taskName.setText(t.name);
        holder.creationData.setText((t.startDateTime.toString()));
        holder.layout.setBackgroundColor(t.category.color);

    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }



}
