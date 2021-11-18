package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVUser;
import com.infinity.reminder.model.DataListUserByManager;
import com.infinity.reminder.model.UserData;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {

    RecyclerView rcvRemind;
    ArrayList<UserData> users;
    AdapterRCVUser adapterRCVUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        addController();
    }

    private void addController() {
        // ánh xạ
        rcvRemind = findViewById(R.id.rcv_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRemind.setLayoutManager(linearLayoutManager);
        rcvRemind.setNestedScrollingEnabled(false);
        users = new ArrayList<>();

        DataClient dataClient = APIUtils.getData();
        Call<DataListUserByManager> callback = dataClient.getListUserByManager("Bearer " + Storager.USER_APP.getAccess_token());
        callback.enqueue(new Callback<DataListUserByManager>() {
            @Override
            public void onResponse(@NonNull Call<DataListUserByManager> call, @NonNull Response<DataListUserByManager> response) {
                if(response.code() == 200){
                    users = (ArrayList<UserData>) response.body().getData();
                    adapterRCVUser = new AdapterRCVUser(DoctorActivity.this, users);
                    rcvRemind.setAdapter(adapterRCVUser);
                }else if (response.code() == 403){
                    File dir = getFilesDir();
                    File file = new File(dir, Storager.FILE_INTERNAL);
                    file.delete();

                    Intent intent = new Intent(DoctorActivity.this , LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(@NonNull Call<DataListUserByManager> call, @NonNull Throwable t) {

            }
        });


    }

    public void logout(View view) {
        File dir = getFilesDir();
        File file = new File(dir, Storager.FILE_INTERNAL);
        file.delete();

        Intent intent = new Intent(this , LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}