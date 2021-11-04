package com.infinity.reminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.reminder.R;
import com.infinity.reminder.model.Remind;
import com.infinity.reminder.model.User;

import java.util.ArrayList;

public class AdapterRCVUser extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<User> arrItem;

    public AdapterRCVUser(Context context, ArrayList<User> arrItem) {
        this.context = context;
        this.arrItem = arrItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_user, parent, false);
        return new Viewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Viewhodler viewhodler = (Viewhodler) holder;
        viewhodler.txtName.setText(arrItem.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class Viewhodler extends RecyclerView.ViewHolder {
        TextView  txtName;

        Viewhodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_user_txt_name);
        }
    }
}