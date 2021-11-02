package com.infinity.reminder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.infinity.reminder.R;
import com.infinity.reminder.adapter.AdapterRCVRemind;
import com.infinity.reminder.model.RecordChat;
import com.infinity.reminder.model.Remind;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordActivity extends AppCompatActivity {

    Button btnRecord;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private final int currentFormat = 1;
    private final int[] output_formats = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};
    private final String[] file_exts = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP};
    private String uriFile;
    private RecyclerView rcvRecord;
    ArrayList<RecordChat> recordChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        addController();
        addEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addEvents() {
        btnRecord.setOnTouchListener((v, event) -> {
            // TODO Auto-generated method stub
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    btnRecord.setText("Đang ghi...");
                    startRecording();
                    return true;
                case MotionEvent.ACTION_UP:
                    stopRecording();
                    break;
            }
            return false;
        });
    }

    private void addController() {
        btnRecord = findViewById(R.id.btn_record);
        rcvRecord = findViewById(R.id.rcv_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRecord.setLayoutManager(linearLayoutManager);
        rcvRecord.setNestedScrollingEnabled(false);
        recordChats = new ArrayList<>();
        adapterRCVRemind = new AdapterRCVRemind(this, reminds);
        rcvRecord.setAdapter(adapterRCVRemind);

    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }

    private void startRecording() {
        uriFile = getFilename();
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(uriFile);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        try {
            if (null != recorder) {
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;
                btnRecord.setText("Đang gửi");
                btnRecord.setEnabled(false);
            }
        }catch (Exception e){
            btnRecord.setText("Giữ và nói");
        }
//        Log.e("AAAA" , uriFile);
//
//        File file = new File(uriFile);
//
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), uriFile);
//
//        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);
//
//        DataClient dataClient = APIUtils.getData();
//        Call<ResponseBody> responseBodyCall = dataClient.addRecord(multipartBody);
//        responseBodyCall.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("Success", "success "+response.code());
//                Log.d("Success", "success "+response.message());
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("failure", "message = " + t.getMessage());
//                Log.d("failure", "cause = " + t.getCause());
//            }
//        });

    }
}