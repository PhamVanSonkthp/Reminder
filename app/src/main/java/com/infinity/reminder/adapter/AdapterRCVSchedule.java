package com.infinity.reminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.reminder.R;
import com.infinity.reminder.activity.ScheduleActivity;
import com.infinity.reminder.model.DataSchedule;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRCVSchedule extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<DataSchedule.DataListSchedule> arrItem;

    public AdapterRCVSchedule(Context context, ArrayList<DataSchedule.DataListSchedule> arrItem) {
        this.context = context;
        this.arrItem = arrItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_schedule, parent, false);
        return new Viewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Viewhodler viewhodler = (Viewhodler) holder;
        viewhodler.txtTitle.setText(arrItem.get(position).getContent());
        viewhodler.txtTime.setText(arrItem.get(position).getTime());

        DataClient dataClient = APIUtils.getData();
        Call<DataSchedule> callback = dataClient.getSchedule("Bearer " + Storager.USER_APP.getAccess_token() , id);
        callback.enqueue(new Callback<DataSchedule>() {
            @Override
            public void onResponse(@NonNull Call<DataSchedule> call, @NonNull Response<DataSchedule> response) {
                if(response.code() == 200){

                }else{

                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSchedule> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class Viewhodler extends RecyclerView.ViewHolder {
        TextView txtTitle , txtTime;
        ImageView imgDelete;

        Viewhodler(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_rcv_remind_txt_title);
            txtTime = itemView.findViewById(R.id.item_rcv_remind_txt_time);
            imgDelete = itemView.findViewById(R.id.item_rcv_chedue_img_delete);
        }
    }
}