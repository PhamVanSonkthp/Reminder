package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.infinity.reminder.R;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        addController();
    }

    private void addController() {
        txtName = findViewById(R.id.txt_name);
        txtName.setText(Storager.USER_APP.getUserData().getFullname());
    }

    public void addSchedule(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("id", Storager.USER_APP.getUserData().getId());
        startActivity(intent);
    }

    public void onAirSensor(View view) {
        Intent intent = new Intent(this, AirSensorActivity.class);
        intent.putExtra("id", Storager.USER_APP.getUserData().getId());
        startActivity(intent);
    }

    public void onMax30100Sensor(View view) {
        Intent intent = new Intent(this, Max30100SensorActivity.class);
        intent.putExtra("id", Storager.USER_APP.getUserData().getId());
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
                        Call<String> callback = dataClient.addAlert("Bearer " + Storager.USER_APP.getAccess_token(), Storager.USER_APP.getUserData().getId());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                if (response.code() == 403) {
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                                    File dir = getFilesDir();
                                    File file = new File(dir, Storager.FILE_INTERNAL);
                                    file.delete();

                                    Intent intent = new Intent(UserActivity.this, LoginActivity.class);
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

                    if (result.get(0).contains("Nhịp tim")) {
                        startActivity(new Intent(UserActivity.this, Max30100SensorActivity.class));
                    } else if (result.get(0).contains("Không khí")) {
                        startActivity(new Intent(UserActivity.this, AirSensorActivity.class));
                    } else if (result.get(0).contains("Lịch") || result.get(0).contains("Nhắc nhở")) {
                        startActivity(new Intent(UserActivity.this, ScheduleActivity.class));
                    }
                }
                break;
            }
            default: {
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                // if the intentResult is null then
                // toast a message as "cancelled"
                if (intentResult != null) {
                    if (intentResult.getContents() == null) {

                    } else {
                        // if the intentResult is not null we'll set
                        // the content and format of scan message
                        Log.e("AAAA", intentResult.getContents());
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }

        }
    }

    public void logout(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_logout);

        Button btnLogout = dialog.findViewById(R.id.dialog_logout_btn_logout);
        Button btnCancel = dialog.findViewById(R.id.dialog_logout_btn_cancel);

        btnLogout.setOnClickListener(v -> {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getId());
            File dir = getFilesDir();
            File file = new File(dir, Storager.FILE_INTERNAL);
            file.delete();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        btnCancel.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    private Bitmap generateQR(String content, int size) {
        Bitmap bitmap = null;
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(content,
                    BarcodeFormat.QR_CODE, size, size);
        } catch (WriterException e) {
            Log.e("generateQR()", e.getMessage());
        }
        return bitmap;
    }

    public void myQR(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_my_qr_code);
        ImageView myImage = dialog.findViewById(R.id.dialog_my_qr_code_img);
        myImage.setImageBitmap(generateQR(Storager.USER_APP.getUserData().getId() + "", getWindow().getDecorView().getWidth() / 2));
        dialog.show();

    }

    public void scanQR(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Quét mã");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    public void infor(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_infor);

        TextView txtID = dialog.findViewById(R.id.dialog_infor_txt_id);
        TextView txtName = dialog.findViewById(R.id.dialog_infor_txt_name);
        EditText edtIdDevice = dialog.findViewById(R.id.dialog_infor_edt_id_device);
        EditText edtChannel = dialog.findViewById(R.id.dialog_infor_edt_channel);
        Button btnSave = dialog.findViewById(R.id.dialog_infor_btn_save);

        edtIdDevice.setText(Storager.USER_APP.getUserData().getDevice_code());
        edtChannel.setText(Storager.USER_APP.getUserData().getChannel());

        dialog.findViewById(R.id.dialog_infor_txt_name_btn_close).setOnClickListener(v -> {
            dialog.cancel();
        });

        txtID.setText(Storager.USER_APP.getUserData().getId() + "");
        txtName.setText(Storager.USER_APP.getUserData().getFullname());

        btnSave.setOnClickListener(view1 -> {

        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void connectClock(View view) {
        startActivity(new Intent(this , ListDeviceActivity.class));
    }

    public void bleConnectClock(View view) {
        startActivity(new Intent(this , BLEConnectActivity.class));
    }

    public void wifi(View view) {
        startActivity(new Intent(this , WifiActivity.class));
    }
}
