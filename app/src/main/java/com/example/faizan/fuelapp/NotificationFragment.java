package com.example.faizan.fuelapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class NotificationFragment extends Fragment {
    RecyclerView notificationRecycler;
    notificationCardAdapter adapter;
    GridLayoutManager manager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification,container,false);

        notificationRecycler = (RecyclerView) view.findViewById(R.id.notificationRecycle);
        notificationRecycler = view.findViewById(R.id.notificationRecycle);
        manager = new GridLayoutManager(getContext(),1);
        adapter = new notificationCardAdapter(getContext());
        notificationRecycler.setLayoutManager(manager);
        notificationRecycler.setAdapter(adapter);

        return view;
    }





}
