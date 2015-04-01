package com.nutra_o.nutra_o.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutra_o.nutra_o.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndkoebslisterFragment extends Fragment {


    public IndkoebslisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indkoebslister, container, false);
    }


}
