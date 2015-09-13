package com.uxcasuals.waves.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uxcasuals.waves.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerCollapsedFragment extends Fragment {


    public PlayerCollapsedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_collapsed, container, false);
    }


}
