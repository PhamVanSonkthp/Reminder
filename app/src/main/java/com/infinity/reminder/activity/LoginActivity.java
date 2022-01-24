package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.infinity.reminder.R;
import com.infinity.reminder.model_objects.DataRegister;
import com.infinity.reminder.model_objects.User;
import com.infinity.reminder.model_objects.UserData;
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
                Storager.USER_APP.getUserData().setId(data.getInt("id"));
                Storager.USER_APP.getUserData().setFullname(data.getString("fullname"));

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
                if(response.code() == 200 && response.body().getUserData() != null){
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
                    FirebaseMessaging.getInstance().subscribeToTopic("id-" + Storager.USER_APP.getUserData().getId());

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
                cancelDialogProcessing();
            }
        });
    }

    public void signUp(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_register);

        EditText edtUserName = dialog.findViewById(R.id.dialog_register_edt_user_name);
        EditText edtPassword = dialog.findViewById(R.id.dialog_register_edt_password);
        EditText edtName = dialog.findViewById(R.id.dialog_register_edt_name);

        dialog.findViewById(R.id.dialog_register_btn_close).setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.findViewById(R.id.dialog_register_btn_register).setOnClickListener(v -> {
            DataClient dataClient = APIUtils.getData();
            Call<DataRegister> callback = dataClient.register(
                    edtUserName.getText().toString(),
                    edtPassword.getText().toString(),
                    edtName.getText().toString(),
                    "abc@gmail.com",
                    "0378115555",
                    58,
                    1,
                    "hn",
                    1,
                    "[]",
                    "[]",
                    "[]"
            );
            callback.enqueue(new Callback<DataRegister>() {
                @Override
                public void onResponse(@NonNull Call<DataRegister> call, @NonNull Response<DataRegister> response) {
                    cancelDialogProcessing();
                    if(response.code() == 200){
                        DataRegister dataRegister = response.body();
                        Log.e("AAAA" , dataRegister.getCode());
                        if(dataRegister.getCode().equals("00")){
                            dialog.cancel();
                            Toast.makeText(LoginActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }else if(dataRegister.getCode().equals("04")){
                            dialog.cancel();
                            Toast.makeText(LoginActivity.this, "Mật khẩu không được dưới 6 ký tự", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Có lỗi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DataRegister> call, @NonNull Throwable t) {
                    cancelDialogProcessing();
                }
            });
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}