package com.infinity.reminder.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.infinity.reminder.R;
import com.infinity.reminder.activity.LoginActivity;
import com.infinity.reminder.activity.WifiActivity;
import com.infinity.reminder.model_objects.DataListWifi;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRCVWifi extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<DataListWifi> arrItem;

    public AdapterRCVWifi(Context context, ArrayList<DataListWifi> arrItem) {
        this.context = context;
        this.arrItem = arrItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_wifi, parent, false);
        return new Viewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Viewhodler viewhodler = (Viewhodler) holder;
        viewhodler.txtName.setText(arrItem.get(holder.getAdapterPosition()).getName());
        viewhodler.txtPhone.setText(arrItem.get(holder.getAdapterPosition()).getPass());

        viewhodler.btnUpdate.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_add_wifi);

            EditText edtName = dialog.findViewById(R.id.dialog_add_wifi_edt_name);
            EditText edtPassword = dialog.findViewById(R.id.dialog_add_wifi_edt_password);
            ImageButton btnClose = dialog.findViewById(R.id.dialog_add_wifi_btn_close);
            Button btnTransfer = dialog.findViewById(R.id.dialog_add_wifi_btn_add);

            btnClose.setOnClickListener(view1 -> {
                dialog.cancel();
            });

            btnTransfer.setOnClickListener(view1 -> {
                DataClient dataClient = APIUtils.getData();
                Call<String> callback = dataClient.updateListWifi("Bearer " + Storager.USER_APP.getAccess_token() , arrItem.get(holder.getAdapterPosition()).getId()+"" , edtName.getText().toString(), edtPassword.getText().toString(), arrItem.get(holder.getAdapterPosition()).getStatus());
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if(response.code() == 200){
                            dialog.cancel();
                        }else if (response.code() == 403){
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                            File dir = context.getFilesDir();
                            File file = new File(dir, Storager.FILE_INTERNAL);
                            file.delete();

                            Intent intent = new Intent(context , LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Toast.makeText(context , t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        });

        viewhodler.btnDelete.setOnClickListener(v -> {
            DataClient dataClient = APIUtils.getData();
            Call<String> callback = dataClient.deleteListWifi("Bearer " + Storager.USER_APP.getAccess_token() , arrItem.get(holder.getAdapterPosition()).getId()+"" );
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.code() == 403){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                        File dir = context.getFilesDir();
                        File file = new File(dir, Storager.FILE_INTERNAL);
                        file.delete();

                        Intent intent = new Intent(context , LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }

                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(context , t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
            if(arrItem.size() > 0 && holder.getAdapterPosition() < arrItem.size()){
                arrItem.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
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