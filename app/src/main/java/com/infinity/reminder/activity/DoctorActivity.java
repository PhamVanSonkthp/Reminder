package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVRemind;
import com.infinity.reminder.adapter.AdapterRCVUser;
import com.infinity.reminder.model.Remind;
import com.infinity.reminder.model.User;
import com.infinity.reminder.model.UserData;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {

    RecyclerView rcvRemind;
    ArrayList<User> users;
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

        users.add(new User("","",1, new UserData(1,"","","Phạm xuân phong","0378xxxxxx",55,1,"","","","","","")));
        users.add(new User("","",1, new UserData(1,"","","Phạm xuân trường","0975xxxxx",55,1,"","","","","","")));
        users.add(new User("","",1, new UserData(1,"","","Phạm xuân vũ","037912xxx",55,1,"","","","","","")));

        adapterRCVUser = new AdapterRCVUser(this, users);
        rcvRemind.setAdapter(adapterRCVUser);
    }
}