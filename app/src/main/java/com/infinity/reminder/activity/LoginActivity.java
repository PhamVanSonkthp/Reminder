package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infinity.reminder.R;
import com.infinity.reminder.model.User;
import com.infinity.reminder.model.UserData;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName , edtPassword;
    private Dialog dialogProcessing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addController();
        checkLogined();
    }

    private void checkLogined() {
        showDialogProcessing();
        try {
            FileInputStream in = openFileInput(Storager.FILE_INTERNAL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            JSONObject data = new JSONObject(buffer.toString());

            cancelDialogProcessing();
            if (data.has("access_token")) {
                Storager.USER_APP = new User();
                Storager.USER_APP.setAccess_token(data.getString("access_token"));
                Storager.USER_APP.setToken_type(data.getString("token_type"));
                Storager.USER_APP.setExpires_in(data.getInt("expires_in"));
                UserData userData = new UserData();
                Storager.USER_APP.setUserData(userData);
                Storager.USER_APP.getUserData().setRole(data.getInt("role"));

                if(Storager.USER_APP.getUserData().getRole() == 1){
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

        } catch (IOException | JSONException ignored) {
            cancelDialogProcessing();
        }
    }

    private void addController() {

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);

        dialogProcessing = new Dialog(this);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);
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


    public void login(View view) {
        showDialogProcessing();
        String userName = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();

        DataClient dataClient = APIUtils.getData();
        Call<User> callback = dataClient.login(userName, password);
        callback.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                cancelDialogProcessing();
                if(response.code() == 200){
                    // đăng nhập thành công
                    Storager.USER_APP = response.body();

                    JSONObject jsonData = new JSONObject();
                    FileOutputStream fileOutputStream;

                    try {
                        jsonData.put("access_token", Storager.USER_APP.getAccess_token());
                        jsonData.put("token_type", Storager.USER_APP.getToken_type());
                        jsonData.put("expires_in", Storager.USER_APP.getExpires_in());
                        jsonData.put("id", Storager.USER_APP.getUserData().getId());
                        jsonData.put("username", Storager.USER_APP.getUserData().getUsername());
                        jsonData.put("email", Storager.USER_APP.getUserData().getEmail());
                        jsonData.put("fullname", Storager.USER_APP.getUserData().getFullname());
                        jsonData.put("phone", Storager.USER_APP.getUserData().getPhone());
                        jsonData.put("age", Storager.USER_APP.getUserData().getAge());
                        jsonData.put("role", Storager.USER_APP.getUserData().getRole());
                        jsonData.put("address", Storager.USER_APP.getUserData().getAddress());
                        jsonData.put("note", Storager.USER_APP.getUserData().getNote());
                        jsonData.put("job", Storager.USER_APP.getUserData().getJob());
                        jsonData.put("email_verified_at", Storager.USER_APP.getUserData().getEmail_verified_at());
                        jsonData.put("created_at", Storager.USER_APP.getUserData().getCreated_at());
                        jsonData.put("updated_at", Storager.USER_APP.getUserData().getUpdated_at());
                        fileOutputStream = openFileOutput(Storager.FILE_INTERNAL, Context.MODE_PRIVATE);
                        fileOutputStream.write(jsonData.toString().getBytes());
                        fileOutputStream.close();
                    } catch (JSONException | IOException e) {

                    }

                    if(Storager.USER_APP.getUserData().getRole() == 1){
                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }else {
                    // đăng nhập[ thất bại
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

            }
        });
    }
}