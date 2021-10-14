package com.infinity.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.infinity.reminder.adapter.AdapterRCVRemind;
import com.infinity.reminder.model.Remind;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView rcvRemind;
    ArrayList<Remind> reminds;
    AdapterRCVRemind adapterRCVRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ánh xạ
        rcvRemind = findViewById(R.id.rcv_remind);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRemind.setLayoutManager(linearLayoutManager);
        rcvRemind.setNestedScrollingEnabled(false);
        reminds = new ArrayList<>();
        adapterRCVRemind = new AdapterRCVRemind(this, reminds);
        rcvRemind.setAdapter(adapterRCVRemind);

        // fetch data from server
        DataClient dataClient = APIUtils.getData();
        final Call<List<Remind>> callback = dataClient.getRemind();
        callback.enqueue(new Callback<List<Remind>>() {
            @Override
            public void onResponse(@NonNull Call<List<Remind>> call, @NonNull Response<List<Remind>> response) {
                ArrayList<Remind> arrayList = (ArrayList<Remind>) response.body();
                if(arrayList != null) reminds.addAll(arrayList);
                adapterRCVRemind.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Remind>> call, @NonNull Throwable t) {

            }
        });

        // thêm data remind vào server

        FirebaseMessaging.getInstance().subscribeToTopic("reminder");

    }
}