package com.example.faizan.fuelapp;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HistoryFragment extends Fragment {

    RecyclerView grid;
    historyCardAdapter adapter;
    GridLayoutManager manager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history,container,false);

        grid = (RecyclerView) view.findViewById(R.id.gridHistory);

        grid = view.findViewById(R.id.gridHistory);
        manager = new GridLayoutManager(getContext() , 1);
        adapter = new historyCardAdapter(getContext());
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);






        return view;
    }






}
