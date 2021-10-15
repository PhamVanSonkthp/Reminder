package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.infinity.reminder.R;
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

    EditText edtTitle, edtTime;
    Button btnAdd;

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
        Call<List<Remind>> callback = dataClient.getRemind();
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

        btnAdd = findViewById(R.id.btnAdd);
        edtTitle = findViewById(R.id.edtTitle);
        edtTime = findViewById(R.id.edtTime);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String time = edtTime.getText().toString();

                Call<String> callback = dataClient.addRemind(title , time);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }
}
