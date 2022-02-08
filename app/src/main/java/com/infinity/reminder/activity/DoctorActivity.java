package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVUser;
import com.infinity.reminder.model_objects.DataListUserByManager;
import com.infinity.reminder.model_objects.UserData;
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
    private Dialog dialogProcessing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        addController();

    }

    private void loadData(){
        showDialogProcessing();
        DataClient dataClient = APIUtils.getData();
        Call<DataListUserByManager> callback = dataClient.getListUserByManager("Bearer " + Storager.USER_APP.getAccess_token());
        callback.enqueue(new Callback<DataListUserByManager>() {
            @Override
            public void onResponse(@NonNull Call<DataListUserByManager> call, @NonNull Response<DataListUserByManager> response) {
                if(response.code() == 200){
                    users = (ArrayList<UserData>) response.body().getData();
                    adapterRCVUser = new AdapterRCVUser(DoctorActivity.this, users);
                    rcvRemind.setAdapter(adapterRCVUser);
                    cancelDialogProcessing();
                }else if (response.code() == 403){
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
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
                Toast.makeText(DoctorActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
                cancelDialogProcessing();
            }
        });
    }

    private void addController() {
        // ánh xạ
        rcvRemind = findViewById(R.id.rcv_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRemind.setLayoutManager(linearLayoutManager);
        rcvRemind.setNestedScrollingEnabled(false);
        users = new ArrayList<>();

        dialogProcessing = new Dialog(this);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);

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

    public void logout(View view) {

        FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getId());
        File dir = getFilesDir();
        File file = new File(dir, Storager.FILE_INTERNAL);
        file.delete();

        Intent intent = new Intent(this , LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void scanQR(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Quét mã");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {

                break;
            }
            default: {
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                // if the intentResult is null then
                // toast a message as "cancelled"
                if (intentResult != null) {
                    if (intentResult.getContents() != null) {
                        showDialogProcessing();
                        DataClient dataClient = APIUtils.getData();
                        Call<String> callback = dataClient.addUserByDoctor("Bearer " + Storager.USER_APP.getAccess_token() , intentResult.getContents());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                loadData();
                            }

                            @Override
                            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                cancelDialogProcessing();
                                Toast.makeText(DoctorActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }

        }
    }
}