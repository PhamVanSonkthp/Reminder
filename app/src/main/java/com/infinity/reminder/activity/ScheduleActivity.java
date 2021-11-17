package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVRemind;
import com.infinity.reminder.canvas.CustomDateTimePicker;
import com.infinity.reminder.model.DataSchedule;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    EditText edtTitle;
    TextView txtTime;

    RecyclerView rcvRemind;
    ArrayList<DataSchedule.DataListSchedule> dataSchedules;
    AdapterRCVRemind adapterRCVRemind;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        addController();
        addEvents();
        webService();
    }

    private void webService() {
        // fetch data from server
        DataClient dataClient = APIUtils.getData();
        Call<DataSchedule> callback = dataClient.getSchedule("Bearer " + Storager.USER_APP.getAccess_token() , id);
        callback.enqueue(new Callback<DataSchedule>() {
            @Override
            public void onResponse(@NonNull Call<DataSchedule> call, @NonNull Response<DataSchedule> response) {
                dataSchedules.addAll(response.body().getData().getData());
                adapterRCVRemind.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<DataSchedule> call, @NonNull Throwable t) {

            }
        });
    }

    private void addEvents() {
        txtTime.setOnClickListener(v -> {
            CustomDateTimePicker custom;
            custom = new CustomDateTimePicker(this,
                    new CustomDateTimePicker.ICustomDateTimeListener() {

                        @Override
                        public void onSet(Dialog dialog, Calendar calendarSelected,
                                          Date dateSelected, int year, String monthFullName,
                                          String monthShortName, int monthNumber, int day,
                                          String weekDayFullName, String weekDayShortName,
                                          int hour24, int hour12, int min, int sec,
                                          String AM_PM) {
                            //                        ((TextInputEditText) findViewById(R.id.edtEventDateTime))
                            txtTime.setText("");
                            txtTime.setText(year
                                    + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                    + " " + hour24 + ":" + min
                                    + ":" + sec);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            /**
             * Pass Directly current time format it will return AM and PM if you set
             * false
             */
            custom.set24HourFormat(true);
            /**
             * Pass Directly current data and time to show when it pop up
             */
            custom.setDate(Calendar.getInstance());

            custom.showDialog();
        });
    }

    private void addController() {
        id = getIntent().getIntExtra("id", 0);
        edtTitle = findViewById(R.id.edt_content);
        txtTime = findViewById(R.id.txtTime);

        rcvRemind = findViewById(R.id.rcv_remind);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRemind.setLayoutManager(linearLayoutManager);
        rcvRemind.setNestedScrollingEnabled(false);
        dataSchedules = new ArrayList<>();
        adapterRCVRemind = new AdapterRCVRemind(this, dataSchedules);
        rcvRemind.setAdapter(adapterRCVRemind);
    }

    public void addSchedule(View view) {
        String title = edtTitle.getText().toString();
        String time = txtTime.getText().toString();
        DataClient dataClient = APIUtils.getData();
        Call<String> callback = dataClient.addRemind("Bearer " + Storager.USER_APP.getAccess_token() , title, time , 1 , id);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }
}