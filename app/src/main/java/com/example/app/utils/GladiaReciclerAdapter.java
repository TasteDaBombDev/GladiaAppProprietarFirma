package com.example.app.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.userScreen.previzEvent.PrevizEventMain;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GladiaReciclerAdapter extends RecyclerView.Adapter<GladiaReciclerAdapter.GladiaReciclerViewHolder> {

    private ArrayList<EventDetails> events;

    public GladiaReciclerAdapter(ArrayList<EventDetails> events){
        this.events = events;
    }

    @NonNull
    @Override
    public GladiaReciclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        GladiaReciclerViewHolder rvh = new GladiaReciclerViewHolder(v);

        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GladiaReciclerViewHolder holder, final int position) {
        final EventDetails currentItem = events.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PrevizEventMain.class);
                intent.putExtra("ID",currentItem.getId());
                v.getContext().startActivity(intent);
            }
        });

        Picasso.get().load(currentItem.getImgPath()).into(holder.pic);
        holder.names.setText(currentItem.getName());
        holder.hour.setText(currentItem.getHour());
        holder.date.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class GladiaReciclerViewHolder extends  RecyclerView.ViewHolder{

        public ImageView pic;
        public TextView names,date,hour;


        public GladiaReciclerViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.eventPic);
            names = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.eventDate);
            hour = itemView.findViewById(R.id.eventOra);
        }
    }

}
