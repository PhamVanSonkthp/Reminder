package com.infinity.reminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.infinity.reminder.R;

import java.util.ArrayList;
import java.util.Locale;

public class UserActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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
                        startActivity(new Intent(UserActivity.this , AddScheduleActivity.class));
                    }
                }
                break;
            }

        }
    }
}
