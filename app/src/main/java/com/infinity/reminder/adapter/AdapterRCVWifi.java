package com.infinity.reminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.reminder.R;
import com.infinity.reminder.activity.AirSensorActivity;
import com.infinity.reminder.activity.Max30100SensorActivity;
import com.infinity.reminder.activity.ScheduleActivity;
import com.infinity.reminder.model_objects.UserData;
import com.infinity.reminder.model_objects.Wifi;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRCVWifi extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<Wifi.DataListWifi> arrItem;

    public AdapterRCVWifi(Context context, ArrayList<Wifi.DataListWifi> arrItem) {
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
        viewhodler.txtName.setText(arrItem.get(holder.getAdapterPosition()).getName());
        viewhodler.txtPhone.setText(arrItem.get(holder.getAdapterPosition()).getPass());

        viewhodler.btnUpdate.setOnClickListener(v -> {
//            Intent intent = new Intent(context , ScheduleActivity.class);
//            intent.putExtra("id" , arrItem.get(holder.getAdapterPosition()).getId());
//            context.startActivity(intent);
        });

        viewhodler.btnDelete.setOnClickListener(v -> {

            if(arrItem.size() > 0 && holder.getAdapterPosition() < arrItem.size()){
                arrItem.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }

            DataClient dataClient = APIUtils.getData();
            Call<String> callback = dataClient.deleteUserByDoctor("Bearer " + Storager.USER_APP.getAccess_token() , arrItem.get(position).getId());
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class Viewhodler extends RecyclerView.ViewHolder {
        TextView  txtName, txtPhone;
        ImageView btnUpdate, btnDelete;

        Viewhodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_wifi_txt_name);
            txtPhone = itemView.findViewById(R.id.item_rcv_wifi_txt_password);
            btnUpdate = itemView.findViewById(R.id.item_rcv_wifi_btn_update);
            btnDelete = itemView.findViewById(R.id.item_rcv_wifi_btn_delete);
        }
    }
}