package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.firebase.messaging.FirebaseMessaging;
import com.infinity.reminder.R;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public void addSchedule(View view) {
        Intent intent = new Intent(this , ScheduleActivity.class);
        intent.putExtra("id" , Storager.USER_APP.getUserData().getId());
        startActivity(intent);
    }

    public void onAirSensor(View view) {
        Intent intent = new Intent(this , AirSensorActivity.class);
        intent.putExtra("id" , Storager.USER_APP.getUserData().getId());
        startActivity(intent);
    }

    public void onMax30100Sensor(View view) {
        Intent intent = new Intent(this , Max30100SensorActivity.class);
        intent.putExtra("id" , Storager.USER_APP.getUserData().getId());
        startActivity(intent);
    }

    public void alert(View view) {
        new AwesomeWarningDialog(this)
                .setTitle("Cảnh báo")
                .setMessage("Cảnh báo tới bác sĩ và người nhà?")
                .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_notice, R.color.white)
                .setCancelable(true)
                .setButtonText("Phát ngay")
                .setButtonBackgroundColor(R.color.dialogNoticeBackgroundColor)
                .setButtonTextColor(R.color.white)
                .setWarningButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        DataClient dataClient = APIUtils.getData();
                        Call<String> callback = dataClient.addAlert("Bearer " + Storager.USER_APP.getAccess_token() , Storager.USER_APP.getUserData().getId());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                if (response.code() == 403){
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                                    File dir = getFilesDir();
                                    File file = new File(dir, Storager.FILE_INTERNAL);
                                    file.delete();

                                    Intent intent = new Intent(UserActivity.this , LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                            }
                        });

                    }
                })
                .show();
    }

    public void assistant(View view) {
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));

                    if(result.get(0).contains("Nhịp tim")){
                        startActivity(new Intent(UserActivity.this , Max30100SensorActivity.class));
                    }else if(result.get(0).contains("Không khí")){
                        startActivity(new Intent(UserActivity.this , AirSensorActivity.class));
                    }else if(result.get(0).contains("Lịch") || result.get(0).contains("Nhắc nhở")){
                        startActivity(new Intent(UserActivity.this , ScheduleActivity.class));
                    }
                }
                break;
            }

        }
    }

    public void logout(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_logout);

        Button btnLogout = dialog.findViewById(R.id.dialog_logout_btn_logout);
        Button btnCancel = dialog.findViewById(R.id.dialog_logout_btn_cancel);

        btnLogout.setOnClickListener(v -> {
            File dir = getFilesDir();
            File file = new File(dir, Storager.FILE_INTERNAL);
            file.delete();

            Intent intent = new Intent(this , LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        btnCancel.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }
}
