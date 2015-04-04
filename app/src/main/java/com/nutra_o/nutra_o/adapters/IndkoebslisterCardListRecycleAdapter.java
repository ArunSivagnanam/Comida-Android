package com.nutra_o.nutra_o.adapters;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nutra_o.nutra_o.fragments.hovedmenu.IndkoebslisterFragment;
import com.nutra_o.nutra_o.fragments.tabmenu.IndkoebslisterTab1Fragment;
import com.nutra_o.nutra_o.models.ShoppingList;
import com.nutra_o.nutra_o.R;

import java.util.ArrayList;

public class IndkoebslisterCardListRecycleAdapter extends RecyclerView.Adapter<IndkoebslisterCardListRecycleAdapter.ViewHolder> {

    private IndkoebslisterTab1Fragment currentFragment;
    private ArrayList<ShoppingList> shoppingLists;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        IndkoebslisterTab1Fragment currentFragment;
        TextView vName;
        TextView creationData;
        TextView duoDate;
        TextView count;
        RelativeLayout layout;


        public ViewHolder(View itemView, IndkoebslisterTab1Fragment currentFragment) {
            super(itemView);
            this.currentFragment = currentFragment;
            vName = (TextView) itemView.findViewById(R.id.txtName);
            creationData = (TextView) itemView.findViewById(R.id.creatiion_textView);
            duoDate = (TextView) itemView.findViewById(R.id.duoDate_textView);
            count = (TextView) itemView.findViewById(R.id.count_textView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layuuu);

            layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            // call method on menu fragment
            currentFragment.onCardClick(v);

        }
    }

    public IndkoebslisterCardListRecycleAdapter(ArrayList<ShoppingList> shoppingLists, IndkoebslisterTab1Fragment fragment){

        currentFragment = fragment;
        this.shoppingLists = shoppingLists;

    }

    @Override
    public IndkoebslisterCardListRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(currentFragment.getActivity()).inflate(R.layout.shoppinglist_card, parent, false);

        return new ViewHolder(itemView,currentFragment);

    }

    // ligger indjoebslist model oplysninger i viewholder
    @Override
    public void onBindViewHolder(IndkoebslisterCardListRecycleAdapter.ViewHolder holder, int position) {

        ShoppingList l = shoppingLists.get(position);
        holder.vName.setText(l.name);
        holder.creationData.setText((l.creationTime.toString()).substring(0,10));
        holder.duoDate.setText((l.duoDate.toString()).substring(0, 10));
        holder.count.setText(shoppingLists.size()+"");
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }



}
