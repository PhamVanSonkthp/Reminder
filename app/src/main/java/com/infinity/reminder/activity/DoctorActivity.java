package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVUser;
import com.infinity.reminder.model.DataListUserByManager;
import com.infinity.reminder.model.UserData;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

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
        addEvents();
    }

    private void addEvents() {

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
                users = (ArrayList<UserData>) response.body().getData();
                adapterRCVUser = new AdapterRCVUser(DoctorActivity.this, users);
                rcvRemind.setAdapter(adapterRCVUser);
            }

            @Override
            public void onFailure(@NonNull Call<DataListUserByManager> call, @NonNull Throwable t) {

            }
        });


    }
}