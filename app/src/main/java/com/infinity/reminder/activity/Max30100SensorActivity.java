package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.messaging.FirebaseMessaging;
import com.infinity.reminder.R;
import com.infinity.reminder.helper.Protecter;
import com.infinity.reminder.model_objects.DataListAir;
import com.infinity.reminder.model_objects.DataListMax30100;
import com.infinity.reminder.model_objects.DataMax30100;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Max30100SensorActivity extends AppCompatActivity {

    private LineChart lineChart;
    private int id;
    private EditText edtTime;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    TextView txtBMPMin , txtBMPMax , txtBMPMedium;
    TextView txtSPO2Min , txtSPO2Max , txtSPO2Medium;
    TextView txtIRMin , txtIRMax , txtIRMedium;

    ImageView imgRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max30100_sensor);
        addController();
        addEvents();

        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getData();
                h.postDelayed(this, 5000);
                scaleView();
            }
        }, 0);
    }

    private void scaleView() {
        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        imgRing.startAnimation(scaleDown);
    }

    private void addEvents() {
        edtTime.setOnClickListener(v -> {
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void getData() {
        DataClient dataClient = APIUtils.getData();
        Call<DataMax30100> callback = dataClient.getMax30100("Bearer " + Storager.USER_APP.getAccess_token() , edtTime.getText().toString(), id);
        callback.enqueue(new Callback<DataMax30100>() {
            @Override
            public void onResponse(@NonNull Call<DataMax30100> call, @NonNull Response<DataMax30100> response) {
                if(response.code() == 200){
                    lineChart.setData(generateDataLine(response.body().getData().getData() ));

//                    List<DataListMax30100> dataListMax30100s
//                             = new ArrayList<>();
//                    dataListMax30100s.add(new DataListMax30100(1,1,15,10,10,"",""));
//                    dataListMax30100s.add(new DataListMax30100(1,1,88,46,35,"",""));
//                    dataListMax30100s.add(new DataListMax30100(1,1,46,46,86,"",""));
//
//
//
//                    lineChart.setData(generateDataLine(dataListMax30100s));
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();

                    List<DataListMax30100> dataListAirs = response.body().getData().getData();
                    float minBMP = 0 , maxBMP = 0, totalBMP = 0,
                            minSPO2 = 0 , maxSPO2 = 0, totalSPO2 = 0,
                            minIR = 0 , maxIR = 0, totalIR = 0;
                    if(dataListAirs.size() > 0){
                        minBMP = dataListAirs.get(0).getBmp();
                        maxBMP = dataListAirs.get(0).getBmp();
                        minSPO2 = dataListAirs.get(0).getSpo2();
                        maxSPO2 = dataListAirs.get(0).getSpo2();
                        minIR = dataListAirs.get(0).getIr();
                        maxIR = dataListAirs.get(0).getIr();
                    }

                    for(int i = 0 ; i < dataListAirs.size() ; i++){
                        if(dataListAirs.get(i).getBmp() < minBMP){
                            minBMP = dataListAirs.get(i).getBmp();
                        }
                        if(dataListAirs.get(i).getBmp() > maxBMP){
                            maxBMP = dataListAirs.get(i).getBmp();
                        }
                        if(dataListAirs.get(i).getSpo2() < minSPO2){
                            minSPO2 = dataListAirs.get(i).getSpo2();
                        }
                        if(dataListAirs.get(i).getSpo2() > maxSPO2){
                            maxSPO2 = dataListAirs.get(i).getSpo2();
                        }
                        if(dataListAirs.get(i).getIr() < minIR){
                            minIR = dataListAirs.get(i).getIr();
                        }
                        if(dataListAirs.get(i).getIr() > maxIR){
                            maxIR = dataListAirs.get(i).getIr();
                        }

                        totalBMP += dataListAirs.get(i).getBmp();
                        totalSPO2 += dataListAirs.get(i).getSpo2();
                        totalIR += dataListAirs.get(i).getIr();
                    }

                    txtBMPMin.setText(minBMP+"");
                    txtBMPMax.setText(maxBMP+"");
                    txtBMPMedium.setText((totalBMP / (dataListAirs.size() > 0 ? dataListAirs.size() : 1)) +"");

                    txtSPO2Min.setText(minSPO2+"");
                    txtSPO2Max.setText(maxSPO2+"");
                    txtSPO2Medium.setText((totalSPO2 / (dataListAirs.size() > 0 ? dataListAirs.size() : 1)) +"");

                    txtIRMin.setText(minIR+"");
                    txtIRMax.setText(maxIR+"");
                    txtIRMedium.setText((totalIR / (dataListAirs.size() > 0 ? dataListAirs.size() : 1)) +"");

                }else if (response.code() == 403){
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("id-" + Storager.USER_APP.getUserData().getRole());
                    File dir = getFilesDir();
                    File file = new File(dir, Storager.FILE_INTERNAL);
                    file.delete();

                    Intent intent = new Intent(Max30100SensorActivity.this , LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataMax30100> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtTime.setText(sdf.format(myCalendar.getTime()));
        getData();
    }

    private void addController() {

        imgRing = findViewById(R.id.ivNotification);

        txtBMPMin = findViewById(R.id.txt_bmp_min);
        txtBMPMax = findViewById(R.id.txt_bmp_max);
        txtBMPMedium = findViewById(R.id.txt_bmp_medium);

        txtSPO2Min = findViewById(R.id.txt_spo2_min);
        txtSPO2Max = findViewById(R.id.txt_spo2_max);
        txtSPO2Medium = findViewById(R.id.txt_spo2_medium);

        txtIRMin = findViewById(R.id.txt_ir_min);
        txtIRMax = findViewById(R.id.txt_ir_max);
        txtIRMedium = findViewById(R.id.txt_ir_medium);


        edtTime = findViewById(R.id.edt_time_max30100);

        edtTime.setText(Protecter.getDate());
        date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        id = getIntent().getIntExtra("id", 0);
        lineChart = findViewById(R.id.line_chart);

        //lineChart.setData(generateDataLine(1 ));
        Description description = new Description();
        description.setText("Max30100");
        lineChart.setDescription(description);

        lineChart.getAxisRight().setDrawLabels(true);
        //lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getLegend().setEnabled(true);
//        lineChart.getAxisRight().setDrawGridLines(false);
//        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //yAxis.setLabelCount(5);
//        xAxis.setLabelCount(7,true);
//        yAxis.setLabelCount(5,true);
        //xAxis.enableAxisLineDashedLine(10,10,0);

    }

    private LineData generateDataLine(List<DataListMax30100> dataListAirs) {

        while (dataListAirs.size() > 60){
            dataListAirs.remove(dataListAirs.size()-1);
        }

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < dataListAirs.size(); i++) {
            values1.add(new Entry(i, (float)(dataListAirs.get(i).getBmp())));
        }

        LineDataSet d1 = new LineDataSet(values1, "BMP");
        d1.setLineWidth(1f);
        d1.setColor(getResources().getColor(R.color.green));
        d1.setDrawValues(false);
        d1.setDrawCircles(false);
        d1.setDrawCircleHole(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        sets.add(d1);

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0 ; i < dataListAirs.size() ; i++){
            values2.add(new Entry(i, (float)(dataListAirs.get(i).getSpo2())));
        }

        LineDataSet d2 = new LineDataSet(values2, "SPO2");
        d2.setLineWidth(1f);
        d2.setColor(getResources().getColor(R.color.red));
        d2.setDrawValues(false);
        d2.setDrawCircles(false);
        d2.setDrawCircleHole(false);

        sets.add(d2);

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0 ; i < dataListAirs.size() ; i++){
            values3.add(new Entry(i, (float)(dataListAirs.get(i).getIr())));
        }

        LineDataSet d3 = new LineDataSet(values3, "IR");
        d3.setLineWidth(1f);
        d3.setColor(getResources().getColor(R.color.yellow));
        d3.setDrawValues(false);
        d3.setDrawCircles(false);
        d3.setDrawCircleHole(false);

        sets.add(d3);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        if (Storager.USER_APP.getUserData().getBpm_limit() != null){
            LimitLine ll1 = new LimitLine(Float.parseFloat(Storager.USER_APP.getUserData().getBpm_limit()), "BPM Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
            leftAxis.addLimitLine(ll1);
        }
        if (Storager.USER_APP.getUserData().getIr_limit() != null){
            LimitLine ll1 = new LimitLine(Float.parseFloat(Storager.USER_APP.getUserData().getIr_limit()), "Ir Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            ll1.setTextSize(10f);
            leftAxis.addLimitLine(ll1);
        }

        return new LineData(sets);
    }

    public void back(View view) {
        finish();
    }
}