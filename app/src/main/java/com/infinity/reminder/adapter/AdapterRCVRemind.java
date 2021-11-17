package com.infinity.reminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.reminder.R;
import com.infinity.reminder.model.DataSchedule;

import java.util.ArrayList;

public class AdapterRCVRemind extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<DataSchedule.DataListSchedule> arrItem;

    public AdapterRCVRemind(Context context, ArrayList<DataSchedule.DataListSchedule> arrItem) {
        this.context = context;
        this.arrItem = arrItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_remind, parent, false);
        return new Viewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Viewhodler viewhodler = (Viewhodler) holder;
        viewhodler.txtTitle.setText(arrItem.get(position).getContent());
        viewhodler.txtTime.setText(arrItem.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class Viewhodler extends RecyclerView.ViewHolder {
        TextView txtTitle , txtTime;

        Viewhodler(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_rcv_remind_txt_title);
            txtTime = itemView.findViewById(R.id.item_rcv_remind_txt_time);
        }
    }
}