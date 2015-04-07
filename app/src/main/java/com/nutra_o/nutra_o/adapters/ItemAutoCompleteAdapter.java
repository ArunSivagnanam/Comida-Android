package com.nutra_o.nutra_o.adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.nutra_o.nutra_o.activitys.IndkoebsListEditActivity;
import com.nutra_o.nutra_o.models.FoodInfo;
import com.nutra_o.nutra_o.service.FoodInfoAccesor;
import com.nutra_o.nutra_o.service.ServiceConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ars on 06-04-2015.
 */
public class ItemAutoCompleteAdapter extends ArrayAdapter<String> {

    protected static final String TAG = "SuggestionAdapter";
    private List<String> suggestions;
    public List<FoodInfo> foodInfoSuggestions;

    public ItemAutoCompleteAdapter(Activity context, String nameFilter) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<String>();

    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                // TODO lav til singleton senere, se om jeg kan bruge den i application objectet

                ServiceConnector connecter = new ServiceConnector();
                FoodInfoAccesor foodInfoAccesor = new FoodInfoAccesor(connecter);

                if (constraint != null) {


                    foodInfoSuggestions = foodInfoAccesor.seachFoodByName(constraint.toString());
                    suggestions.clear();
                    for (int i=0;i < foodInfoSuggestions.size();i++) {
                        suggestions.add(foodInfoSuggestions.get(i).DanName); // bruger det danske navn
                    }

                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

        };
        return myFilter;

    }
}
