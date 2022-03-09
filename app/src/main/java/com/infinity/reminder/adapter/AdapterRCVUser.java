package com.infinity.reminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.reminder.R;
import com.infinity.reminder.activity.AlertActivity;
import com.infinity.reminder.activity.ScheduleActivity;
import com.infinity.reminder.activity.AirSensorActivity;
import com.infinity.reminder.activity.Max30100SensorActivity;
import com.infinity.reminder.model_objects.UserData;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRCVUser extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<UserData> arrItem;

    public AdapterRCVUser(Context context, ArrayList<UserData> arrItem) {
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
        viewhodler.txtName.setText(arrItem.get(holder.getAdapterPosition()).getFullname());
        viewhodler.txtPhone.setText(arrItem.get(holder.getAdapterPosition()).getPhone());
        viewhodler.txtAge.setText(arrItem.get(holder.getAdapterPosition()).getAge()+"");

        viewhodler.btnSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(context , ScheduleActivity.class);
            intent.putExtra("id" , arrItem.get(holder.getAdapterPosition()).getId());
            context.startActivity(intent);
        });

        viewhodler.btnAlert.setOnClickListener(v -> {
            Intent intent = new Intent(context , AlertActivity.class);
            intent.putExtra("id" , arrItem.get(holder.getAdapterPosition()).getId());
            context.startActivity(intent);
        });

        viewhodler.btnHeart.setOnClickListener(v -> {
            Intent intent = new Intent(context , Max30100SensorActivity.class);
            intent.putExtra("id" , arrItem.get(holder.getAdapterPosition()).getId());

            Log.e("AAAA" , arrItem.get(holder.getAdapterPosition()).getId()+"");
            Log.e("AAAA" , Storager.USER_APP.getAccess_token());
            context.startActivity(intent);
        });

        viewhodler.btnAir.setOnClickListener(v -> {
            Intent intent = new Intent(context , AirSensorActivity.class);
            intent.putExtra("id" , arrItem.get(holder.getAdapterPosition()).getId());
            context.startActivity(intent);
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
        TextView  txtName, txtPhone, txtAge;
        ImageView btnSchedule,btnHeart,btnAir, btnDelete, btnAlert;

        Viewhodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_user_txt_name);
            txtPhone = itemView.findViewById(R.id.item_rcv_user_txt_phone);
            txtAge = itemView.findViewById(R.id.item_rcv_user_txt_age);
            btnSchedule = itemView.findViewById(R.id.item_rcv_user_btn_schedule);
            btnAlert = itemView.findViewById(R.id.item_rcv_user_btn_alert);
            btnHeart = itemView.findViewById(R.id.item_rcv_user_btn_heart);
            btnAir = itemView.findViewById(R.id.item_rcv_user_btn_air);
            btnDelete = itemView.findViewById(R.id.item_rcv_user_btn_delete);
        }
    }
}