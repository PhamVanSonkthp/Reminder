package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addSchedule(View view) {
        startActivity(new Intent(this , AddScheduleActivity.class));
    }

    public void onAirSensor(View view) {
        startActivity(new Intent(this , AirSensorActivity.class));
    }

    public void onMax30100Sensor(View view) {
        startActivity(new Intent(this , Max30100SensorActivity.class));
    }

    public void alert(View view) {

    }
}
