package com.example.faizan.fuelapp;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by faizan on 12/13/2017.
 */

public class historyCardAdapter extends RecyclerView.Adapter<historyCardAdapter.MyViewHolder> {

    Context context;

    public historyCardAdapter(Context context)
    {

        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.historycard , parent , false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, pickupLocation, dropLocation, fare, rideType, carNumber, carNumber2;

        public MyViewHolder(View itemView) {
            super(itemView);



            date = (TextView) itemView.findViewById(R.id.date);
            pickupLocation = (TextView) itemView.findViewById(R.id.pickupLocation);
            dropLocation = (TextView) itemView.findViewById(R.id.dropLocation);
            fare = (TextView) itemView.findViewById(R.id.fare);
            carNumber = (TextView) itemView.findViewById(R.id.carNumber);
            carNumber2 = (TextView) itemView.findViewById(R.id.carNumber2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.final_dialog);
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });



        }
    }
}
