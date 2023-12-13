package com.example.scoutingapp2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchCustomAdapter extends RecyclerView.Adapter<MatchCustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id_ary, preMatch_ary, auton_ary, teleop_ary;
    Activity activity;


    //This is a custom adapter. It will take the data from the activity and divide the data into the rows.
    //We use this as a way to convert the raw array lists to a format a recycler view can use

    //We will get the array list from the activity, then set it to the ones in our class
    MatchCustomAdapter(Activity activity, Context context, ArrayList id, ArrayList preMatch,
                              ArrayList auton, ArrayList teleop)
    {
        this.activity = activity;
        this.context = context;
        id_ary = id;
        preMatch_ary = preMatch;
        teleop_ary = teleop;
        auton_ary = auton;
    }

    //All that is important is that the R.layout is the row layout you want to use
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.match_data_row, parent, false);
        return new MyViewHolder(view);
    }

    //Here, we are setting the text of the text views in the row to the position of that row in the list
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Log.d("path123", "hi");
        holder.preMatch_txt.setText(String.valueOf(id_ary.get(position)));
        holder.auton_txt.setText(String.valueOf(auton_ary.get(position)));
        holder.teleop_txt.setText(String.valueOf(teleop_ary.get(position)));

        //this handles what happens when someone clicks on the item
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update_Match.class);
                intent.putExtra("_id", Integer.parseInt(id_ary.get(position).toString()));
                activity.startActivityForResult(intent, 1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return id_ary.size();
    }

    //This finds the text boxes in the row to be used later
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView preMatch_txt, auton_txt, teleop_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            preMatch_txt = itemView.findViewById(R.id.preMatch_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            auton_txt = itemView.findViewById(R.id.auton_txt);
            teleop_txt = itemView.findViewById(R.id.teleop_txt);
        }
    }
}
