package com.example.faizan.fuelapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by faizan on 12/13/2017.
 */

public class notificationCardAdapter extends RecyclerView.Adapter<notificationCardAdapter.MyViewHolder> {

    Context context;
    public notificationCardAdapter(Context context){
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notificationcard, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(notificationCardAdapter.MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notifydate, notifyContent;

        public MyViewHolder(View itemView) {
            super(itemView);

            notifydate = (TextView) itemView.findViewById(R.id.notifyDate);
            notifyContent = (TextView) itemView.findViewById(R.id.notifyContent);
        }
    }
}
