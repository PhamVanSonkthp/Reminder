package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVWifi;
import com.infinity.reminder.model_objects.DataListWifi;
import com.infinity.reminder.model_objects.Wifi;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WifiActivity extends AppCompatActivity {

    RecyclerView rcvWifi;
    ArrayList<DataListWifi> wifis;
    AdapterRCVWifi adapterRCVWifi;
    private Dialog dialogProcessing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        addController();
    }

    private void loadData(){
        showDialogProcessing();
        DataClient dataClient = APIUtils.getData();
        Call<Wifi> callback = dataClient.getListWifi("Bearer " + Storager.USER_APP.getAccess_token() , Storager.USER_APP.getUserData().getDevice_code());
        callback.enqueue(new Callback<Wifi>() {
            @Override
            public void onResponse(@NonNull Call<Wifi> call, @NonNull Response<Wifi> response) {
                if(response.code() == 200){
                    wifis = (ArrayList<DataListWifi>) response.body().getData().getData();
                    adapterRCVWifi = new AdapterRCVWifi(WifiActivity.this, wifis);
                    rcvWifi.setAdapter(adapterRCVWifi);
                    cancelDialogProcessing();
                }else if (response.code() == 403){
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                    File dir = getFilesDir();
                    File file = new File(dir, Storager.FILE_INTERNAL);
                    file.delete();

                    Intent intent = new Intent(WifiActivity.this , LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Wifi> call, @NonNull Throwable t) {
                Toast.makeText(WifiActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
                cancelDialogProcessing();
            }
        });
    }

    private void addController() {
        rcvWifi = findViewById(R.id.rcv_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvWifi.setLayoutManager(linearLayoutManager);
        rcvWifi.setNestedScrollingEnabled(false);
        wifis = new ArrayList<>();

        dialogProcessing = new Dialog(this);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);

//        wifis.add(new DataListWifi(1,"1","1",1,1,"1","1"));
//        adapterRCVWifi = new AdapterRCVWifi(WifiActivity.this, wifis);
//        rcvWifi.setAdapter(adapterRCVWifi);

        loadData();
    }

    private void showDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.show();
        }
    }

    private void cancelDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.dismiss();
        }
    }

    public void back(View view) {
        finish();
    }

    public void addWifi(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_transfer_wifi);

        EditText edtName = dialog.findViewById(R.id.dialog_add_wifi_edt_name);
        EditText edtPassword = dialog.findViewById(R.id.dialog_add_wifi_edt_password);
        Button btnClose = dialog.findViewById(R.id.dialog_add_wifi_btn_close);
        Button btnTransfer = dialog.findViewById(R.id.dialog_add_wifi_btn_add);

        btnClose.setOnClickListener(view1 -> {
            dialog.cancel();
        });

        btnTransfer.setOnClickListener(view1 -> {
            showDialogProcessing();
            DataClient dataClient = APIUtils.getData();
            Call<String> callback = dataClient.addListWifi("Bearer " + Storager.USER_APP.getAccess_token() , Storager.USER_APP.getUserData().getDevice_code() , edtName.getText().toString(), edtPassword.getText().toString());
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if(response.code() == 200){
                        loadData();
                        cancelDialogProcessing();
                        dialog.cancel();
                    }else if (response.code() == 403){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                        File dir = getFilesDir();
                        File file = new File(dir, Storager.FILE_INTERNAL);
                        file.delete();

                        Intent intent = new Intent(WifiActivity.this , LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(WifiActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
                    cancelDialogProcessing();
                }
            });
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}