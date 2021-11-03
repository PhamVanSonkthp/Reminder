package com.infinity.reminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.reminder.R;
import com.infinity.reminder.helper.MusicPlayer;
import com.infinity.reminder.model.RecordChat;
import com.infinity.reminder.model.Remind;

import java.util.ArrayList;

public class AdapterRCVRecord extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<RecordChat> arrItem;

    public AdapterRCVRecord(Context context, ArrayList<RecordChat> arrItem) {
        this.context = context;
        this.arrItem = arrItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_record_chat, parent, false);
        return new Viewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Viewhodler viewhodler = (Viewhodler) holder;
        viewhodler.btnPlayAudio.setOnClickListener(v -> {
            MusicPlayer.playAudio(context , arrItem.get(position).getUrlAudio());
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class Viewhodler extends RecyclerView.ViewHolder {
        TextView txtTitle;
        Button btnPlayAudio;

        Viewhodler(@NonNull View itemView) {
            super(itemView);
            btnPlayAudio = itemView.findViewById(R.id.item_rcv_record_btn_play_audio);
        }
    }
}